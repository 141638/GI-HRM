package com.gi.hrm.utils.bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Slf4j
@Service
public class FileUtilService {
    /** maximum number of records can be written in a backup file */
    public static final Integer MAX_BACKUP_FILE_RECORDS = 10000;
    /** data buffer size */
    public static final int DATA_BUFFER_SIZE = 4096;
    /** BOM */
    private static final byte[] BOM = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
    /** backup directory */
    private final String backupDirectory;
    /** firebase project ID */
    private final String firebaseProject;

    /**
     * Constructor
     *
     * @param env environment property access
     */
    public FileUtilService(final Environment env) {
        this.backupDirectory = env.getProperty("com.gi.hrm.cartracker.backup_directory");
        this.firebaseProject = env.getProperty("firebase.project_id");
    }

    /**
     * Creates a new `ByteArrayOutputStream` with a UTF-8 byte order mark (BOM) prepended.
     *
     * @return `ByteArrayOutputStream` with the UTF-8 BOM prepended.
     * @throws IOException if an I/O error occurs while writing the BOM to the output stream.
     */
    public ByteArrayOutputStream byteArrayOutputStream() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(BOM);
        return out;
    }

    /**
     * Export data to CSV
     *
     * @param recordData list of row cells data
     * @param header array of headers
     * @return csv file in byte[] format
     */
    public byte[] exportCsv(List<String[]> recordData, String[] header) {
        try (
                ByteArrayOutputStream out = byteArrayOutputStream();
                ICSVWriter csvWriter = new CSVWriterBuilder(new OutputStreamWriter(out))
                        .withEscapeChar(ICSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(ICSVWriter.DEFAULT_LINE_END)
                        .build()) {

            csvWriter.writeNext(header);
            csvWriter.writeAll(recordData);
            csvWriter.flush();
            return out.toByteArray();
        } catch (IOException e) {
            log.error("Error when exporting CSV", e);
            return new byte[0];
        }
    }

    /**
     * Convert a list of object into a list of value array
     *
     * @param items list of object to convert
     * @param colSize total value in the array
     * @param hasIndexCol determine if the columns should be skipped at the first one or not
     * @param quoteFields field names need to be wrapped by [""]
     * @return list of value array
     * @throws IllegalAccessException if the java.lang.reflect.Field.get() access is prevented
     */
    @SuppressWarnings({"java:S3011", "PMD.AvoidAccessibilityAlteration"})
    public List<String[]> listObjectToLineArray(List<?> items, int colSize, boolean hasIndexCol,
                                                String... quoteFields) throws IllegalAccessException {
        List<String[]> linesArray = new ArrayList<>();
        int startAt = hasIndexCol ? 1 : 0;
        int row = 1;
        for (Object item : items) {
            String[] line = new String[colSize];
            Field[] fields = item.getClass().getDeclaredFields();
            if (hasIndexCol) {
                line[0] = String.valueOf(row);
            }
            for (int i = startAt; i < colSize; i++) {
                Field field = fields[i - startAt];
                field.setAccessible(true);
                if (Arrays.stream(quoteFields).anyMatch(quoteField -> quoteField.equals(field.getName()))) {
                    line[i] = String.format("\" %s \"", coalesce(field.get(item), "").toString());
                } else {
                    line[i] = coalesce(field.get(item), "").toString();
                }
            }
            linesArray.add(line);
            row++;
        }
        return linesArray;
    }

    /**
     * Read CSV lines from multipart file, auto assigns the delimiter on file name extension
     *
     * @param csvFile FilePart csv format
     * @return flux stream of cells in a row
     */
    public Flux<String[]> readCsvFromMultipartFile(FilePart csvFile) {
        String[] fileNameParts = Objects.requireNonNull(csvFile.filename()).split("\\.");
        String fileExtension = fileNameParts[fileNameParts.length - 1];
        String delimiter = switch (fileExtension) {
            case "csv" -> ",";
            case "tsv" -> "\t";
            default -> throw new IllegalStateException("Unexpected file extension: " + fileExtension);
        };
        return readCsvFromMultipartFile(csvFile, delimiter);
    }

    /**
     * Read CSV lines from multipart file
     *
     * @param csvFile FilePart csv format
     * @param cellDelimiter delimiter char for cells
     * @return flux stream of cells in a row
     */
    public Flux<String[]> readCsvFromMultipartFile(FilePart csvFile, String cellDelimiter) {
        return csvFile.content()
                .flatMap(fileContent ->
                        Flux.fromStream(
                                new BufferedReader(
                                        new InputStreamReader(fileContent.asInputStream())
                                ).lines()))
                .map(row -> row.split(cellDelimiter));
    }

    /**
     * Read JSON file into a flux of object
     *
     * @param jsonFile json file contains array of objects
     * @param clazz type of object inside the array
     * @param <S> generic type that can read lines from
     * @param <T> generic type for object
     * @return flux stream of objects
     */
    public <S extends Part, T> Flux<T> readArrayFromJsonFile(S jsonFile, Class<T> clazz) {
        Gson gson = new Gson();
        return jsonFile.content()
                .flatMap(fileContent ->
                        Flux.fromStream(new BufferedReader(new InputStreamReader(fileContent.asInputStream())).lines()))
                .collectList()
                .flatMapMany(lines ->
                        Flux.fromIterable(
                                gson.fromJson(String.join("", lines),
                                        TypeToken.getParameterized(List.class, clazz).getType()
                                )
                        )
                );
    }

    /**
     * Read file xlsx from multipart file
     *
     * @param xlsxFile FilePart in xlsx format
     * @param ignoreHeader boolean flag to check if the result should contain header row or not
     * @param totalColumns total columns to be processed
     * @return flux stream of row cells
     */
    public Flux<String[]> readXlsxFromMultipartFile(FilePart xlsxFile, Boolean ignoreHeader, Integer totalColumns) {
        try {
            // create temp file for reading
            Path tempFile = Files.createTempFile(xlsxFile.filename(), "-temp-" + new Date().getTime());
            // open file async channel
            var channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);

            return DataBufferUtils
                    // start write file content using the async channel
                    .write(xlsxFile.content(), channel, 0)
                    .doOnTerminate(() -> {
                        try {
                            // on done writing, close the async channel
                            channel.close();
                        } catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    // block reactive chain by waiting until the data buffer is done reading
                    .collectList()
                    // read the completed input stream from temp file
                    .flatMapMany(dataBuffer -> readXlsxFromWrittenTempFile(tempFile, ignoreHeader, totalColumns));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * After the temp file is written, read the file as input stream into workbook
     *
     * @param tempFile xlsx temp file
     * @param ignoreHeader boolean flag to check if the result should contain header row or not
     * @param totalColumns total columns to be processed
     * @return flux stream of row cells
     */
    private Flux<String[]> readXlsxFromWrittenTempFile(Path tempFile, Boolean ignoreHeader, Integer totalColumns) {
        try (
                Workbook workbook = WorkbookFactory.create(new FileInputStream(tempFile.toString()))
        ) {
            Sheet sheet = workbook.getSheetAt(0);

            List<String[]> result = new ArrayList<>();
            // get header column size if is not inputted
            if (Objects.isNull(totalColumns)) {
                Row headerRow = sheet.getRow(0);
                int iterateIndex = 0;
                while (Objects.nonNull(cellValue(headerRow.getCell(iterateIndex)))) {
                    iterateIndex++;
                }
                totalColumns = iterateIndex;
            }
            // add header
            if (Objects.isNull(ignoreHeader) || Boolean.FALSE.equals(ignoreHeader)) {
                Row headerRow = sheet.getRow(0);
                result.add(
                        IntStream.range(0, totalColumns).boxed()
                                .map(cellIndex -> cellValue(headerRow.getCell(cellIndex))).toArray(String[]::new)
                );
            }
            // skip header
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String[] rowCells = new String[totalColumns];
                for (int j = 0; j < totalColumns; j++) {
                    Cell cell = row.getCell(j);
                    if (Objects.isNull(cell)) {
                        rowCells[j] = "";
                    } else {
                        rowCells[j] = cellValue(cell);
                    }
                }
                result.add(rowCells);
            }
            return Flux.fromIterable(result);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String cellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case BLANK, _NONE -> null;
            case BOOLEAN -> Boolean.toString(cell.getBooleanCellValue());
            case NUMERIC -> Double.toString(cell.getNumericCellValue());
            case STRING -> cell.getStringCellValue();
            default -> throw new IllegalStateException("CellType does not supported: " + cell.getCellType());
        };
    }

    /**
     * Write object into JSON file
     *
     * @param filename file name
     * @param dir directory to write the file
     * @param obj object to be written
     * @throws IOException if the directory of the backup folder cannot be found/created
     */
    public void writeBackupToJsonFile(String filename, String dir, Object obj) throws IOException {
        Gson gson = new Gson();

        String writeDirectory = (backupDirectory + "/" + dir).replace("//", "/");
        File base = new File(writeDirectory);
        if (!base.exists() && Boolean.FALSE.equals(base.mkdirs())) {
            throw new IOException("Failed to find/create directory " + writeDirectory);
        }
        Path filePath = Paths.get(writeDirectory,
                filename + "-" + firebaseProject + "-" + (new Date()).getTime() + ".json");
        try (FileWriter writer = new FileWriter(filePath.toString())) {
            gson.toJson(obj, writer);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Read FilePart as Json file content into dto
     *
     * @param jsonFile file contains JSON object
     * @param clazz class to map JSON object into
     * @param <T> generic type define clazz
     * @return mapped object
     */
    public <T> Mono<T> readJSONFileIntoObject(FilePart jsonFile, Class<T> clazz) {
        return this.readJSONFileIntoObject(jsonFile, null, clazz);
    }

    /**
     * Read FilePart as Json file content into dto
     *
     * @param jsonFile file contains JSON object
     * @param clazz class to map JSON object into
     * @param <T> generic type define clazz
     * @return mapped object
     */
    public <T> Mono<T> readJSONFileIntoObject(FilePart jsonFile, TypeReference<T> clazz) {
        return this.readJSONFileIntoObject(jsonFile, clazz.getType(), null);
    }

    /**
     * Read FilePart as Json file content into dto(for array type)
     *
     * @param jsonFile file contains JSON object
     * @param type array/list type to map JSON object into
     * @param <T> generic type define clazz
     * @return mapped object
     */
    public <T> Mono<T> readJSONFileIntoObject(FilePart jsonFile, Type type) {
        return this.readJSONFileIntoObject(jsonFile, type, null);
    }

    /**
     * Private logic for reading file part as Json file content into predefined type or class
     *
     * @param jsonFile file contains JSON object
     * @param type array/list type to map JSON object into
     * @param clazz class to map JSON object into
     * @param <T> generic type define clazz
     * @return mapped object
     */
    private <T> Mono<T> readJSONFileIntoObject(FilePart jsonFile, Type type, Class<T> clazz) {
        try {
            // temp file to write the file part content into
            Path tempFile = Files.createTempFile(jsonFile.filename(), "-temp-" + new Date().getTime());
            // open file async channel
            var channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);

            // start write jsonFile to the temp file
            return DataBufferUtils
                    .write(jsonFile.content(), channel, 0)
                    .doOnTerminate(() -> {
                        try {
                            channel.close();
                        } catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    // block reactive stream until done writing
                    .collectList()
                    .flatMap(dataBuffers -> {
                        // read json from the written temp file, mapping to T class instance
                        try (
                                InputStream jsonStream = new FileInputStream(tempFile.toString());
                                JsonReader reader = new JsonReader(new InputStreamReader(jsonStream))
                        ) {
                            return Mono.just(new Gson().fromJson(reader, coalesce(type, clazz)));
                        } catch (IOException e) {
                            return Mono.error(new IllegalStateException(e));
                        }
                    });
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Get coalesce between 2 value
     *
     * @param checkNullValue (a): value to perform null check
     * @param alternativeValue (b): value if the first one is null
     * @param <T> generic type for both values
     * @return not-null (a), or (b)
     */
    private <T> T coalesce(T checkNullValue, T alternativeValue) {
        return Objects.isNull(checkNullValue) ? alternativeValue : checkNullValue;
    }
}

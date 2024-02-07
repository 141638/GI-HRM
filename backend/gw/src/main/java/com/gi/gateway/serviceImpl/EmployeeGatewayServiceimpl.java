package com.gi.gateway.serviceImpl;

import com.gi.gateway.common.Constants;
import com.gi.gateway.common.JsonEnpointKeyMapping;
import com.gi.gateway.common.ReactiveCommonService;
import com.gi.gateway.common.Utils;
import com.gi.gateway.common.WebClientUtils;
import com.gi.gateway.dto.request.employee.EmployeeAddRequest;
import com.gi.gateway.dto.request.employee.EmployeeListSearchRequest;
import com.gi.gateway.dto.response.common.ApiResponse;
import com.gi.gateway.dto.response.common.PreBuiltReactiveServerResponse;
import com.gi.gateway.dto.response.common.PreBuiltServerResponse;
import com.gi.gateway.entity.Employees;
import com.gi.gateway.entity.RoleGroup;
import com.gi.gateway.exception.BadRequestException;
import com.gi.gateway.exception.RecordNotFoundException;
import com.gi.gateway.repository.EmployeeRepository;
import com.gi.gateway.repository.RoleGroupRepository;
import com.gi.gateway.service.EmployeeGatewayService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@AllArgsConstructor
public class EmployeeGatewayServiceimpl implements EmployeeGatewayService {
    private final WebClientUtils webClientUtils;
    private final WebClient webClient;
    private final ReactiveCommonService reactService;
    private final PasswordEncoder encoder;
    private final RoleGroupRepository roleGroupRepository;
    private final EmployeeRepository employeeRepository;

    private final RabbitTemplate rabbitGo;

    private String getEndpoint() {
        return String.valueOf(Utils.getJsonEndpoints().get(JsonEnpointKeyMapping.EMPLOYEE) + "/api/staff");
    }

    private String getEndpointDropdown() {
        return String.valueOf(Utils.getJsonEndpoints().get(JsonEnpointKeyMapping.EMPLOYEE) + "/api/dropdown/");
    }

    @Override
    public Mono<ServerResponse> searchEmployee(ServerRequest request) {
        return request.bodyToMono(EmployeeListSearchRequest.class).map(searchRequest -> {
            return webClient.post().uri(getEndpoint() + "/search")
                    .body(Mono.just(searchRequest), EmployeeListSearchRequest.class).retrieve()
                    .bodyToMono(ApiResponse.class).log();
        }).flatMap(PreBuiltServerResponse::success);

    }

    @Override
    public Mono<ServerResponse> addEmployee(ServerRequest request) {
        return request.bodyToFlux(EmployeeAddRequest.class).take(1).next().map(empRequest -> {
            Integer employeeId = createEmployee(empRequest, 1);
            var headers = Map.of("session-user-id", 1);
            return webClientUtils.post(getEndpoint() + "/add", headers, empRequest, EmployeeAddRequest.class)
                    .bodyToMono(ApiResponse.class)
                    .onErrorResume(ex -> addEmployeeErrorResume(ex, employeeId)).log();
        }).flatMap(PreBuiltServerResponse::success);
    }

    private Integer createEmployee(EmployeeAddRequest empRequest, Integer userId) {
        RoleGroup roleGroup = roleGroupRepository.findById(empRequest.getRole())
                .orElseThrow(() -> new RecordNotFoundException("RoleGroup"));
        Employees employee = new Employees();
        employee.setEmail(empRequest.getEmail());
        employee.setPassword(encoder.encode(empRequest.getPassword()));
        employee.setRoleGroups(roleGroup);
        employee.setUsername(empRequest.getUsername());

        employee.setCommonRegist(userId);
        employeeRepository.save(employee);

        return employee.getId();
    }

    private Mono<ApiResponse> addEmployeeErrorResume(Throwable ex, Integer employeeId) {
        employeeRepository.deleteById(employeeId);
        return Mono.just(ApiResponse.apiResponseErrorHandler(HttpStatus.BAD_REQUEST, Constants.DEFAULT_ERROR_MESSAGE));
    }

    @Override
    public Mono<ServerResponse> detailsEmployee(ServerRequest request) {
        String rawID = request.queryParam("id").orElse(null);
        if (!StringUtils.hasText(rawID)) {
            ApiResponse responseBody = ApiResponse.apiResponseErrorHandler(HttpStatus.BAD_REQUEST, "ID Not Found");
            return PreBuiltServerResponse.badRequest(responseBody);
        }
        final Integer id = Integer.parseInt(rawID);
        String uri = reactService.buildUri(getEndpoint(), "details", Map.of("id", id));

        return webClient.get().uri(uri).retrieve().bodyToMono(ApiResponse.class)
                .flatMap(PreBuiltServerResponse::success);
    }

    @Override
    public Mono<ServerResponse> deleteEmployee(ServerRequest request) {
        String rawID = request.queryParam("id").orElse(null);
        if (!StringUtils.hasText(rawID)) {
            ApiResponse responseBody = ApiResponse.apiResponseErrorHandler(HttpStatus.BAD_REQUEST, "ID Not Found");
            return PreBuiltServerResponse.badRequest(responseBody);
        }
        final Integer id = Integer.parseInt(rawID);

        String uri = reactService.buildUri(getEndpoint(), "delete", Map.of("id", id));
        return webClient.delete().uri(uri).retrieve().bodyToMono(ApiResponse.class)
                .flatMap(PreBuiltServerResponse::success);
    }

    @Override
    public Mono<ServerResponse> dropdown(ServerRequest request) {
        String path = request.queryParam("url").orElseThrow(() -> new BadRequestException("url cannot be null", 0));
        return webClient.get().uri(getEndpointDropdown() + path).retrieve().bodyToMono(ApiResponse.class).log()
                .flatMap(PreBuiltReactiveServerResponse::success);
    }
}

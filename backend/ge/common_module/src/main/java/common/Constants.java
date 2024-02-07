package common;

public class Constants {

	public static final boolean DELETE_FLASE = false;
	public static final boolean DELETE_TRUE = true;

	// HTTP status
	public static final Integer HTTP_200 = 200;
	public static final Integer HTTP_400 = 400;
	public static final Integer HTTP_401 = 401;
	public static final Integer HTTP_404 = 404;
	public static final Integer HTTP_500 = 500;

	// Status response
	public static final String SUCCESS = "Action success";
	public static final String FAILED = "Action failed";
	public static final String RECORD_NOT_FOUND = "Record not found - %s";
	public static final String OBJECT_FIELD_MISMATCH = "Entity field's and request param's size mismatch";

	public static final String VALIDATE_NOT_NULL = "Input field cannot be empty";

	// Database table
	public static final String EMPLOYEES = "employees";
	public static final String ROLE_GROUPS = "role_groups";
	public static final String PROFILES = "profiles";
	public static final String DEPARTMENTS = "departments";
	public static final String EQUIPMENTS = "equipments";
	public static final String DAY_OFFS = "day_offs";
	public static final String ROOMS = "rooms";
	public static final String PROJECTS = "projects";
	public static final String DOCUMENTS = "documents";
	public static final String BOOKING_ROOMS = "booking_rooms";
}

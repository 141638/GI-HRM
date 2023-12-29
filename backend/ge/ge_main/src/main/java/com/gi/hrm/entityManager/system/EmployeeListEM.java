package com.gi.hrm.entityManager.system;

import com.gi.hrm.dto.request.system.EmployeeListSearchRequest;
import com.gi.hrm.dto.response.system.EmployeeListResponse;
import com.gi.hrm.dto.response.system.ResultPageResponse;
import com.gi.hrm.entityManager.CommonEntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeListEM extends CommonEntityManager {
	public EmployeeListEM() {
		super();
	}

	private static final String QUERY_WITH_SELECT = "With datatable AS (SELECT EMP.ID AS employeeId, PRO.image_url AS imageUrl, "
	        + "PRO.full_name AS employeeName, EMP.email AS employeeEmail, dep.name AS departmentName, PRO.date_of_birth AS dateOfBirth, "
	        + "EMP.insystem_role AS insystemRole, EMP.employee_code AS employeeCode, EMP.department_id AS employeeDepartment ";
	private static final String QUERY_WITH_FROM = "FROM Employees EMP "
	        + "JOIN Profiles PRO ON PRO.employee_id = EMP.ID "
	        + "LEFT JOIN Departments DEP ON EMP.department_id = DEP.ID ";
	private static final String QUERY_WITH_WHERE = "WHERE PRO.delete_flag = FALSE AND EMP.delete_flag = FALSE) ";

	private static final String QUERY_SELECT = "SELECT dtb.employeeId AS id, dtb.imageUrl, dtb.employeeName, "
	        + "dtb.departmentName, dtb.employeeCode, dtb.employeeDepartment, dtb.employeeEmail, dtb.insystemRole, "
	        + "TO_CHAR(dtb.dateOfBirth," + DATE_SQLFORMAT + ") AS dateOfBirth\n";
	private static final String QUERY_FROM = " FROM datatable dtb WHERE TRUE \n";
	private static final String QUERY_ORDER = "ORDER BY dtb.employeeId ASC\n";

	private static final String COND_NAME = "dtb.employeeName";
	private static final String COND_EMAIL = "dtb.employeeEmail";
	private static final String COND_DEPARTMENT = "dtb.employeeDepartment";
	private static final String COND_CODE = "dtb.employeeCode";
	private static final String COND_DATE_OF_BIRTH = "dtb.dateOfBirth";
	private static final String COND_KEYS = "dtb";

	public ResultPageResponse searchListEmployee(EmployeeListSearchRequest request) {
		Integer pageSize = request.getPageSize();
		Integer currentPage = request.getCurrentPage();
		List<String> listFields = Arrays.asList(COND_NAME, COND_EMAIL, COND_DEPARTMENT, COND_CODE, COND_DATE_OF_BIRTH,
				COND_KEYS);

		Session session = session();
		StringBuilder sqlBuilder = sqlBuilder();
		sqlBuilder.append(QUERY_WITH_SELECT);
		sqlBuilder.append(QUERY_WITH_FROM);
		sqlBuilder.append(QUERY_WITH_WHERE);

		sqlBuilder.append(QUERY_SELECT);
		sqlBuilder.append(QUERY_FROM);
		setParamsMapping(listFields, request, sqlBuilder);
		sqlBuilder.append(QUERY_ORDER);

		String listSql = sqlBuilder.toString();
		String countSql = buildCountSql(listSql, QUERY_SELECT, QUERY_ORDER);
		@SuppressWarnings({ "unchecked", "deprecation" })
		NativeQuery<EmployeeListResponse> listQuery = session.createNativeQuery(listSql);
		offSetLimit(currentPage, pageSize, listQuery);
		NativeQuery<Integer> countQuery = session.createNativeQuery(countSql, Integer.class);
		setQueryParameters(Arrays.asList(listQuery, countQuery), request);
		int count = countQuery.uniqueResult();

		session.close();
		session.clear();

		return pageResponseImpl.resultPagination(request, listQuery, EmployeeListResponse.class, count);
	}
}

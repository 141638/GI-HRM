package com.gi.gateway.serviceImpl;

import com.gi.gateway.common.Constants;
import com.gi.gateway.common.JsonEnpointKeyMapping;
import com.gi.gateway.common.ReactiveCommonService;
import com.gi.gateway.common.Utils;
import com.gi.gateway.config.MyRabbitProperties;
import com.gi.gateway.dto.request.employee.EmployeeAddRequest;
import com.gi.gateway.dto.request.employee.EmployeeListSearchRequest;
import com.gi.gateway.dto.response.common.ApiResponse;
import com.gi.gateway.dto.response.common.PreBuiltServerResponse;
import com.gi.gateway.entity.Employees;
import com.gi.gateway.entity.RoleGroup;
import com.gi.gateway.exception.RecordNotFoundException;
import com.gi.gateway.repository.EmployeeRepository;
import com.gi.gateway.repository.RoleGroupRepository;
import com.gi.gateway.service.EmployeeGatewayService;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeGatewayServiceimpl implements EmployeeGatewayService {
	private final WebClient webClient;
	private final ReactiveCommonService reactService;
	private final PasswordEncoder encoder;
	private final RoleGroupRepository roleGroupRepository;
	private final EmployeeRepository employeeRepository;

	private final RabbitTemplate rabbitGo;

	private String getEndpoint() {
		return String.valueOf(Utils.getJsonEndpoints().get(JsonEnpointKeyMapping.EMPLOYEE) + "/api/staff");
	}

	@Override
	public Mono<ServerResponse> searchEmployee(ServerRequest request) {
		return request.bodyToMono(EmployeeListSearchRequest.class).map(searchRequest -> {
			return webClient.post().uri(getEndpoint() + "/search")
					.body(Mono.just(searchRequest), EmployeeListSearchRequest.class).retrieve()
					.bodyToMono(ApiResponse.class).log();
		}).flatMap(webClientResponse -> PreBuiltServerResponse.OK(webClientResponse));

	}

	@Override
	public Mono<ServerResponse> addEmployee(ServerRequest request) {
		return request.bodyToMono(EmployeeAddRequest.class).flatMap(addRequest -> {
			rabbitGo.convertAndSend(MyRabbitProperties.TOPIC_EXCHANGE_NAME, MyRabbitProperties.ROUTING_KEY_1,
					addRequest);
			return PreBuiltServerResponse.OK(ApiResponse.ApiResponseSuccess(addRequest));
		});
//		return reactService.currentUserLoginId().flatMap(item -> {
//			Integer employeeId = createEmployee(request, item);
//			return webClient.post().uri(getEndpoint() + "/add").header("session-user-id", String.valueOf(item))
//					.body(Mono.just(request), EmployeeAddRequest.class).retrieve().bodyToMono(ApiResponse.class)
//					.onErrorResume(ex -> addEmployeeErrorResume(ex, employeeId)).log();
//		});
	}

	private Integer createEmployee(EmployeeAddRequest request, Integer userId) {
		RoleGroup roleGroup = roleGroupRepository.findById(request.getRole())
				.orElseThrow(() -> new RecordNotFoundException("RoleGroup"));
		Employees employee = new Employees();
		employee.setEmail(request.getEmail());
		employee.setPassword(encoder.encode(request.getPassword()));
		employee.setRoleGroups(roleGroup);
		employee.setUsername(request.getUsername());

		employee.setUpdatedAt(Timestamp.from(Instant.now()));
		employee.setUpdatedBy(userId);
		employee.setDeleteFlag(false);
		employeeRepository.save(employee);

		return employee.getId();
	}

	private Mono<ApiResponse> addEmployeeErrorResume(Throwable ex, Integer employeeId) {
		employeeRepository.deleteById(employeeId);
		return Mono.just(ApiResponse.ApiResponseErrorHandler(HttpStatus.BAD_REQUEST, Constants.DEFAULT_ERROR_MESSAGE));
	}

	@Override
	public Mono<ServerResponse> detailsEmployee(ServerRequest request) {
		String rawID = request.queryParam("id").orElse(null);
		if (!StringUtils.hasText(rawID)) {
			ApiResponse responseBody = ApiResponse.ApiResponseErrorHandler(HttpStatus.BAD_REQUEST, "ID Not Found");
			return PreBuiltServerResponse.BAD_REQUEST(responseBody);
		}
		final Integer id = Integer.parseInt(rawID);
		String uri = reactService.buildUri(getEndpoint(), "details", Map.of("id", id));

		return webClient.get().uri(uri).retrieve().bodyToMono(ApiResponse.class)
				.flatMap(response -> PreBuiltServerResponse.OK(response));
	}

	@Override
	public Mono<ServerResponse> deleteEmployee(ServerRequest request) {
		String rawID = request.queryParam("id").orElse(null);
		if (!StringUtils.hasText(rawID)) {
			ApiResponse responseBody = ApiResponse.ApiResponseErrorHandler(HttpStatus.BAD_REQUEST, "ID Not Found");
			return PreBuiltServerResponse.BAD_REQUEST(responseBody);
		}
		final Integer id = Integer.parseInt(rawID);

		String uri = reactService.buildUri(getEndpoint(), "delete", Map.of("id", id));
		return webClient.delete().uri(uri).retrieve().bodyToMono(ApiResponse.class)
				.flatMap(response -> PreBuiltServerResponse.OK(response));
	}

}

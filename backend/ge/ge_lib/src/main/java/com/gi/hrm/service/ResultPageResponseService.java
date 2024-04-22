package com.gi.hrm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gi.hrm.util.HibernateScalarUtils;
import com.gi.hrm.util.PaginationResult;
import org.hibernate.query.NativeQuery;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.gi.hrm.dto.request.CommonPaginatorRequest;
import com.gi.hrm.dto.response.ResultPageResponse;

@Service
public class ResultPageResponseService {
	private static ModelMapper modelMapper = new ModelMapper();

	public <T> ResultPageResponse resultPage(CommonPaginatorRequest request, Page<?> data, Class<T> responseDTO) {
		ResultPageResponse res = new ResultPageResponse();
		res.setCurrentPage(request.getCurrentPage());
		res.setItems(data.getContent().stream().map(item -> modelMapper.map(item, responseDTO))
				.collect(Collectors.toList()));
		res.setTotalPages(data.getTotalPages());
		res.setTotalItems(Long.valueOf(data.getTotalElements()).intValue());
		return res;
	}

	public Pageable pageRequest(CommonPaginatorRequest request, String order) {
		String[] splitOrder = order.split(",");
		List<Order> sorts = new ArrayList<>();
		for (String string : splitOrder) {
			sorts.add(new Sort.Order(Sort.Direction.ASC, string));
		}
		Sort sort = Sort.by(sorts);
		return PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);
	}

	public <T> ResultPageResponse resultPageResponse(CommonPaginatorRequest request, NativeQuery<?> query,
			Class<T> clazz) {
		ResultPageResponse resultPageResponse = new ResultPageResponse();
		HibernateScalarUtils.addScalar(query, clazz);
		PaginationResult<?> result = new PaginationResult<>(query, request.getCurrentPage(), request.getPageSize(),
				request.getPageSize());
		resultPageResponse.setItems(result.getList());
		resultPageResponse.setTotalPages(result.getTotalPages());
		resultPageResponse.setTotalItems(result.getTotalRecords());
		resultPageResponse.setCurrentPage(result.getCurrentPage());
		return resultPageResponse;
	}

	public <T> ResultPageResponse resultPagination(CommonPaginatorRequest request, NativeQuery<?> query, Class<T> clazz,
			int totalRecords) {
		ResultPageResponse resultPageResponse = new ResultPageResponse();
		HibernateScalarUtils.addScalar(query, clazz);
		int totalPages;
		if (totalRecords % request.getPageSize() == 0) {
			totalPages = totalRecords / request.getPageSize();
		} else {
			totalPages = totalRecords / request.getPageSize() + 1;
		}
		resultPageResponse.setItems(query.getResultList());
		resultPageResponse.setTotalPages(totalPages);
		resultPageResponse.setTotalItems(totalRecords);
		resultPageResponse.setCurrentPage(request.getCurrentPage());
		return resultPageResponse;
	}

	public <T> ResultPageResponse resultPaginationNotClass(CommonPaginatorRequest request, List<?> arrays,
			int totalRecords) {
		ResultPageResponse resultPageResponse = new ResultPageResponse();
		int totalPages;
		if (totalRecords % request.getPageSize() == 0) {
			totalPages = totalRecords / request.getPageSize();
		} else {
			totalPages = totalRecords / request.getPageSize() + 1;
		}
		resultPageResponse.setItems(arrays);
		resultPageResponse.setTotalPages(totalPages);
		resultPageResponse.setTotalItems(totalRecords);
		resultPageResponse.setCurrentPage(request.getCurrentPage());
		return resultPageResponse;
	}
}

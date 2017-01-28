package com.khokhlov.common.util;

import com.khokhlov.rest.response.common.AbstractResponseRo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Khokhlov Pavel
 */
public class ControllerUtil {

	public static <R extends AbstractResponseRo> ResponseEntity<R> wrapResponse(R response) {
		return new ResponseEntity<>(response, getStatus(response));
	}

	private static <R extends AbstractResponseRo> HttpStatus getStatus(R response) {
		return response.hasError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
	}
}

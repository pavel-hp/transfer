package com.khokhlov.common.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.rest.response.common.EmptyResponseRo;
import com.khokhlov.rest.response.common.ErrorRo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Khokhlov Pavel
 */
@Component
public class ValidationExceptionHandler implements HandlerExceptionResolver, Ordered {

	private static final Logger logger = LoggerFactory.getLogger(ValidationExceptionHandler.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1000;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
		// we have to know real reason of exception in logs
		logger.error("Error: ", e);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		EmptyResponseRo errorResponse = new EmptyResponseRo();
		ErrorRo errorRo = new ErrorRo();
		String errorCode = ErrorCode.UNKNOWN_ERROR.name();
		if (e instanceof HttpMessageConversionException || e instanceof HttpMediaTypeException) {
			errorCode = ErrorCode.REQUEST_PARAMETER_CONFLICT.name();
		}
		errorRo.setCode(errorCode);
		errorResponse.setError(errorRo);
		try {
			response.addHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8.toString());
			this.objectMapper
					.writerFor(EmptyResponseRo.class)
					.writeValue(response.getOutputStream(), errorResponse);
		} catch (IOException iox) {
			logger.error("Unknown error: ", iox);
		}
		return new ModelAndView();
	}
}

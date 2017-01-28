package com.khokhlov.common.error;

import com.khokhlov.rest.response.common.EmptyResponseRo;
import com.khokhlov.rest.response.common.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Khokhlov Pavel
 */
@Component
public class ValidationExceptionHandler implements HandlerExceptionResolver, Ordered {

	private static final Logger logger = LoggerFactory.getLogger(ValidationExceptionHandler.class);

	@Autowired
	private ErrorWriter writer;

	@Override
	public int getOrder() {
		return Order.ANY;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
		// we have to know real reason of exception in logs
		logger.error("Error: ", e);

		ErrorCode errorCode = ErrorCode.UNKNOWN_ERROR;
		if (e instanceof HttpMessageConversionException || e instanceof HttpMediaTypeException) {
			errorCode = ErrorCode.REQUEST_PARAMETER_CONFLICT;
		}
		EmptyResponseRo errorResponse = ConstraintViolationExceptionHandler.makeError(errorCode);

		writer.write(response, errorResponse, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		return new ModelAndView();
	}
}

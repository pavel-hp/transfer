package com.khokhlov.common.error;

import com.khokhlov.rest.response.common.EmptyResponseRo;
import com.khokhlov.rest.response.common.ErrorCode;
import com.khokhlov.rest.response.common.ErrorRo;
import com.khokhlov.rest.response.common.FieldErrorRo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Khokhlov Pavel
 */
@Component
public class ConstraintViolationExceptionHandler implements HandlerExceptionResolver, Ordered {

	private static final Logger logger = LoggerFactory.getLogger(ConstraintViolationExceptionHandler.class);

	@Autowired
	private ErrorWriter writer;

	@Autowired
	private MessageSource messageSource;

	private static final String BUNDLE_PATH = "message.field.";

	@Override
	public int getOrder() {
		return Order.CONSTRAINT;
	}


	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
		if (e instanceof ValidationException) {
			// we have to know real reason of exception in logs
			logger.error("ValidationException: ", e);
			EmptyResponseRo errorResponse = makeError(ErrorCode.VALIDATION);
			if (e instanceof ConstraintViolationException) {
				ConstraintViolationException constrainedEx = (ConstraintViolationException) e;
				Set<ConstraintViolation<?>> constraintViolations = constrainedEx.getConstraintViolations();
				fillErrors(constraintViolations, errorResponse.getError());
			}
			writer.write(response, errorResponse, HttpServletResponse.SC_BAD_REQUEST);
			return new ModelAndView();
		}
		return null;
	}


	private void fillErrors(Set<ConstraintViolation<?>> constraintViolations, ErrorRo errorRo) {
		List<FieldErrorRo> fieldErrorRoList = constraintViolations.stream()
				.map(ConstraintViolationExceptionHandler.this::translate).collect(Collectors.toList());
		errorRo.setFieldErrors(fieldErrorRoList);
	}

	private FieldErrorRo translate(ConstraintViolation<?> constraintViolation) {
		String field = StringUtils.substringAfterLast(
				constraintViolation.getPropertyPath().toString(), ".");
		String messageCode = constraintViolation.getMessageTemplate();
		String message = messageSource.getMessage(BUNDLE_PATH + messageCode, null, LocaleContextHolder.getLocale());

		return new FieldErrorRo(field, messageCode, message);
	}

	static EmptyResponseRo makeError(ErrorCode errorCode) {
		EmptyResponseRo errorResponse = new EmptyResponseRo();
		ErrorRo errorRo = new ErrorRo();
		errorRo.setCode(errorCode.name());
		errorResponse.setError(errorRo);
		return errorResponse;
	}


}
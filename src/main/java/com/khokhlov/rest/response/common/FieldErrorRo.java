package com.khokhlov.rest.response.common;

import com.khokhlov.rest.model.RestObject;

import java.util.Map;

/**
 * @author Khokhlov Pavel
 */
public class FieldErrorRo implements RestObject {

	private static final long serialVersionUID = 1318260393937482398L;

	private String fieldCode;
	private String errorCode;
	private String message;
	private Map<String, Object> parameters;

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
}
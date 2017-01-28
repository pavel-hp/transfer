package com.khokhlov.rest.response.common;

import com.khokhlov.rest.model.RestObject;

import java.util.List;

/**
 * @author Khokhlov Pavel
 */
public class ErrorRo implements RestObject {
	private static final long serialVersionUID = 3147753119057591373L;

	private String code;
	private String message;

	private List<FieldErrorRo> fieldErrors;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<FieldErrorRo> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorRo> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}

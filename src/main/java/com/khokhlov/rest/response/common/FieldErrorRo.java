package com.khokhlov.rest.response.common;

import com.khokhlov.rest.model.RestObject;

/**
 * @author Khokhlov Pavel
 */
public class FieldErrorRo implements RestObject {

	private static final long serialVersionUID = 1318260393937482398L;

	private String fieldCode;
	private String errorCode;
	private String message;

	public FieldErrorRo() {
	}

	public FieldErrorRo(String fieldCode, String errorCode, String message) {
		this();
		this.fieldCode = fieldCode;
		this.errorCode = errorCode;
		this.message = message;
	}

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

}
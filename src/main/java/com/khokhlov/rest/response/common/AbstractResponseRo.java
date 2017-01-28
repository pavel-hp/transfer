package com.khokhlov.rest.response.common;

import com.khokhlov.common.error.ErrorCode;
import com.khokhlov.rest.model.RestObject;

/**
 * @author Khokhlov Pavel
 */
public abstract class AbstractResponseRo implements RestObject {
	private static final long serialVersionUID = 3548947543855064105L;

	private ErrorRo error;

	public ErrorRo getError() {
		return error;
	}

	public void setError(ErrorRo error) {
		this.error = error;
	}

	public boolean hasError() {
		return error != null;
	}

	public ErrorRo makeValidationError(String message) {
		return makeError(ErrorCode.VALIDATION.name(), message);
	}

	public ErrorRo makeValidationError() {
		return makeValidationError(null);
	}

	private ErrorRo makeError(String code, String message) {
		if (!hasError()) {
			error = new ErrorRo();
		}
		error.setCode(code);
		error.setMessage(message);
		return error;
	}

}

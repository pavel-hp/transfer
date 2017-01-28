package com.khokhlov.rest.response.common;

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
}

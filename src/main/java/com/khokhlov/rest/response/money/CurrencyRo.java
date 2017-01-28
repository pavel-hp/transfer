package com.khokhlov.rest.response.money;

import com.khokhlov.rest.RestObject;

/**
 * @author Khokhlov Pavel
 */
public class CurrencyRo implements RestObject {

	private static final long serialVersionUID = -4897048654343915424L;

	private String code;
	private String symbol;
	private Integer scale;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}
}

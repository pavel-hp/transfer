package com.khokhlov.rest.model.money;

import com.khokhlov.rest.model.RestObject;

import java.math.BigDecimal;

/**
 * @author Khokhlov Pavel
 */
public class MoneyRo implements RestObject {
	private BigDecimal amount;
	private CurrencyRo currency;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public CurrencyRo getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyRo currency) {
		this.currency = currency;
	}
}

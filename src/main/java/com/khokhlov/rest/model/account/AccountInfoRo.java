package com.khokhlov.rest.model.account;

import com.khokhlov.rest.model.RestObject;
import com.khokhlov.rest.model.money.MoneyRo;

/**
 * @author Khokhlov Pavel
 */
public class AccountInfoRo implements RestObject {

	private static final long serialVersionUID = 1318260393937482398L;

	private Long id;
	private String accountName;
	private String accountNumber;
	private MoneyRo availableAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public MoneyRo getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(MoneyRo availableAmount) {
		this.availableAmount = availableAmount;
	}
}
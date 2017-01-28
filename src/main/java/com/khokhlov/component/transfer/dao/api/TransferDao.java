package com.khokhlov.component.transfer.dao.api;

import com.khokhlov.rest.model.account.AccountInfoRo;

import java.math.BigDecimal;

/**
 * @author Khokhlov Pavel
 */
public interface TransferDao {
	void transfer(AccountInfoRo source, AccountInfoRo destination, BigDecimal amount);
}

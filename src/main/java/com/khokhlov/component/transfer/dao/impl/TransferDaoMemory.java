package com.khokhlov.component.transfer.dao.impl;

import com.khokhlov.component.transfer.dao.api.TransferDao;
import com.khokhlov.rest.model.account.AccountInfoRo;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author Khokhlov Pavel
 */
@Repository
public class TransferDaoMemory implements TransferDao {

	@Override
	public void transfer(AccountInfoRo source, AccountInfoRo destination, BigDecimal amount) {


	}
}

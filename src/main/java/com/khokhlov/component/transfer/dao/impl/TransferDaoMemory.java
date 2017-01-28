package com.khokhlov.component.transfer.dao.impl;

import com.khokhlov.component.transfer.dao.api.TransferDao;
import com.khokhlov.rest.model.account.AccountInfoRo;
import com.khokhlov.rest.model.money.MoneyRo;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author Khokhlov Pavel
 */
@Repository
public class TransferDaoMemory implements TransferDao {

	@Override
	public void transfer(AccountInfoRo source, AccountInfoRo destination, BigDecimal amount) {
		MoneyRo sourceAvailable = source.getAvailableAmount();
		MoneyRo destinationAvailable = destination.getAvailableAmount();

		sourceAvailable.setAmount(sourceAvailable.getAmount().subtract(amount));
		destinationAvailable.setAmount(destinationAvailable.getAmount().add(amount));

	}

}

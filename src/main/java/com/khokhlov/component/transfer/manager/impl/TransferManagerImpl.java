package com.khokhlov.component.transfer.manager.impl;

import com.khokhlov.component.transfer.dao.api.AccountDao;
import com.khokhlov.component.transfer.dao.api.TransferDao;
import com.khokhlov.component.transfer.manager.api.TransferManager;
import com.khokhlov.rest.model.account.AccountInfoRo;
import com.khokhlov.rest.model.money.MoneyRo;
import com.khokhlov.rest.request.transfer.TransferRegisterRequestRo;
import com.khokhlov.rest.response.common.ErrorRo;
import com.khokhlov.rest.response.transfer.TransferPrepareResponse;
import com.khokhlov.rest.response.transfer.TransferRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

/**
 * @author Khokhlov Pavel
 */
@Component
@Validated
public class TransferManagerImpl implements TransferManager {

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private TransferDao transferDao;

	@Override
	public TransferPrepareResponse prepare() {
		TransferPrepareResponse response = new TransferPrepareResponse();
		response.getSourceAccounts().addAll(accountDao.getUserAccounts());
		response.getDestinationAccounts().addAll(accountDao.getUserAccounts());
		return response;
	}

	@Override
	public synchronized TransferRegisterResponse transfer(TransferRegisterRequestRo requestRo) {
		TransferRegisterResponse response = new TransferRegisterResponse();
		BigDecimal userMoney = requestRo.getMoney();
		Long destinationId = requestRo.getDestinationId();
		Long sourceId = requestRo.getSourceId();
		if (destinationId.equals(sourceId)) {
			ErrorRo errorRo = response.makeValidationError();
			errorRo.addErrorField("sourceId", "THE_SAME", "The same accounts");
			return response;
		}
		AccountInfoRo source = checkAccount(sourceId, "sourceId", response);
		AccountInfoRo destination = checkAccount(destinationId, "destinationId", response);
		if (response.hasError()) {
			return response;
		}
		MoneyRo sourceMoney = source.getAvailableAmount();

		MoneyRo destinationMoney = destination.getAvailableAmount();
		if (!sourceMoney.getCurrency().getCode().equals(destinationMoney.getCurrency().getCode())) {
			ErrorRo errorRo = response.makeValidationError();
			errorRo.addErrorField("sourceId", "CONVERSATION_FORBIDDEN", "Conversation not allowed");
		}
		int result = sourceMoney.getAmount().compareTo(userMoney);
		if (result == -1) {
			ErrorRo errorRo = response.makeValidationError();
			errorRo.addErrorField("sourceId", "NO_MONEY", "Insufficient funds");
		}


		if (!response.hasError()) {
			transferDao.transfer(source, destination, userMoney);
		}

		return response;
	}

	private AccountInfoRo checkAccount(Long sourceId, String fieldName, TransferRegisterResponse response) {
		AccountInfoRo sourceAccount = accountDao.findById(sourceId);
		if (sourceAccount == null) {
			ErrorRo errorRo = response.makeValidationError();
			errorRo.addErrorField(fieldName, "NOT_FOUND", "Account not found");
		}
		return sourceAccount;
	}

}

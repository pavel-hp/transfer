package com.khokhlov.component.transfer.manager.impl;

import com.khokhlov.component.transfer.dao.api.AccountDao;
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

import java.util.List;

/**
 * @author Khokhlov Pavel
 */
@Component
@Validated
public class TransferManagerImpl implements TransferManager {

	@Autowired
	private AccountDao accountDao;

	@Override
	public TransferPrepareResponse prepare() {
		TransferPrepareResponse response = new TransferPrepareResponse();
		response.getSourceAccounts().addAll(accountDao.getUserAccounts());
		response.getDestinationAccounts().addAll(accountDao.getUserAccounts());
		return response;
	}

	@Override
	public TransferRegisterResponse transfer(TransferRegisterRequestRo requestRo) {
		TransferRegisterResponse response = new TransferRegisterResponse();
		Long destinationId = requestRo.getDestinationId();
		Long sourceId = requestRo.getSourceId();
		if (destinationId.equals(sourceId)) {
			response.makeValidationError("The same accounts");
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
		int result = sourceMoney.getAmount().compareTo(requestRo.getMoney());
		if (result == -1) {
			ErrorRo errorRo = response.makeValidationError();
			errorRo.addErrorField("sourceId", "NO_MONEY", "Insufficient funds");
		}

		return response;
	}

	private AccountInfoRo checkAccount(Long sourceId, String fieldName, TransferRegisterResponse response) {
		List<AccountInfoRo> accounts = accountDao.getUserAccounts();
		AccountInfoRo sourceAccount = accounts.stream()
				.filter(account -> account.getId().equals(sourceId))
				.findFirst().orElse(null);
		if (sourceAccount == null) {
			ErrorRo errorRo = response.makeValidationError();
			errorRo.addErrorField(fieldName, "NOT_FOUND", "Account not found");
		}
		return sourceAccount;
	}

}

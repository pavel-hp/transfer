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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

	@Autowired
	private MessageSource messageSource;

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
			makeValidationError("sourceId", "THE_SAME", response);
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
			makeValidationError("sourceId", "CONVERSATION_FORBIDDEN", response);
		}
		int result = sourceMoney.getAmount().compareTo(userMoney);
		if (result == -1) {
			makeValidationError("sourceId", "NO_MONEY", response);
		}


		if (!response.hasError()) {
			transferDao.transfer(source, destination, userMoney);
		}

		return response;
	}

	private AccountInfoRo checkAccount(Long sourceId, String fieldName, TransferRegisterResponse response) {
		AccountInfoRo account = accountDao.findById(sourceId);
		if (account == null) {
			makeValidationError(fieldName, "NOT_FOUND", response);
		}
		return account;
	}

	private void makeValidationError(String field, String errorCode, TransferRegisterResponse response) {
		ErrorRo errorRo = response.makeValidationError();
		String message = messageSource.getMessage("transfer.register." + errorCode, null, LocaleContextHolder.getLocale());
		errorRo.addErrorField(field, errorCode, message);
	}

}

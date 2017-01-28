package com.khokhlov.component.transfer.manager.impl;

import com.khokhlov.component.transfer.dao.api.AccountDao;
import com.khokhlov.component.transfer.manager.api.TransferManager;
import com.khokhlov.rest.request.transfer.TransferRegisterRequestRo;
import com.khokhlov.rest.response.transfer.TransferPrepareResponse;
import com.khokhlov.rest.response.transfer.TransferRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Khokhlov Pavel
 */
@Component
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
		return response;
	}

}

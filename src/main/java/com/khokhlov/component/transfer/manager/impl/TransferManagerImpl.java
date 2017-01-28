package com.khokhlov.component.transfer.manager.impl;

import com.khokhlov.component.transfer.manager.api.TransferManager;
import com.khokhlov.rest.response.transfer.TransferPrepareResponse;
import org.springframework.stereotype.Component;

/**
 * @author Khokhlov Pavel
 */
@Component
public class TransferManagerImpl implements TransferManager {

	@Override
	public TransferPrepareResponse prepare() {
		TransferPrepareResponse response = new TransferPrepareResponse();
		return response;
	}

}

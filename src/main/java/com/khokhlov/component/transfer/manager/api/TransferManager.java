package com.khokhlov.component.transfer.manager.api;

import com.khokhlov.rest.request.transfer.TransferRegisterRequestRo;
import com.khokhlov.rest.response.transfer.TransferPrepareResponse;
import com.khokhlov.rest.response.transfer.TransferRegisterResponse;

import javax.validation.Valid;

/**
 * @author Khokhlov Pavel
 */
public interface TransferManager {

	TransferPrepareResponse prepare();

	TransferRegisterResponse transfer(@Valid TransferRegisterRequestRo requestRo);
}

package com.khokhlov.component.transfer.controller;

import com.khokhlov.common.controller.UrlConstants;
import com.khokhlov.component.transfer.manager.api.TransferManager;
import com.khokhlov.rest.response.transfer.TransferPrepareResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Khokhlov Pavel
 */
@RestController
public class TransferController {

	@Autowired
	private TransferManager manager;

	@RequestMapping(path = UrlConstants.PREPARE)
	@GetMapping
	public TransferPrepareResponse prepareTransfer() {
		return manager.prepare();
	}

}

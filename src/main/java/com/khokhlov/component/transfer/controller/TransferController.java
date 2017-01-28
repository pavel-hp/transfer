package com.khokhlov.component.transfer.controller;

import com.khokhlov.common.util.UrlConstants;
import com.khokhlov.component.transfer.manager.api.TransferManager;
import com.khokhlov.rest.request.transfer.TransferRegisterRequestRo;
import com.khokhlov.rest.response.transfer.TransferPrepareResponse;
import com.khokhlov.rest.response.transfer.TransferRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.khokhlov.common.util.ControllerUtil.wrapResponse;

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

	@RequestMapping(path = UrlConstants.REGISTER)
	@PostMapping
	public ResponseEntity<TransferRegisterResponse> registerTransfer(@RequestBody TransferRegisterRequestRo requestRo) {
		return wrapResponse(manager.transfer(requestRo));
	}


}

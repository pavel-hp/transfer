package com.khokhlov.rest.response.transfer;

import com.khokhlov.rest.model.account.AccountInfoRo;
import com.khokhlov.rest.response.common.AbstractResponseRo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Khokhlov Pavel
 */
public class TransferPrepareResponse extends AbstractResponseRo {

	private static final long serialVersionUID = 8446052111937282837L;

	private List<AccountInfoRo> sourceAccounts = new ArrayList<>();
	private List<AccountInfoRo> destinationAccounts = new ArrayList<>();

	public List<AccountInfoRo> getSourceAccounts() {
		return sourceAccounts;
	}

	public void setSourceAccounts(List<AccountInfoRo> sourceAccounts) {
		this.sourceAccounts = sourceAccounts;
	}

	public List<AccountInfoRo> getDestinationAccounts() {
		return destinationAccounts;
	}

	public void setDestinationAccounts(List<AccountInfoRo> destinationAccounts) {
		this.destinationAccounts = destinationAccounts;
	}
}

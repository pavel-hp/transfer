package com.khokhlov.component.transfer.dao.api;

import com.khokhlov.rest.model.account.AccountInfoRo;

import java.util.List;

/**
 * @author Khokhlov Pavel
 */
public interface AccountDao {
	List<AccountInfoRo> getUserAccounts();

	AccountInfoRo findById(Long id);
}

package com.khokhlov.component.transfer.dao.impl;

import com.khokhlov.component.transfer.dao.api.AccountDao;
import com.khokhlov.rest.model.account.AccountInfoRo;
import com.khokhlov.rest.model.money.CurrencyRo;
import com.khokhlov.rest.model.money.MoneyRo;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Khokhlov Pavel
 */
@Repository
public class AccountDaoImpl implements AccountDao {

	enum Currency {
		RUB("\u20BD"), EUR("€"), USD("$");
		private String symbol;

		Currency(String symbol) {
			this.symbol = symbol;
		}
	}

	@Override
	public List<AccountInfoRo> getUserAccounts() {
		return getDummyData();
	}

	public static List<AccountInfoRo> getDummyData() {
		List<AccountInfoRo> accounts = new ArrayList<>();
		accounts.add(getSourceAccount(1L, "Накопительный", "40817810555555555111", Currency.RUB));
		accounts.add(getSourceAccount(2L, "Экспресс", "40817840555555555444", Currency.RUB));
		accounts.add(getSourceAccount(3L, "Счета для USD открытого вклада", "40817840555555555222", Currency.USD));
		accounts.add(getSourceAccount(4L, "Счета для EUR открытого вклада", "40817840555555555333", Currency.EUR));
		return accounts;
	}

	static AccountInfoRo getSourceAccount(Long id, String name, String account, Currency currency) {
		AccountInfoRo accountInfoRo = new AccountInfoRo();
		accountInfoRo.setAccountName(name);
		accountInfoRo.setAccountNumber(account);
		accountInfoRo.setId(id);
		accountInfoRo.setAvailableAmount(makeMoney("2000", currency));
		return accountInfoRo;
	}

	static MoneyRo makeMoney(String value, Currency currency) {
		MoneyRo moneyRo = new MoneyRo();
		moneyRo.setAmount(new BigDecimal(value));
		moneyRo.setCurrency(makeCurrency(currency));
		return moneyRo;
	}

	static CurrencyRo makeCurrency(Currency currency) {
		CurrencyRo currencyRo = new CurrencyRo();
		currencyRo.setCode(currency.name());
		currencyRo.setScale(2);
		currencyRo.setSymbol(currency.symbol);
		return currencyRo;
	}

}

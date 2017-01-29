package com.khokhlov;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.common.util.UrlConstants;
import com.khokhlov.component.transfer.dao.api.AccountDao;
import com.khokhlov.component.transfer.dao.impl.AccountDaoMemory;
import com.khokhlov.rest.request.transfer.TransferRegisterRequestRo;
import com.khokhlov.rest.response.common.ErrorCode;
import com.khokhlov.rest.response.common.FieldErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = TransferApplication.class)
public class TransferApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AccountDao accountDao;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void checkPrepare() throws Exception {

		given(accountDao.getUserAccounts()).willReturn(AccountDaoMemory.getDummyData());

		mvc.perform(get(UrlConstants.PREPARE).accept(MediaType.APPLICATION_JSON))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sourceAccounts", hasSize(4)))
				.andExpect(jsonPath("$.destinationAccounts", hasSize(4)));
	}

	@Test
	public void checkErrorHandle() throws Exception {
		// check error handle
		mvc.perform(post(UrlConstants.REGISTER))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(HttpServletResponse.SC_INTERNAL_SERVER_ERROR))
				.andExpect(jsonPath("$.error.code", is(ErrorCode.REQUEST_PARAMETER_CONFLICT.name())));


		mvc.perform(post(UrlConstants.REGISTER).accept(MediaType.APPLICATION_JSON))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(HttpServletResponse.SC_INTERNAL_SERVER_ERROR))
				.andExpect(jsonPath("$.error.code", is(ErrorCode.REQUEST_PARAMETER_CONFLICT.name())));


		mvc.perform(post(UrlConstants.REGISTER).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new TransferRegisterRequestRo())))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is(HttpServletResponse.SC_INTERNAL_SERVER_ERROR))
				.andExpect(jsonPath("$.error.code", is(ErrorCode.REQUEST_PARAMETER_CONFLICT.name())));

		mvc.perform(post(UrlConstants.REGISTER).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new TransferRegisterRequestRo())))
				.andExpect(status().is(HttpServletResponse.SC_BAD_REQUEST))
				.andExpect(jsonPath("$.error.code", is(ErrorCode.VALIDATION.name())));

	}

	@Test
	public void registerFailedTheSameAccounts() throws Exception {
		TransferRegisterRequestRo requestRo = new TransferRegisterRequestRo();
		requestRo.setDestinationId(1L);
		requestRo.setSourceId(1L);
		requestRo.setMoney(new BigDecimal("200.23"));
		mvc.perform(post(UrlConstants.REGISTER).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestRo)))
				.andExpect(status().is(HttpServletResponse.SC_BAD_REQUEST))
				.andExpect(jsonPath("$.error.code", is(ErrorCode.VALIDATION.name())))
				.andExpect(jsonPath("$.error.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.error.fieldErrors[0].fieldCode", is("sourceId")))
				.andExpect(jsonPath("$.error.fieldErrors[0].errorCode", is("THE_SAME")))
				.andExpect(jsonPath("$.error.fieldErrors[0].message", is("The same accounts")));
	}

	@Test
	public void registerFailedConversationForbidden() throws Exception {
		given(accountDao.findById(1L)).willReturn(AccountDaoMemory.getRurSaving());
		given(accountDao.findById(3L)).willReturn(AccountDaoMemory.getUsd());

		TransferRegisterRequestRo requestRo = new TransferRegisterRequestRo();
		requestRo.setDestinationId(1L);
		requestRo.setSourceId(3L);
		requestRo.setMoney(new BigDecimal("200.23"));
		mvc.perform(post(UrlConstants.REGISTER).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestRo)))
				.andExpect(status().is(HttpServletResponse.SC_BAD_REQUEST))
				.andExpect(jsonPath("$.error.code", is(ErrorCode.VALIDATION.name())))
				.andExpect(jsonPath("$.error.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.error.fieldErrors[0].fieldCode", is("sourceId")))
				.andExpect(jsonPath("$.error.fieldErrors[0].errorCode", is("CONVERSATION_FORBIDDEN")))
				.andExpect(jsonPath("$.error.fieldErrors[0].message", is("Conversation not allowed")));

	}

	@Test
	public void registerFailedInsufficientFunds() throws Exception {
		given(accountDao.findById(1L)).willReturn(AccountDaoMemory.getRurSaving());
		given(accountDao.findById(2L)).willReturn(AccountDaoMemory.getRurExpress());

		TransferRegisterRequestRo requestRo = new TransferRegisterRequestRo();
		requestRo.setDestinationId(1L);
		requestRo.setSourceId(2L);
		requestRo.setMoney(new BigDecimal("2000.1"));
		mvc.perform(post(UrlConstants.REGISTER).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestRo)))
				.andExpect(status().is(HttpServletResponse.SC_BAD_REQUEST))
				.andExpect(jsonPath("$.error.code", is(ErrorCode.VALIDATION.name())))
				.andExpect(jsonPath("$.error.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.error.fieldErrors[0].fieldCode", is("sourceId")))
				.andExpect(jsonPath("$.error.fieldErrors[0].errorCode", is("NO_MONEY")))
				.andExpect(jsonPath("$.error.fieldErrors[0].message", is("Insufficient funds")));
	}

	@Test
	public void registerSuccess() throws Exception {

		given(accountDao.findById(1L)).willReturn(AccountDaoMemory.getRurSaving());
		given(accountDao.findById(2L)).willReturn(AccountDaoMemory.getRurExpress());

		TransferRegisterRequestRo requestRo = new TransferRegisterRequestRo();
		requestRo.setDestinationId(1L);
		requestRo.setSourceId(2L);
		requestRo.setMoney(new BigDecimal("200"));
		mvc.perform(post(UrlConstants.REGISTER).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestRo)))
				.andExpect(status().isOk());
	}

	@Test
	public void testMoneyFormat() throws Exception {
		prepareMoneyTest("0", FieldErrorCode.MIN_MONEY);
		prepareMoneyTest(null, FieldErrorCode.NOT_NULL);
		prepareMoneyTest("-10", FieldErrorCode.MIN_MONEY);
		prepareMoneyTest("-1", FieldErrorCode.MIN_MONEY);
		prepareMoneyTest("0.00", FieldErrorCode.MIN_MONEY);
		prepareMoneyTest("0.034", FieldErrorCode.BAD_FORMAT);
		prepareMoneyTest("102112121220.00", FieldErrorCode.BAD_FORMAT);

	}

	private ResultActions prepareMoneyTest(String money, String code) throws Exception {
		TransferRegisterRequestRo requestRo = new TransferRegisterRequestRo();
		requestRo.setDestinationId(1L);
		requestRo.setSourceId(2L);
		if (money != null) {
			requestRo.setMoney(new BigDecimal(money));
		}
		String expectedMessage = getMessageByFieldErrorCode(code);
		return mvc.perform(post(UrlConstants.REGISTER).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestRo)))
				.andExpect(jsonPath("$.error.code", is(ErrorCode.VALIDATION.name())))
				.andExpect(jsonPath("$.error.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.error.fieldErrors[0].fieldCode", is("money")))
				.andExpect(jsonPath("$.error.fieldErrors[0].errorCode", is(code)))
				.andExpect(jsonPath("$.error.fieldErrors[0].message", is(expectedMessage)));
	}

	private String getMessageByFieldErrorCode(String code) {
		String localizedMessage;
		switch (code) {
			case FieldErrorCode.MIN_MONEY:
				localizedMessage = "Minimum amount exceeded";
				break;
			case FieldErrorCode.BAD_FORMAT:
				localizedMessage = "Unsupported format";
				break;
			case FieldErrorCode.NOT_NULL:
				localizedMessage = "This field cannot be empty";
				break;
			default:
				throw new RuntimeException();
		}
		return localizedMessage;
	}

}

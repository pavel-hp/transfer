package com.khokhlov;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.common.error.ErrorCode;
import com.khokhlov.common.util.UrlConstants;
import com.khokhlov.component.transfer.dao.api.AccountDao;
import com.khokhlov.component.transfer.dao.impl.AccountDaoImpl;
import com.khokhlov.rest.request.transfer.TransferRegisterRequestRo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletResponse;

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
	public void contextLoads() throws Exception {

		given(accountDao.getUserAccounts()).willReturn(AccountDaoImpl.getDummyData());

		mvc.perform(get(UrlConstants.PREPARE).accept(MediaType.APPLICATION_JSON))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sourceAccounts", hasSize(4)))
				.andExpect(jsonPath("$.destinationAccounts", hasSize(4)));
	}

	@Test
	public void errorHandle() throws Exception {
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

	}

	@Test
	public void register() throws Exception {
		mvc.perform(post(UrlConstants.REGISTER).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new TransferRegisterRequestRo())))
				.andExpect(status().isOk());
	}

}

package com.khokhlov;

import com.khokhlov.common.controller.UrlConstants;
import com.khokhlov.component.transfer.controller.TransferController;
import com.khokhlov.component.transfer.manager.api.TransferManager;
import com.khokhlov.rest.response.transfer.TransferPrepareResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransferController.class)
public class TransferApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TransferManager manager;

	@Test
	public void contextLoads() throws Exception {

		given(manager.prepare()).willReturn(new TransferPrepareResponse());

		mvc.perform(get(UrlConstants.PREPARE).accept(MediaType.APPLICATION_JSON))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

}

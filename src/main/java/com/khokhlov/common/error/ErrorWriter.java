package com.khokhlov.common.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khokhlov.rest.response.common.EmptyResponseRo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Khokhlov Pavel
 */
@Component
public class ErrorWriter {

	private static final Logger logger = LoggerFactory.getLogger(ErrorWriter.class);

	@Autowired
	private ObjectMapper objectMapper;

	void write(HttpServletResponse response, EmptyResponseRo responseRo, int status) {
		response.setStatus(status);
		try {
			response.addHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8.toString());
			objectMapper
					.writerFor(EmptyResponseRo.class)
					.writeValue(response.getOutputStream(), responseRo);
		} catch (IOException iox) {
			logger.error("Unknown error: ", iox);
		}
	}
}

package com.khokhlov.rest.request.transfer;

import com.khokhlov.rest.model.RestObject;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static com.khokhlov.rest.response.common.FieldErrorCode.*;


/**
 * @author Khokhlov Pavel
 */
public class TransferRegisterRequestRo implements RestObject {
	private static final long serialVersionUID = -2731857406805352102L;

	@NotNull(message = NOT_NULL)
	private Long sourceId;

	@NotNull(message = NOT_NULL)
	private Long destinationId;

	@NotNull(message = NOT_NULL)
	@Digits(integer = 10, fraction = 2, message = BAD_FORMAT)
	@DecimalMin(value = "0.01", message = MIN_MONEY)
	private BigDecimal money;

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Long getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(Long destinationId) {
		this.destinationId = destinationId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
}

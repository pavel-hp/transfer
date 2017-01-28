package com.khokhlov.rest.request.transfer;

import com.khokhlov.rest.model.RestObject;

import java.math.BigDecimal;

/**
 * @author Khokhlov Pavel
 */
public class TransferRegisterRequestRo implements RestObject {
	private static final long serialVersionUID = -2731857406805352102L;

	private Long sourceId;
	private Long destinationId;
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

package com.khokhlov.common.error;

import org.springframework.core.Ordered;

/**
 * @author Khokhlov Pavel
 */
interface Order {

	int CONSTRAINT = Ordered.HIGHEST_PRECEDENCE + 1000;
	int ANY = CONSTRAINT + 1000;
}

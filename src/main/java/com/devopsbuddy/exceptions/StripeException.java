package com.devopsbuddy.exceptions;

public class StripeException extends RuntimeException {

	private static final long serialVersionUID = -8186942906191667495L;

	public StripeException(Throwable e) {
		super(e);
	}

}

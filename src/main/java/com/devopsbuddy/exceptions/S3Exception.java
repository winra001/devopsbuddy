package com.devopsbuddy.exceptions;

public class S3Exception extends RuntimeException {

	private static final long serialVersionUID = -1705078733908102848L;

	public S3Exception(Throwable e) {
		super(e);
	}

	public S3Exception(String s) {
		super(s);
	}

}

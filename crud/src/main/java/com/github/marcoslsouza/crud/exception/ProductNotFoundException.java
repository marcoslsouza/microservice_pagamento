package com.github.marcoslsouza.crud.exception;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5860209076175030914L;

	public ProductNotFoundException(String msg) {
		super(msg);
	}
}

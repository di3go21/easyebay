package com.easyebay.upload;

public class StorageException extends RuntimeException{

	public StorageException(String message) {
		super(message);
	}
	
	public StorageException(String mess, Throwable cause) {
		
		super (mess,cause);
	}
	
}

package com.logistic.dao.exceptions;

/**
 * Represents exceptions during DAO operations. 
 *
 * @author Mike
 * @version 1.2
 */
public class DAOException extends Exception {
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
	public DAOException(String message) {
		super(message);
	}
	public DAOException(Throwable cause) {
		super(cause);
	}
	public DAOException() {
		super();
	}
}
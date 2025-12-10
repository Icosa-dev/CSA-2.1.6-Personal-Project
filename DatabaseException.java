/*
 * SPDX-License-Identifier: BSD-2-Clause
 *
 * Copyright (c) 2025, LJC
 */

/**
 * Exception class for errors thrown by public methods of the Database class.
 * 
 * @author LJC
 * @version 1.0
 * @since 1.0
 */
public class DatabaseException extends Exception {
    /**
      * Constructs a new {@code DatabaseException} with {@code null} as its detail message.
      * The cause is not initialized and may subsequently be initialized by a call to
      * {@link #initCause(Throwable)}.
      * 
      * @since 1.0
      */
    public DatabaseException() {
        super();
    }

    /**
     * Constructs a new {@code DatabaseException} with the specified detail message.
     * The cause is not initialized and may subsequently be initialized by a call to
     * {@link #initCause(Throwable)}.
     * 
     * @param msg The detail message
     * 
     * @since 1.0 
     */
    public DatabaseException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new {@code DatabaseException} with the specified detail message and cause.
     * 
     * @param msg The detail message
     * @param cause The exception cause
     */
    public DatabaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
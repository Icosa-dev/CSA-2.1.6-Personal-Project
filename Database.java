/*
 * SDPX-License-Identifer: BSD-2-Clause
 *
 * Copyright (c) 2025, LJC
 */

import java.io.File;

/**
 * Database class used to instantiate Database objects.
 * 
 * @author LJC
 * @version 1.0
 * @since 1.0
 */
public class Database {
    // Private Attributes
    private File dbFile;
    private boolean verboseOutput;

    // Public methods 
    /**
     * Database object constructor.
     * 
     * @param dbName Name of the database file excluding the .cvs file extention.
     * @param verboseOutput True to enable verbose logging; false for normal output.
     * 
     * @since 1.0
     */
    Database(String dbName, boolean enableVerboseOutput)
    {
        logSuccess("TEST LOG SUCCESS");
        logInfo("TEST LOG INFO");
        logError("TEST LOG ERROR");
        this.dbFile = new File(dbName + ".cvs");
        this.verboseOutput = enableVerboseOutput;
    }

    /**
     * Creates a key in the database file or, if exists, will updated the existing key.
     * 
     * @param key The name of the key to create.
     * @param val The double value to assign to the key.
     */
    public void create(String key, double val) { }
    
    // Private Methods

    // Logger methods
    // Source - https://stackoverflow.com/a
    // Posted by WhiteFang34, modified by community. See post 'Timeline' for change
    // history
    // Retrieved 2025-12-03, License - CC BY-SA 3.0
    // ANSI Escape Codes for Colors e
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * Logs a successful action.
     * 
     * @param msg The message to log.
     * 
     * @since 1.0 
     */
    private void logSuccess(String msg) {
        if (verboseOutput)
            System.out.println("[" + ANSI_GREEN + "SUCCESS" + ANSI_RESET + "] " + msg);
    }

    /**
     * Logs information.
     * 
     * @param msg The message to log.
     * 
     * @since 1.0
     */
    private void logInfo(String msg) {
        if (verboseOutput) 
            System.out.println("[" + ANSI_YELLOW + "INFO" + ANSI_RESET + "] " + msg);
    }

    /**
     * Logs an error.
     * 
     * @param msg The message to log.
     * 
     * @since 1.0
     */
    private void logError(String msg) {
        if (verboseOutput)
            System.err.println("[" + ANSI_RED + "ERROR" + ANSI_RESET + "] " + msg);
    }
}
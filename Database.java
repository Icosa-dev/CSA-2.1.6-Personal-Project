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
    // Private attributes
    File dbFile;
    boolean verboseOutput;

    /**
     * Database object constructor.
     * 
     * @param dbName Name of the database file excluding the .cvs file extention.
     * @param verboseOutput True to enable verbose logging; false for normal output.
     */
    Database(String dbName, boolean enableVerboseOutput)
    {
        this.dbFile = new File(dbName + ".cvs");
        this.verboseOutput = enableVerboseOutput;
    }
}
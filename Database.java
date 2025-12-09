/*
 * SPDX-License-Identifier: BSD-2-Clause
 *
 * Copyright (c) 2025, LJC
 */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;

public class Database {
    // Source - https://stackoverflow.com/a
    // Posted by WhiteFang34, modified by community. See post 'Timeline' for change
    // history
    // Retrieved 2025-12-03, License - CC BY-SA 3.0
    // ANSI Escape Codes for Colors
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    private File dbFile;
    private boolean outputEnabled;

    Database(String dbName, boolean enableOutput) {
        outputEnabled = enableOutput;
        logInfo("outputEnabled: " + outputEnabled);
        openDbFile(dbName);
    }

    public void openDbFile(String dbName) {
        dbFile = new File(dbName + ".csv");

        if (dbFile.exists()) {
            logInfo("DB File exists.");
            logInfo("File name: " + dbFile.getName());
            logInfo("File path absolute: " + dbFile.getAbsolutePath());
        } else {
            logInfo("File does not exist.");
            try {
                dbFile.createNewFile();
                logSuccess("DB File created.");
                logInfo("File name: " + dbFile.getName());
                logInfo("File path absolute: " + dbFile.getAbsolutePath());
            } catch (Exception e) {
                logError("Error creating file: " + e.getMessage());
            }
        }
    }

    public int deleteDbFile(String filename) {
        File file = new File(filename + ".csv");

        if (file.exists()) {
            String name = file.getName();
            String path = file.getAbsolutePath();
            logInfo("File exists.");
            logInfo("File name: " + name);
            logInfo("File path absolute: " + path);

            if (file.delete())
                logSuccess("Deleted file \"" + name + "\" at \"" + path + "\"");
            else {
                logError("Could not delete file \"" + name + "\" at \"" + path + "\"");
                return -1;
            }
        } else
            logInfo("File does not exist.");
        return 0;
    }

    public int create(String key, double val) {
        if (!dbFile.exists()) {
            logError("Failure to create key: \"" + key + "\". Database file does not exist");
            return -1;
        }

        if (!exists(key)) {
            appendLine(key + "," + val);
            return 0;
        }

        update(key, val);

        return 0;
    }

    public int update(String key, double val) {
        int keyIndex = getKeyIndex(key);

        if (keyIndex == -1) {
            logError("Failure to update key \"" + key + "\". Could not get key index.");
            return -1;
        }

        logInfo("Updating value of key \"" + key + "\"...");
        if (overwriteLine(key + "," + val, keyIndex) == 0)
            logSuccess("Value for key \"" + key + "\" updated. Value set to " + val);
        else
            return -1;

        return 0;
    }

    public boolean exists(String key) {
        if (!dbFile.exists()) {
            logError("Failure to check for key: \"" + key + "\". Database file does not exist");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(dbFile.getName()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (parseString(line).equals(key))
                    return true;
            }
        } catch (IOException e) {
            logError(e.getMessage());
            return false;
        }

        return false;
    }

    public double read(String key) {
        if (!dbFile.exists()) {
            logError("Failure to read key \"" + key + "\". Database file does not exist");
            return -1;
        }        

        try (BufferedReader reader = new BufferedReader(new FileReader(dbFile.getName()))) {
            String currentLine;
            int keyIndex = getKeyIndex(key);
            int currentLineIndex = 1;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLineIndex == keyIndex)
                    return parseDouble(currentLine);
                currentLineIndex++;
            }
        } catch (IOException e) {
            logError(e.getMessage());
            return -1;
        }



        return -1;
    }

    public String dump() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dbFile.getName()))) {
            String line;
            String fileStr = "";
            while ((line = bufferedReader.readLine()) != null) {
                fileStr += line + "\n";
            }
            return fileStr;
        } catch (IOException e) {
            logError(e.getMessage());
            return "";
        }
    }

    private int overwriteLine(String str, int lineIndex) {
        if (!dbFile.exists()) {
            logError("Failure to overwrite line " + lineIndex + ". Database file does not exist");
            return -1;
        }

        logInfo("Overwriting line " + lineIndex);

        String newFileStr = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(dbFile.getName()))) {
            String currentLine;
            int currentLineIndex = 1;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLineIndex == lineIndex)
                    newFileStr += str + "\n";
                else
                    newFileStr += currentLine + "\n";
                currentLineIndex++;
            }
        } catch (IOException e) {
            logError(e.getMessage());
            return -1;
        }

        try (FileWriter writer = new FileWriter(dbFile.getPath())) {
            writer.write(newFileStr);
            logSuccess("Overwritten line " + lineIndex);
        } catch (IOException e) {
            logError(e.getMessage());
            return -1;
        }

        return 0;
    }

    private int appendLine(String str) {
        if (!dbFile.exists()) {
            logError("Failure to append to file \"" + dbFile.getName() + "\" at \"" + dbFile.getAbsolutePath()
                    + "\". Database file does not exist");
            return -1;
        }

        logInfo("Appending to database file...");

        String newFileStr = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(dbFile.getName()))) {
            String currentLine;

            while ((currentLine = reader.readLine()) != null)
                newFileStr += currentLine + "\n";
        } catch (IOException e) {
            logError(e.getMessage());
            return -1;
        }

        try (FileWriter writer = new FileWriter(dbFile.getPath())) {
            writer.write(newFileStr += str);
            logSuccess("Appended string to database file: \"" + str + "\"");
        } catch (IOException e) {
            logError(e.getMessage());
            return -1;
        }

        return 0;
    }

    private int getKeyIndex(String key) {

        if (!dbFile.exists()) {
            logError("Failure to find key index of \"" + key + "\". Database file does not exist.");
            return -1;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(dbFile.getName()))) {
            String line;
            int index = 1;
            while ((line = reader.readLine()) != null) {
                if (parseString(line).equals(key))
                    return index;
                index++;
            }
        } catch (IOException e) {
            logError(e.getMessage());
            return -1;
        }

        return -1;
    }

    public int delete(String key) {
       return overwriteLine("", getKeyIndex(key)); 
    }

    public void printInfo() {
        logInfo("Database name: " + dbFile.getName());
        logInfo("Database path: " + dbFile.getAbsolutePath());
        logInfo("Output enabled? " + outputEnabled);
    }

    private static String parseString(String str) {
        return str.substring(0, str.indexOf(","));
    }

    private static double parseDouble(String str) {
        return Double.parseDouble(str.substring(str.indexOf(",") + 1));
    }

    // Logger methods
    public void enableOutput() {
        outputEnabled = true;
    }

    public void disableOutput() {
        outputEnabled = false;
    }

    private void logInfo(String msg) {
        if (outputEnabled)
            System.out.println("[" + ANSI_YELLOW + "INFO" + ANSI_RESET + "] " + msg);
    }

    private void logError(String msg) {
        if (outputEnabled)
            System.err.println("[" + ANSI_RED + "ERROR" + ANSI_RESET + "] " + msg);
    }

    private void logSuccess(String msg) {
        if (outputEnabled)
            System.out.println("[" + ANSI_GREEN + "SUCCESS" + ANSI_RESET + "] " + msg);
    }
}
/*
 * SPDX-License-Identifier: BSD-2-Clause
 *
 * Copyright (c) 2025, LJC
 */

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        print("Database name: ");
        String dbName = sc.nextLine();
        print("Enable verbose output(Y/n) ");
        String ynInput = sc.nextLine();

        // The default is Y so only check for N
        Database db;
        if (ynInput.toLowerCase().equals("n"))
            db = new Database(dbName, false);
        else
            db = new Database(dbName, true);

        while (true) {
            print("Enter operation: ");
            String operation = sc.nextLine();

            switch (operation.toLowerCase()) {
                case "help":
                    println("HELP: Print Help Message");
                    println("INFO: Get info on db");
                    println("CREATE: Create a key");
                    println("READ: Print the value of a key");
                    println("UPDATE: Update the value of a key");
                    println("DELETE KEY: Delete a key");
                    println("OPEN DB: Open db file");
                    println("DELETE DB: Delete db file");
                    println("ENABLE OUTPUT: Enable db output");
                    println("DISABLE OUTPUT: Disable db output");
                    println("DUMP DB: Dump all keys and values");
                    break;

                case "create":
                    print("Enter key name: ");
                    String key = sc.nextLine();
                    print("Enter double value: ");
                    Double val = sc.nextDouble();
                    db.create(key, val);
                    break;

                case "read":
                    print("Enter key name: ");
                    key = sc.nextLine();
                    println("" + db.read(key));
                    break;

                case "update":
                    print("Enter key name: ");
                    key = sc.nextLine();
                    print("Enter new double value: ");
                    val = sc.nextDouble();
                    db.update(key, val);
                    break;

                case "delete key":
                    print("Enter key name: ");
                    key = sc.nextLine();
                    db.delete(key);
                    break;

                case "open db":
                    print("Enter db name: ");
                    db.openDbFile(sc.nextLine());
                    break;

                case "delete db":
                    print("Enter db name: ");
                    db.deleteDbFile(sc.nextLine());
                    break;

                case "info":
                    db.printInfo();
                    break;

                case "enable output":
                    db.enableOutput();
                    break;

                case "disable output":
                    db.disableOutput();
                    break;

                case "dump db":
                    println(db.dump());
                    break;

                case "quit":
                    return;

                default:
                    println("\nInvalid Operation. Try running \"HELP\"");
                    break;
            }
        }
    }

    private static void print(String str) {
        System.out.print(str);
    }

    private static void println(String str) {
        System.out.println(str);
    }
}
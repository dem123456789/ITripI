/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.cs2340.ITripCS2340.Controller;

import edu.gatech.cs2340.ITripCS2340.Model.Account;
import edu.gatech.cs2340.ITripCS2340.Model.List;
import java.sql.*;

/**
 *
 * @author lipeilin
 */
public class SQLCtrl {

    private static String myPath = "";

    public static void main(String[] args) {
        //init();
        //System.out.println("Password check result =
        // " + checkPassword("AAA", "aab"));
        //System.out.println("Username check result =
        // " + checkIfUserExists("AAA"));
        //changeUserName("AAA", "BBB");
        //changePassword("BBB", "1231231232");
        printTable();
    }

    public static void printTable() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"
                    + myPath + "/test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ACCM;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                System.out.println("ID = " + id);
                System.out.println("USERNAME = " + username);
                System.out.println("PASSWORD = " + password);
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("PRINT TABLE done successfully");
    }

    /**
     * Only call init() for the first time. Create or connect to the database
     * and auto generate an ACCM table
     */
    public static void init(String path) {
        System.out.println("Start initialization...");
        myPath = path;
        Connection c = null;
        Statement stmt = null;
        try {
            //Register the JDBC driver
            Class.forName("org.sqlite.JDBC");
            //Connect to an existing database; if it doesnt exist,
            // create a new one
            c = DriverManager.getConnection("jdbc:sqlite:"
                    + myPath + "/test.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE ACCM "
                    + "(ID INTEGER PRIMARY KEY NOT NULL,"
                    + " USERNAME TEXT NOT NULL,"
                    + " PASSWORD INTEGER NOT NULL,"
                    + " ITINERARY TEXT NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static void createAccount(String user, String pass) {
        Connection c = null;
        Statement stmt = null;
        try {
            Account acc = new Account(user, pass);
            //Connect to an existing database; if it doesnt exist,
            // create a new one
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"
                    + myPath + "/test.db");
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO ACCM VALUES "
                    + "('%d', '%s', '%d', '%s');", acc.getHashedUserName(),
                    acc.getUserName(), acc.getHashedPassword(),
                    acc.getItinerary().toJSON());
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static void deleteAccount(String user) {
        Connection c = null;
        Statement stmt = null;
        try {
            //Connect to an existing database; if it doesnt exist,
            // create a new one
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"
                    + myPath + "/test.db");
            stmt = c.createStatement();
            String sql = String.format("DELETE FROM ACCM WHERE ID = '%d'",
                    Account.fnvHash(user));
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //System.out.println(String.format("%s is created successfully", user));

    }

    public static Account getAccount(String user) {
        Connection c = null;
        Statement stmt = null;
        Account account;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"
                    + myPath + "/test.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM"
                    + " ACCM WHERE ID='%d';", Account.fnvHash(user)));
            int pass = Integer.parseInt(rs.getString("PASSWORD"));
            String list = rs.getString("ITINERARY");
            account = new Account(user, pass, List.getListFromJSON(list));
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            account = new Account("error", "error");
        }
        return account;
    }

    public static boolean checkPassword(String user, String pass) {
        return getAccount(user).checkPassword(pass);
    }

    public static boolean checkIfUserExists(String user) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"
                    + myPath + "/test.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(
                    "SELECT ID FROM ACCM WHERE ID = '%d';",
                    Account.fnvHash(user)));
            boolean result = rs.next();
            rs.close();
            stmt.close();
            c.close();
            return result;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public static void changeUserName(String oldUsername, String newUserName) {
        Connection c = null;
        Statement stmt = null;
        try {
            //Connect to an existing database; if it doesnt exist,
            // create a new one
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"
                    + myPath + "/test.db");
            stmt = c.createStatement();
            String sql = String.format("UPDATE ACCM SET USERNAME = '%s' "
                    + "WHERE ID = '%d'", newUserName,
                    Account.fnvHash(oldUsername));
            stmt.executeUpdate(sql);
            stmt = c.createStatement();
            sql = String.format("UPDATE ACCM SET ID = '%d' "
                    + "WHERE ID = '%d'", Account.fnvHash(newUserName),
                    Account.fnvHash(oldUsername));
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static void changePassword(String user, String newPass) {

        Connection c = null;
        Statement stmt = null;
        try {
            //Connect to an existing database; if it doesnt exist,
            // create a new one
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"
                    + myPath + "/test.db");
            stmt = c.createStatement();
            String sql = String.format("UPDATE ACCM SET PASSWORD = '%d' "
                    + "WHERE ID = '%d'", Account.fnvHash(newPass),
                    Account.fnvHash(user));
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static void changeItinerary(String user, String list) {

        Connection c = null;
        Statement stmt = null;
        try {
            //Connect to an existing database; if it doesnt exist,
            // create a new one
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"
                    + myPath + "/test.db");
            stmt = c.createStatement();
            String sql = String.format("UPDATE ACCM SET ITINERARY = '%s' "
                    + "WHERE ID = '%d'", list,
                    Account.fnvHash(user));
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}

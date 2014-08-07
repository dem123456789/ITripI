package edu.gatech.cs2340.ITripCS2340.Model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class Database {

    private static Dao<Account, String> accountDao;

    public static void setupDatabase(String path) {
        try {
            JdbcConnectionSource connectionSource = new JdbcConnectionSource(
                    "jdbc:sqlite:" + path + "/accounts.db");
            accountDao = DaoManager.createDao(connectionSource, Account.class);
            TableUtils.createTable(connectionSource, Account.class);
        } catch (SQLException e) {
            System.out.println("Error in setup: " + e.getMessage());
        }
    }

    public static void createAccount(String user, String password) {
        try {
            if (!checkIfUserExists(user)) {
                Account account = new Account(user, password);
                account.setItineraryJSON(account.getItinerary().toJSON());
                accountDao.create(account);
            }
        } catch (Exception e) {
            System.out.println("Error in createAccount: " + e.getMessage());
        }
    }

    public static void deleteAccount(String user) {
        try {
            for (Account account : accountDao) {
                if (account.getUserName().equals(user)) {
                    accountDao.delete(account);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in deleteAccount: " + e.getMessage());
        }
    }

    public static Account getAccount(String user) {
        Account result = new Account();
        for (Account account : accountDao) {
            if (account.getUserName().equals(user)) {
                result = account;
                result.setItinerary(List.getListFromJSON(
                        result.getItineraryJSON()));
            }
        }
        return result;
    }

    public static boolean checkPassword(String user, String pass) {
        for (Account account : accountDao) {
            if (account.getUserName().equals(user)) {
                if (account.checkPassword(pass)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkIfUserExists(String user) {

        for (Account account : accountDao) {
            if (account.getUserName().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public static void changeUserName(String oldUsername, String newUserName) {
        try {
            for (Account account : accountDao) {
                if (account.getUserName().equals(oldUsername)) {
                    accountDao.updateId(account, newUserName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in changeUsername: " + e.getMessage());
        }
    }

    public static void changePassword(String user, String newPass) {
        try {
            for (Account account : accountDao) {
                if (account.getUserName().equals(user)) {
                    account.setPassword(newPass);
                    accountDao.update(account);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in changePassword: " + e.getMessage());
        }
    }

    public static void changeItinerary(String user, List itinerary) {
        try {
            for (Account account : accountDao) {
                if (account.getUserName().equals(user)) {
                    account.setItineraryJSON(itinerary.toJSON());
                    accountDao.update(account);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in changeItinerary: " + e.getMessage());
        }
    }

    public static void changeItinerary(String user, String itinerary) {
        try {
            for (Account account : accountDao) {
                if (account.getUserName().equals(user)) {
                    account.setItineraryJSON(itinerary);
                    accountDao.update(account);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in changeItinerary: " + e.getMessage());
        }
    }

    public static void changeHomeBase(String user, String homeBase) {
        try {
            for (Account account : accountDao) {
                if (account.getUserName().equals(user)) {
                    account.setHomeBase(homeBase);
                    accountDao.update(account);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in changeHomeBase: " + e.getMessage());
        }
    }

}

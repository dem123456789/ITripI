package edu.gatech.cs2340.ITripCS2340.Model;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

/**
 * This class wraps up Username and Password inside a Hashtable.
 * It use serialization to output and input file so that the server
 * can store the usernames and passwords. Usernames is Key, Password
 * is Value.
 * Created by Dreamsoul on 2014/6/8.
 * @author Dreamsoul
 * @version 1.0
 */
public class Hash {

    private Hashtable<Username, Password> data;
    private String relativePath;


    /**
     * The constructor for Hash.
     * It input file if existed otherwise initialize a empty hashtable
     * and output serialized file.
     * @param path  The path where store the serialized hashtable
     */
    public Hash(String path) throws IOException {
        relativePath = path;
        try {
            inputFile();
        } catch (IOException e) {
            data = new Hashtable<Username, Password>();
            outputFile();
        }
    }

    /**
     * This method adds username and password
     * as an account. It will input file each time to refresh
     * the data. It will output data after it adds the account
     * @param username  the Key in hashtable
     * @param password  the Value in hashtable
     */
    public void addAccount(Username username, Password password)
            throws IOException {
        try {
            inputFile();
        } catch (IOException e) {
            return;
        }
        if (password != null && username != null
                && !containsUsername(username)) {
            data.put(username, password);
            outputFile();
        }
    }

    /**
     * This method remove an account based on username and password
     * It will input file each time to refresh
     * the data. It will output data after it removes the account
     * @param username  the Key in hashtable
     * @param password the Value in hashtable
     */
    public void removeAccount(Username username, Password password)
            throws IOException {
        try {
            inputFile();
        } catch (IOException e) {
            return;
        }
        if (username != null && containsUsername(username)) {
            if (checkCorrectPassword(username, password)) {
                data.remove(username);
                outputFile();
            }
        }
    }

    /**
     * This method checks if the data contains given password
     * @param password  the password to be checked existence
     * @return           if contains the password
     */
    public boolean containsPassword(Password password) {
        if (password != null) {
            return data.containsValue(password);
        }
        return false;
    }

    /**
     * This method checks if the data contains given username
     * @param username  the username to be checked existence
     * @return           if contains the username
     */
    public boolean containsUsername(Username username) {
        if (username != null) {
            return data.containsKey(username);
        }
        return false;
    }

    /**
     * This method checks if the username and password matches
     * @param username  the username to be checked
     * @param password  the password to be checked existence
     * @return           if username and password matches
     */
    public boolean checkCorrectPassword(Username username,
                                        Password password) {
        try {
            inputFile();
        } catch (IOException e) {
            return false;
        }
        if (!containsUsername(username)) {
            return false;
        }
        return data.get(username).equals(password);
    }

    /**
     * This method outputs the file and stores it
     * inside the relativePath. It uses Serialization,
     * and the output is tmp.ser.
     */
    private void outputFile() throws IOException {
        FileOutputStream fileOut =
                new FileOutputStream(relativePath + "/tmp.ser");
        ObjectOutputStream output = new ObjectOutputStream(fileOut);
        output.writeObject(data);
        output.close();
        fileOut.close();
        System.out.println("Serialized data is saved in /tmp.ser");
    }

    /**
     * This method inputs the file inside the relativePath.
     * It uses Serialization, and the data is refreshed
     */
    @SuppressWarnings("unchecked")
    private void inputFile() throws IOException {
        FileInputStream fileIn = new FileInputStream(relativePath
                + "/tmp.ser");
        ObjectInputStream input = new ObjectInputStream(fileIn);
        try {
            data = (Hashtable) input.readObject();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        input.close();
        fileIn.close();
    }
}

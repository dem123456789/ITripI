package edu.gatech.cs2340.ITripCS2340.Model;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
/**
 * Created by Dreamsoul on 2014/6/8.
 */
public class Hash {

    private Hashtable<Username, Password> data;
    private String relativePath;

    public Hash(String path) throws IOException {
        relativePath = path;
        try {
            inputFile();
        } catch (IOException e) {
            data = new Hashtable<Username, Password>();
            outputFile();
        }

    }

    public void addAccount(Username username, Password password)
            throws IOException {
        try {
            inputFile();
        } catch (IOException e) {
            return;
        }
        if (password != null && username != null &&
                !containsUsername(username)) {
            data.put(username, password);
            outputFile();
        }
    }

    public void removeAccount(Username username) throws IOException {
        try {
            inputFile();
        } catch (IOException e) {
            return;
        }
        if (username != null && containsUsername(username)) {
            data.remove(username);
            outputFile();
        }
    }

    public boolean containsPassword(Password password) {
        if (password != null) {
            return data.containsValue(password);
        }
        return false;
    }

    public boolean containsUsername(Username username) {
        if (username != null) {
            return data.containsKey(username);
        }
        return false;
    }

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

    private void outputFile() throws IOException {
        FileOutputStream fileOut =
                new FileOutputStream(relativePath + "/tmp.ser");
        ObjectOutputStream output = new ObjectOutputStream(fileOut);
        output.writeObject(data);
        output.close();
        fileOut.close();
        System.out.println("Serialized data is saved in /tmp.ser");
    }

    @SuppressWarnings("unchecked")
    private void inputFile() throws IOException {
        FileInputStream fileIn = new FileInputStream(relativePath +
                "/tmp.ser");
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
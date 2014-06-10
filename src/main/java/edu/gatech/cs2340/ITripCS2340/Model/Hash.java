package edu.gatech.cs2340.ITripCS2340.Model;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
/**
 * Created by Dreamsoul on 2014/6/8.
 */
public class Hash {

    private Hashtable<Username, Password> data;
    private String relativePath;

    public Hash(String path) throws IOException {
        relativePath=path;
        try {
            inputFile();
        } catch (IOException e) {
                data = new Hashtable<Username, Password>();
                outputFile();
        }

    }

    public void addAccount(Username username, Password password) throws IOException {
        if (password != null && username != null && !containsUsername(username)) {
            data.put(username, password);
            outputFile();
        }
    }

    public void removeAccount(Username username) throws IOException {
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

    public boolean checkCorrectPassword(Username username, Password password) {
        if(!containsUsername(username)) {
            return false;
        }
        return data.get(username).equals(password);
    }

    private void outputFile() throws IOException {
        FileOutputStream stream = new FileOutputStream(relativePath+"/tmp.xml");
        XMLEncoder encoder = new XMLEncoder(stream);
        encoder.writeObject(data);
        encoder.close();
        stream.close();
    }

    @SuppressWarnings("unchecked")
    private void inputFile() throws IOException {
            FileInputStream stream = new FileInputStream(relativePath+"/tmp.xml");
            XMLDecoder decoder = new XMLDecoder(stream);
            data = (Hashtable) decoder.readObject();
            decoder.close();
            stream.close();
    }
}
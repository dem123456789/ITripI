package edu.gatech.cs2340.ITripCS2340.Controller;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
/**
 * Created by Dreamsoul on 2014/6/8.
 */
public class Hash {

    private Hashtable<Password, Username> data;

    public Hash() throws IOException {
        inputFile();
        data = new Hashtable<Password, Username>();
    }

    public void addAccount(Password password, Username username) throws IOException {
        if (password != null && username != null && !containsUsername(username)) {
            data.put(password, username);
            outputFile();
        }
    }

    public void removeAccount(Password password) throws IOException {
        if (password != null && containsPassword(password)) {
            data.remove(password);
            outputFile();
        }
    }
    public boolean containsPassword(Password password) {
        if (password != null) {
            return data.containsKey(password);
        }
        return false;
    }

    public boolean containsUsername(Username username) {
        if (username != null) {
            return data.containsValue(username);
        }
        return false;
    }

    private void outputFile() throws IOException {
        FileOutputStream stream = new FileOutputStream("tmp.xml");
        XMLEncoder encoder = new XMLEncoder(stream);
        encoder.writeObject(data);
        encoder.close();
        stream.close();
    }

    @SuppressWarnings("unchecked")
    private void inputFile() throws IOException {
        FileInputStream stream = new FileInputStream("tmp.xml");
        XMLDecoder decoder = new XMLDecoder(stream);
        data = (Hashtable) decoder.readObject();
        decoder.close();
        stream.close();
    }
}

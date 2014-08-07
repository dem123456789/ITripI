package edu.gatech.cs2340.ITripCS2340.Model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * Created by Jonathan on 6/9/2014.
 */
@RunWith(JUnit4.class)
public class HashTest {
    @Test
    public void create() {
        try {
            Hash table = new Hash();
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void addAccount() {
        try {
            Hash table = new Hash();
            table.addAccount(new Password("asdf"), new Username("asdf"));
        } catch (IOException ex) {
            ex.printStackTrace();
            fail();
        }
    }
    @Test
    public void checkadd() {
        try {
            Hash table = new Hash();
            table.addAccount(new Password("asdf"), new Username("asdf"));
            assertEquals(true, table.containsUsername(new Username("asdf")));
            assertEquals(true, table.containsPassword(new Password("asdf")));
        } catch (IOException ex) {
            ex.printStackTrace();
            fail();
        }
    }
}

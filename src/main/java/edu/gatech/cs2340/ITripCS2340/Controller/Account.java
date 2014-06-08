package edu.gatech.cs2340.ITripCS2340.Controller;

/**
 * Created by Dreamsoul on 2014/6/8.
 */
public class Account {
    private String username;
    private String password;

    public Account(String title, String task) {
        this.username = username;
        this.password = password;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getUserName() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int hashCode(){
        int result = 31;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password  != null ? password.hashCode() : 0);
        return result;
    }
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        	// object must be Test at this point
        Account test = (Account)obj;
        return username.equals(test.username)  || (password != null && password.equals(test.password));
    }
}

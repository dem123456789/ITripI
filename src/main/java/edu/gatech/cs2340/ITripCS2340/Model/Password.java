package edu.gatech.cs2340.ITripCS2340.Model;

/**
 * Created by Dreamsoul on 2014/6/8.
 */
public class Password implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 5649792483568634900L;
    private String password;

    public Password(String password) {
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
       return password;
    }
    public int hashCode(){
        int result = 31;
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
        Password test = (Password)obj;
        return password != null && password.equals(test.password);
    }
}

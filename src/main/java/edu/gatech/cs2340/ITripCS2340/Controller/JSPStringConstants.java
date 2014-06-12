package edu.gatech.cs2340.ITripCS2340.Controller;

/**
 * Created by Jonathan on 6/11/2014.
 */
public class JSPStringConstants {
    //vars
    public static final String USERNAME_PARAM = "Username";
    public static final String NEW_USERNAME_PARAM = "NewUsername";
    public static final String PASSWORD_PARAM = "Password";
    public static final String CONFIRM_PASSWORD_PARAM = "ConformationPass";

    //flags
    public static final String MANAGE_ACCOUNT_FLAG = "ManageAccount";
    public static final String CHANGE_USERNAME_FLAG = "ChangeUsername";
    public static final String CHANGE_PASSWORD_FLAG = "ChangePassword";
    public static final String GO_BACK_FLAG = "GoBack";

    //error flags
    public static final String USERNAME_NOT_FOUND_ERROR = "UsernameNotFound";
    public static final String USERNAME_TAKEN_ERROR = "UsernameTaken";
    public static final String PASSWORD_INCORRECT_ERROR = "PasswordIncorrect";
    public static final String PASSWORDS_NOT_SAME_ERROR = "PasswordsNotSame";

    //files
    //don't need leading '/' taken care of in code
    public static final String MAIN_JSP = "Main.jsp";
    public static final String LOGIN_JSP = "Login/Login.jsp";
    public static final String REGISTER_JSP = "Login/Register.jsp";
    public static final String MANAGER_JSP = "Account_Management/Manager.jsp";
    public static final String USERNAME_JSP = "Account_Management/Username.jsp";
    public static final String PASSWORD_JSP = "Account_Management/Password.jsp";
}

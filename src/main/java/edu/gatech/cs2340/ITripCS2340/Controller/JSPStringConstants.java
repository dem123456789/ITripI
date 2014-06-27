package edu.gatech.cs2340.ITripCS2340.Controller;

/**
 * Holds String constants for use in servlet-jsp interactions
 * Created by Jonathan on 6/11/2014.
 * @author Jonathan
 * @version 1.0
 */
public class JSPStringConstants {
    //vars
    public static final String USERNAME_PARAM = "Username";
    public static final String NEW_USERNAME_PARAM = "NewUsername";
    public static final String PASSWORD_PARAM = "Password";
    public static final String CONFIRM_PASSWORD_PARAM = "ConformationPass";
    public static final String CENTRAL_LOCATION = "CentralLocation";
    public static final String INTEREST_PLACE = "InterestPlace";
    public static final String DISTANCE = "Distance";
    public static final String LONGTITUDE = "Longtitude";
    public static final String LATITUDE = "Latitude";
    public static final String BUSINESSES = "Businesses";
    public static final String RATING="Rating";

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
    public static final String LOGINREGISTER_CSS = "Style_Sheets/LoginRegister.css";
    public static final String MAP_CSS = "Style_Sheets/Map.css";
    public static final String MAP_JSP = "Map/Map.jsp";
    
    //API keys
    public static final String GOOGLE_API_KEY= 
            "AIzaSyBU9pnplpYBdzrHI1RKgME1F-6VYcGPyzs";
    public static final String YELP_API_KEY="2J2QEjmKSX1rvU45EwhqLQ";
    public static final String YELP_API_SECRET="Vdf2gi6hydJsjV3PNuATETJWXvo";
    public static final String YELP_TOKEN="ez_Vs7hSgVhYXvoK7izJa9asFAORFNZC";
    public static final String YELP_TOKEN_SECRET="_5SGOs6bTXPSj0JDQHLgVyl1HUM";


    //Unit Conversions
    public static final double MetersPerMile = 1609.34;

    public static double milesToMeters(double miles){
        return miles * MetersPerMile;
    }


}

package edu.gatech.cs2340.ITripCS2340.Controller;

/**
 * Holds String constants for use in servlet-jsp interactions Created by
 * Jonathan on 6/11/2014.
 *
 * @author Jonathan
 * @version 1.0
 */
public class JSPStringConstants {

    //vars
    public static final String USERNAME_PARAM = "Username";
    public static final String NEW_USERNAME_PARAM = "NewUsername";
    public static final String PASSWORD_PARAM = "Password";
    public static final String CONFIRM_PASSWORD_PARAM = "ConformationPass";
    public static final String ACCOUNT_PARAM = "Account";
    public static final String CENTRAL_LOCATION = "CentralLocation";
    public static final String INTEREST_PLACE = "InterestPlace";
    public static final String DISTANCE = "Distance";
    public static final String PLACES_FOUND_IN_CENTRAL_LOCATION
            = "PlacesCentralLocation";
    public static final String LONGTITUDE = "Longtitude";
    public static final String LATITUDE = "Latitude";
    public static final String BUSINESSES = "Businesses";
    public static final String PRICE = "Price";
    public static final String STARTTIME = "StartTime";
    public static final String ENDTIME = "EndTime";
    public static final String FOOD_TYPE = "FoodType";
    public static final String RATING = "Rating";
    public static final String ITINERARY_PARAM = "itinerary";
    public static final String BUSINESS_PARAM = "business";

    //Button
    public static final String ADDPOINT = "AddPoint";
    public static final String DELETEPOINT = "DeletePoint";
    public static final String SHAREPOINT = "SharePoint";

    //Misc
    public static final String PLACETOADD = "PlaceToAdd";
    public static final String PLACETODELETE = "PlaceToDelete";
    public static final String ITINERARIESBOX = "ItinerariesBox";
    public static final String SELECTEDPLACES = "SelectedPlaces";

    //flags
    public static final String MANAGE_ACCOUNT_FLAG = "ManageAccount";
    public static final String CHANGE_USERNAME_FLAG = "ChangeUsername";
    public static final String CHANGE_PASSWORD_FLAG = "ChangePassword";
    public static final String GO_BACK_FLAG = "GoBack";
    public static final String LOGOUT_FLAG = "Logout";
    public static final String GO_TO_MAP_FLAG = "GoToMap";
    public static final String SAVEPLACES = "SavePlaces";
    public static final String SAVE_HOME_BASE_FLAG = "SaveHomeBase";
    public static final String SHOW_ROUTE_FLAG = "ShowRoute";
    public static final String GO_TO_YELP_FLAG = "YELLLLLLLLLLLLLLLP";

    //error flags
    public static final String USERNAME_NOT_FOUND_ERROR = "UsernameNotFound";
    public static final String USERNAME_TAKEN_ERROR = "UsernameTaken";
    public static final String PASSWORD_INCORRECT_ERROR = "PasswordIncorrect";
    public static final String PASSWORDS_NOT_SAME_ERROR = "PasswordsNotSame";

    //files
    //don't need leading '/' taken care of in code
    public static final String MAIN_JSP = "/Main.jsp";
    public static final String LOGIN_JSP = "/Login/Login.jsp";
    public static final String REGISTER_JSP = "/Login/Register.jsp";
    public static final String MANAGER_JSP = "/Account_Management/Manager.jsp";
    public static final String USERNAME_JSP
            = "/Account_Management/Username.jsp";
    public static final String PASSWORD_JSP
            = "/Account_Management/Password.jsp";
    public static final String LOGINREGISTER_CSS
            = "/Style_Sheets/LoginRegister.css";
    public static final String MAP_CSS = "/Style_Sheets/Map.css";
    public static final String ROUTE_CSS = "/Style_Sheets/Route.css";
    public static final String MAP_JSP = "/Map/Map.jsp";
    public static final String DISPLAY_ITINERARY_JSP
            = "/Map/DisplayItinerary.jsp";
    public static final String CENTRALLOCATION_JSP = "/Map/CentralLocation.jsp";
    public static final String INDEX_HTML = "/index.html";
    public static final String YELP_JSP = "/Map/Yelp.jsp";
    public static final String YELP_CSS = "/Style_Sheets/Yelp.css";
    public static final String YELP_RESULTS_JSP = "/Map/YelpResults.jsp";

    //API keys
    public static final String GOOGLE_API_KEY
            = "AIzaSyBU9pnplpYBdzrHI1RKgME1F-6VYcGPyzs";
    public static final String GOOGLE_SERVER_KEY
            = "AIzaSyCtLn8udNQjOCy1uA14rduzDR88OxhL2RA";
    public static final String YELP_API_KEY = "2J2QEjmKSX1rvU45EwhqLQ";
    public static final String YELP_API_SECRET = "Vdf2gi6hydJsjV3PNuATETJWXvo";
    public static final String YELP_TOKEN = "ez_Vs7hSgVhYXvoK7izJa9asFAORFNZC";
    public static final String YELP_TOKEN_SECRET
            = "_5SGOs6bTXPSj0JDQHLgVyl1HUM";
    //Unit Conversions
    public static final double MERTERS_PER_MILE = 1609.34;

    public static double milesToMeters(double miles) {
        return miles * MERTERS_PER_MILE;
    }
}

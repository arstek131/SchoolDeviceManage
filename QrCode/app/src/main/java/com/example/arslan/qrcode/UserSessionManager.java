package com.example.arslan.qrcode;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSessionManager {
    // Shared Preferences reference
    SharedPreferences pref;

    //Editor reference for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "AndroidExamplePref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // username address (make variable public to access from outside)
    public static final String KEY_USERNAME = "username";

    // Constructor
    public UserSessionManager(Context context)
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    //Create login session
    public void createUserLoginSession(String name, String username)
    {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing username in pref
        editor.putString(KEY_USERNAME, username);

        // commit changes
        editor.commit();
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails()
    {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user username id
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser()
    {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn()
    {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}

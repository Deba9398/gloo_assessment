package com.derekbaumgartner.glooassessment;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private static final String PREFS_NAME = "com.derekbaumgartner.glooassessment.user_data";
    private static User user;
    private final SharedPreferences mPref;

    private User(Context context) {
        mPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (user == null) {
            user = new User(context);
        }
    }

    public static synchronized User getInstance() {
        if (user == null)
            throw new IllegalStateException("User is not yet instantiated.");
        return user;
    }

    public void setUserCredentials(String firstName, String lastName, String username, String pass) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("FIRST_NAME", firstName);
        editor.putString("LAST_NAME", lastName);
        editor.putString("USERNAME", username);
        editor.putString("PASSWORD", hashString(pass));
        editor.commit();
    }

    public boolean authenticateUser(String username, String pass) {
        // Check if username and pass match existing credentials
        return username.equals(mPref.getString("USERNAME", null)) &&
                hashString(pass).equals(mPref.getString("PASSWORD", null));
    }

    public boolean isUserRegistered() {
        // Check if a username has been registered with the app yet
        return (mPref.getString("USERNAME", null) != null);
    }

    // SHA-1 hashing function and hex conversion methods by Amir Raminfar - stackoverflow.com
    private String hashString(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes("iso-8859-1"), 0, str.length());
            byte[] sha1hash = md.digest();
            return convertToHex(sha1hash);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

}

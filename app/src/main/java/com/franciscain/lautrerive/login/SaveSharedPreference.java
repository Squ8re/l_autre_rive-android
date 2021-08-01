package com.franciscain.lautrerive.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.franciscain.lautrerive.login.PreferencesUtility.LOGGED_IN_PREF;

public class SaveSharedPreference {
    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context : Le contexte de la fonction
     * @param loggedIn: True si l'utilisateur est déjà connecté, faux sinon.
     */
    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    /**
     * Get the Login Status
     * @param context : le contexte de la fonction
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }
}

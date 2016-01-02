package cravebot.work.pearlsantos.cravebot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import cravebot.BuildConfig;

/**
 * Created by elysi on 1/2/2016.
 */
public class CheckingStart {
    private static Context c;

    public CheckingStart(Context context){
        c = context;
    }

    public static void main(String[] args){
        final String PREFS_NAME = "CheckingVersion";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = 0;
        currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            c.startActivity(new Intent(c, MainActivity.class));

        } else if (savedVersionCode == DOESNT_EXIST) {

            // TODO This is a new install (or the user cleared the shared preferences)
            c.startActivity(new Intent(c, InstructionSlides.class));


        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade

        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).commit();

    }


}
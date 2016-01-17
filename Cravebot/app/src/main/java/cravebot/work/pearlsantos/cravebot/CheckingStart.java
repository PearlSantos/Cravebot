package cravebot.work.pearlsantos.cravebot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import cravebot.BuildConfig;
import cravebot.R;

/**
 * Created by elysi on 1/2/2016.
 */
public class CheckingStart extends AsyncTask<Void, Void, Integer> {
    private Context c;
    public static final String PREFS_NAME = "CheckingVersion";
    final String PREF_VERSION_CODE_KEY = "version_code";
    final int DOESNT_EXIST = -1;
    private Activity act;

    final static String WHAT_TO_DO = "NEXT_ACTION";


    public CheckingStart(Context context, Activity a) {
        c = context;
        act = a;
    }

    public void onPreExecute() {


    }

    @Override
    protected Integer doInBackground(Void... params) {
        final int currentVersionCode = BuildConfig.VERSION_CODE;
        SharedPreferences prefs = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Get current version code
        if (prefs == null || prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST) == DOESNT_EXIST) {
            // TODO This is a new install (or the user cleared the shared preferences)
            // return new Intent(c, InstructionSlides.class);
            prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
            return 2;

        } else if (currentVersionCode == prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST)) {
            //Normal run
            return -1;

        } else if (currentVersionCode > prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST)) {
            prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
            return -1;

            // TODO This is an upgrade

        } else return -1;


    }

    @Override
    public void onPostExecute(Integer i) {
        Intent next = new Intent(act, MainActivity.class);
        next.putExtra(WHAT_TO_DO, i);
        next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        act.startActivity(next);
        System.out.println("WHAT NEXT?" + i);
        act.overridePendingTransition(R.anim.pull_in_right,
                R.anim.push_out_left);
        act.finish();

    }

}
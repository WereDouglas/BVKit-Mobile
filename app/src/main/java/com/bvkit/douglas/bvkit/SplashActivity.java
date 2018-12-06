package com.bvkit.douglas.bvkit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bvkit.douglas.bvkit.Helper.ConnectionDetector;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.entity.mime.Header;

public class SplashActivity extends AppCompatActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    private ConnectionDetector cd =new ConnectionDetector(this);
    File file;

        public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/

        util.LoadProfile(this);
        //   Toast.makeText(getApplicationContext(), "Uploading user profile " + util.USER_CONTACT, Toast.LENGTH_LONG).show();

        cd = new ConnectionDetector(this);

        if (!util.USER_CONTACT.isEmpty()) {
            if (cd.isConnectingToInternet()) {

                // Item();
                if (util.SYNC.equalsIgnoreCase("false")) {
                    Toast.makeText(getApplicationContext(), "Uploading user profile " + util.USER_NAME, Toast.LENGTH_LONG).show();
                   // Register();
                }
            }

            Toast.makeText(getApplicationContext(), "welcome " + util.USER_NAME, Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);

        } else {

            Toast.makeText(getApplicationContext(), "welcome " + util.USER_NAME, Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);

        }

    }

    public void Register() {

        SharedPreferences myPrefs = getSharedPreferences(util.PREFS_NAME, 0);

        Toast.makeText(getApplicationContext(), "Submitting information ", Toast.LENGTH_SHORT).show();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("userID", myPrefs.getString("userID", "").toString());
        params.put("name", myPrefs.getString("name", "").toString());
        params.put("email", myPrefs.getString("email", "").toString());
        params.put("contact", myPrefs.getString("contact", "").toString());
        params.put("image", myPrefs.getString("image", "").toString());
        params.put("password", myPrefs.getString("password", "").toString());
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);//upload file to server from here

            util.uploadFile(getApplication().getFilesDir().getPath() + "/" + myPrefs.getString("image", "").toString());
        }

        client.post(util.Url + "mobile/register", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String ret = new String(responseBody);
                // Toast.makeText(getApplicationContext(), " "+ret, Toast.LENGTH_LONG).show();
                try {
                    JSONObject j = new JSONObject(ret);
                    Toast.makeText(getApplicationContext(), j.get("status").toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), j.get("info").toString(), Toast.LENGTH_LONG).show();

                    if (j.get("status").toString().equals("true")) {
                        Toast.makeText(getApplicationContext(), "Welcome commiting your user profile" + util.USER_NAME + " ", Toast.LENGTH_LONG).show();

                        SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(util.PREFS_NAME, 0);
                        SharedPreferences.Editor editor = myPrefs.edit();
                        editor.putString("sync", "true");
                        editor.apply();
                        editor.commit();
                    } else {

                        Toast.makeText(getApplicationContext(), "invalid user: " + j.get("username").toString() + "", Toast.LENGTH_LONG).show();


                    }
                    //  Toast.makeText(getApplicationContext(), "registration successful", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    // TODO Auto-generated catch block

                    System.out.print("data sync Error" + e);

                    System.out.print(ret);
                    e.printStackTrace();

                }


            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();

                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Error:" + statusCode + error.getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}

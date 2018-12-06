package com.bvkit.douglas.bvkit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bvkit.douglas.bvkit.Helper.ConnectionDetector;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imgView;
    TextView nameTxt, ageTxt, locTxt, testTxt;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtEmail;
    private Button kitBtn, visualBtn, testBtn;


    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.header_name);
        txtEmail = (TextView) navHeader.findViewById(R.id.header_email);
        // imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.header_img);

        /**/
        kitBtn = (Button) findViewById(R.id.kit_btn);
        kitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLocation = new Intent(HomeActivity.this, TestActivity.class);
                startActivity(startLocation);

            }
        });

        testBtn = (Button) findViewById(R.id.test_btn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startLocation = new Intent(HomeActivity.this, StripActivity.class);
                startActivity(startLocation);

            }
        });
        visualBtn = (Button) findViewById(R.id.visual_btn);
        visualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLocation = new Intent(HomeActivity.this, SelectActivity.class);
                startActivity(startLocation);

            }
        });

        nameTxt = (TextView) findViewById(R.id.name);
        imgView = (ImageView) findViewById(R.id.preview);
        ageTxt = (TextView) findViewById(R.id.age);
        locTxt = (TextView) findViewById(R.id.location);
        //dealing with the image
        byte[] decodedString = Base64.decode(util.USER_IMAGE, Base64.DEFAULT);
        nameTxt.setText("Name :" + util.USER_NAME);
        ageTxt.setText("Age :" + util.USER_AGE);
        locTxt.setText("Location :" + util.USER_LOC);
        //  Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Bitmap decodedByte = BitmapFactory.decodeFile(getApplicationContext().getFilesDir().getPath() + "/" + util.USER_IMAGE);
        imgView.setImageBitmap(decodedByte);

        loadNavHeader();
    }

    private void loadNavHeader() {
        // name, website
        txtName.setText(util.USER_NAME);
        txtEmail.setText(util.USER_EMAIL);

        Bitmap decodedByte = BitmapFactory.decodeFile(getApplicationContext().getFilesDir().getPath() + "/" + util.USER_IMAGE);
        imgProfile.setImageBitmap(decodedByte);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            SharedPreferences myPrefs = getSharedPreferences(util.PREFS_NAME, 0);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.clear();
            editor.commit();
            File dir1 = new File(getApplicationContext().getFilesDir().getParent() + File.separator);
            if (dir1.isDirectory()) {
                String[] children = dir1.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir1, children[i]).delete();
                }
            }
            Intent myIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(myIntent);// Commit the edits!
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static String stripFile = "strip.json";
    private ConnectionDetector cd =new ConnectionDetector(this);

    private void Download() {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getApplicationContext()," INTERNET CONNECTION REQUIRED.......", Toast.LENGTH_LONG).show();
            return;
        }

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        // params.put("name", util.USER_NAME);
        Log.e("URL", util.Url + "patient/visuals/" + util.USER_ID);
        client.post(util.Url + "patient/visuals/" + util.USER_ID, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.e("DOWNLOADED DATA", String.valueOf(result));
                try {
                    util.saveData(getApplicationContext(), result, stripFile);
                } catch (Exception e) {
                    Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
                }
                Log.e(" SAVING AS ", stripFile);

                //progressDialog.cancel();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                // progressDialog.cancel();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();

                } else {
                    Log.e("ERROR", statusCode + error.getMessage());

                }

            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_test) {
            Intent myIntent = new Intent(HomeActivity.this, StripActivity.class);
            // myIntent.putExtra("key", value); //Optional parameters
            HomeActivity.this.startActivity(myIntent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            SharedPreferences myPrefs = getSharedPreferences(util.PREFS_NAME, 0);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.clear();
            editor.commit();
            File dir1 = new File(getApplicationContext().getFilesDir().getParent() + File.separator);
            if (dir1.isDirectory()) {
                String[] children = dir1.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir1, children[i]).delete();
                }
            }
            Intent myIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(myIntent);// Commit the edits!
            finish();

        } else if (id == R.id.nav_refresh) {
            Download();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

package com.bvkit.douglas.bvkit;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bvkit.douglas.bvkit.Adapter.StripAdapter;
import com.bvkit.douglas.bvkit.Helper.ConnectionDetector;
import com.bvkit.douglas.bvkit.Model.Strip;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import cz.msebera.android.httpclient.entity.mime.Header;

import static java.security.AccessController.getContext;

public class StripActivity extends AppCompatActivity {

    private TextView mTextMessage;
    StripAdapter adapter;

    ArrayList<Strip> strips;
    ListView stripView;
    ViewPager viewPager;
    File files;
    static String stripFile = "strip.json";
    private ConnectionDetector cd =new ConnectionDetector(this);
    public static final String MyPREFERENCES = "MyPrefs";
    EditText inputSearch;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    Intent myIntents = new Intent(StripActivity.this, SelectActivity.class);
                    // myIntent.putExtra("key", value); //Optional parameters
                    StripActivity.this.startActivity(myIntents);
                    return true;
                case R.id.navigation_dashboard:
                    try {
                        mTextMessage.setText(R.string.title_dashboard);
                    }catch (Exception f){}
                    Intent myIntent = new Intent(StripActivity.this, TestActivity.class);
                    // myIntent.putExtra("key", value); //Optional parameters
                    StripActivity.this.startActivity(myIntent);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strip);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        cd = new ConnectionDetector(this);
        files = new File(this.getFilesDir().getPath() + "/" + stripFile);
        inputSearch = (EditText) findViewById(R.id.search);
        if (cd.isConnectingToInternet()) {
            Toast.makeText(getApplicationContext()," DOWNLOADING........", Toast.LENGTH_LONG).show();
            Download();
            try {
                LoadStrip();
            } catch (Exception ex) {

            }
        }else {
            Toast.makeText(getApplicationContext()," NO INTERNET CONNECTION", Toast.LENGTH_LONG).show();
            if (files.exists()) {
                try {
                    LoadStrip();
                } catch (Exception ex) {

                }
            } else {

                    Toast.makeText(getApplicationContext()," NO FILE CAN BE LOADED AT THE MOMENT", Toast.LENGTH_LONG).show();
            }
        }
        strips =  util.stripList;
        stripView = (ListView) findViewById(R.id.listView);
        adapter = new StripAdapter(this, strips);
        stripView.setAdapter(adapter);
        stripView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                strips.get(position);
              final   Strip dataModel= strips.get(position);
                Toast.makeText(getApplicationContext(), "SELECTED " + dataModel.getDate(), Toast.LENGTH_LONG).show();
                Snackbar.make(view, dataModel.getDate()+"\n"+dataModel.getDate()+" API: "+dataModel.getDate(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();


                AlertDialog.Builder builder = new AlertDialog.Builder(StripActivity.this);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to delete this information?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {                        // Do nothing but close the dialog

                        Delete( dataModel.getId());
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });


        inputSearch = (EditText) findViewById(R.id.search);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //adapter.filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });
    }
    public void Delete(String id) {

        String uniqueID = UUID.randomUUID().toString();
        Toast.makeText(StripActivity.this, "Cancelling session information  ", Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id",id);

        client.post(util.Url + "mobile/delete_visual", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String ret = new String(responseBody);
                // Toast.makeText(getApplicationContext(), " "+ret, Toast.LENGTH_LONG).show();
                try {
                    JSONObject j = new JSONObject(ret);
                    Toast.makeText(StripActivity.this, j.get("status").toString(), Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "ERROR" + e, Toast.LENGTH_LONG).show();
                    System.out.print("data sync Error" + e);

                    System.out.print(ret);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode == 404) {
                    Toast.makeText(StripActivity.this, "Requested resource not found", Toast.LENGTH_LONG).show();

                } else if (statusCode == 500) {
                    Toast.makeText(StripActivity.this, "Something went wrong at server end", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(StripActivity.this, "Error:" + statusCode + error.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    private void Download() {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        // params.put("name", util.USER_NAME);
        Log.e("URL", util.Url + "patient/visuals/" +util.USER_ID);
        client.post(util.Url + "patient/visuals/" +util.USER_ID, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);

                Log.e("DOWNLOADED DATA", String.valueOf(result));
                //  details.setText("UPCOMING EVENTS ON TIME SHEET:" + "\n");
                try {
                    util.saveData(getApplicationContext(), result,stripFile);
                } catch (Exception e) {
                    Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
                }
                Log.e(" SAVING AS ",  stripFile);

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


    private void LoadStrip() {

        util.stripList = new ArrayList<Strip>();
        //  routeList = new ArrayList<String>();
        String json = util.getData(this,stripFile);
        // Toast.makeText(getActivity(), "DATA "+ json, Toast.LENGTH_LONG).show();
        try {
            JSONArray jsonArray = new JSONArray(json);
            //Iterate the jsonArray and print the info of JSONObjects
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Strip v = new Strip();
                v.setDate((i+1)+ "."+jsonObject.optString("created").toString());
                v.setId(jsonObject.optString("id").toString());
                v.setLeu(jsonObject.optString("leu").toString());
                v.setNit(jsonObject.optString("nit").toString());
                v.setUro(jsonObject.optString("uro").toString());
                v.setPro(jsonObject.optString("pro").toString());
                v.setPh(jsonObject.optString("ph").toString());
                v.setBlo(jsonObject.optString("blo").toString());
                v.setSg(jsonObject.optString("sg").toString());
                v.setKet(jsonObject.optString("ket").toString());
                v.setBil(jsonObject.optString("bil").toString());
                v.setGlu(jsonObject.optString("glu").toString());
                util.stripList.add(v);

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.print("data sync Error" + e);
            e.printStackTrace();
        }
    }
}

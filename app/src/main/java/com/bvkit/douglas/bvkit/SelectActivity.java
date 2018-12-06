package com.bvkit.douglas.bvkit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bvkit.douglas.bvkit.Helper.ConnectionDetector;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SelectActivity extends AppCompatActivity {

    private  Button nit_1,nit_2,nit_3,nit_4,nit_5,nit_6,nit_7,nit_8;
    private  Button leu_1,leu_2,leu_3,leu_4,leu_5,leu_6,leu_7,leu_8;
    private  Button uro_1,uro_2,uro_3,uro_4,uro_5,uro_6,uro_7,uro_8;
    private  Button pro_1,pro_2,pro_3,pro_4,pro_5,pro_6,pro_7,pro_8;
    private  Button ph_1,ph_2,ph_3,ph_4,ph_5,ph_6,ph_7,ph_8;
    private  Button blo_1,blo_2,blo_3,blo_4,blo_5,blo_6,blo_7,blo_8;
    private  Button sg_1,sg_2,sg_3,sg_4,sg_5,sg_6,sg_7,sg_8;
    private  Button ket_1,ket_2,ket_3,ket_4,ket_5,ket_6,ket_7,ket_8;
    private  Button bil_1,bil_2,bil_3,bil_4,bil_5,bil_6,bil_7,bil_8;
    private  Button glu_1,glu_2,glu_3,glu_4,glu_5,glu_6,glu_7,glu_8;
    EditText inputObservation;
    Button saveBtn,cancelBtn;
    String sessionID ;
    ColorDrawable buttonColor ;
    String hexColor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionID = UUID.randomUUID().toString();
        if(!TextUtils.isEmpty(sessionID)){
            CreateSession();
        }
        /*LEU*/
        leu_1 = (Button) findViewById(R.id.leu_1);
        leu_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("leu","NEGATIVE:"+ Hex((ColorDrawable) leu_1.getBackground()));
            }
        });
        leu_2 = (Button) findViewById(R.id.leu_2);
        leu_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Submit("leu","TRACE 15+-:"+ Hex((ColorDrawable) leu_2.getBackground()));
            }
        });
        leu_3 = (Button) findViewById(R.id.leu_3);
        leu_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("leu","SMALL 70+:"+ Hex((ColorDrawable) leu_3.getBackground()));
            }
        });
        leu_4 = (Button) findViewById(R.id.leu_4);
        leu_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("leu","MODERATE 125 ++:"+ Hex((ColorDrawable) leu_4.getBackground()));
            }
        });
        leu_5 = (Button) findViewById(R.id.leu_5);
        leu_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("leu","LARGE 500 +++:"+ Hex((ColorDrawable) leu_5.getBackground()));
            }
        });

        /*END LEU*/

        /*NIT*/
        nit_1 = (Button) findViewById(R.id.nit_1);
        nit_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("nit","NEGATIVE-:"+ Hex((ColorDrawable) nit_1.getBackground()));
            }
        });
        nit_2 = (Button) findViewById(R.id.nit_2);
        nit_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("nit","POSITIVE:"+ Hex((ColorDrawable) nit_2.getBackground()));
            }
        });
        nit_3 = (Button) findViewById(R.id.nit_3);
        nit_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("nit","POSITIVE:"+ Hex((ColorDrawable) nit_3.getBackground()));
            }
        });
        nit_4 = (Button) findViewById(R.id.nit_4);
        nit_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("nit","POSITIVE:"+ Hex((ColorDrawable) nit_4.getBackground()));
            }
        });
        nit_5 = (Button) findViewById(R.id.nit_5);
        nit_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("nit","POSITIVE:"+ Hex((ColorDrawable) nit_5.getBackground()));
            }
        });
        nit_6 = (Button) findViewById(R.id.nit_6);
        nit_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("nit","POSITIVE:"+ Hex((ColorDrawable) nit_6.getBackground()));
            }
        });
        nit_7 = (Button) findViewById(R.id.nit_7);
        nit_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("nit","POSITIVE:"+ Hex((ColorDrawable) nit_7.getBackground()));
            }
        });
        nit_8 = (Button) findViewById(R.id.nit_8);
        nit_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("nit","POSITIVE:"+ Hex((ColorDrawable) nit_8.getBackground()));
            }
        });

        /*END NIT*/

        /*URO*/
        uro_1 = (Button) findViewById(R.id.uro_1);
        uro_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("uro","NORMAL 0.2 -:"+ Hex((ColorDrawable) uro_1.getBackground()));
            }
        });
        uro_2 = (Button) findViewById(R.id.uro_2);
        uro_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("uro"," + NORMAL 1(17):"+ Hex((ColorDrawable) uro_2.getBackground()));
            }
        });
        uro_3 = (Button) findViewById(R.id.uro_3);
        uro_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("uro","+ 2(35):"+ Hex((ColorDrawable) uro_3.getBackground()));
            }
        });
        uro_4 = (Button) findViewById(R.id.uro_4);
        uro_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("uro"," + 4(70):"+ Hex((ColorDrawable) uro_4.getBackground()));
            }
        });
        uro_5 = (Button) findViewById(R.id.uro_5);
        uro_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("uro","+ 8(140) :"+ Hex((ColorDrawable) uro_5.getBackground()));
            }
        });
        uro_6 = (Button) findViewById(R.id.uro_6);
        uro_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("uro","+ 12(200):"+ Hex((ColorDrawable) uro_6.getBackground()));
            }
        });

        /*END URO*/
        /*pro*/
        pro_1 = (Button) findViewById(R.id.pro_1);
        pro_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("pro","- NEGATIVE:"+ Hex((ColorDrawable) pro_1.getBackground()));
            }
        });
        pro_2 = (Button) findViewById(R.id.pro_2);
        pro_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("pro","+-TRACE 15 (0.15):"+ Hex((ColorDrawable) pro_2.getBackground()));
            }
        });
        pro_3 = (Button) findViewById(R.id.pro_3);
        pro_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("pro","+ 30 (0.3):"+ Hex((ColorDrawable) pro_3.getBackground()));
            }
        });
        pro_4 = (Button) findViewById(R.id.pro_4);
        pro_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("pro"," ++ 100 (1.0):"+ Hex((ColorDrawable) pro_4.getBackground()));
            }
        });
        pro_5 = (Button) findViewById(R.id.pro_5);
        pro_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("pro","+++ 300 (3.0):"+ Hex((ColorDrawable) pro_5.getBackground()));
            }
        });
        pro_6 = (Button) findViewById(R.id.pro_6);
        pro_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("pro","++++ 2000 (20):"+ Hex((ColorDrawable) pro_6.getBackground()));
            }
        });

        /*END pro*/
        /*ph*/
        ph_1 = (Button) findViewById(R.id.ph_1);
        ph_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ph","5.0:"+ Hex((ColorDrawable) ph_1.getBackground()));
            }
        });
        ph_2 = (Button) findViewById(R.id.ph_2);
        ph_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ph","6.0:"+ Hex((ColorDrawable) ph_2.getBackground()));
            }
        });
        ph_3 = (Button) findViewById(R.id.ph_3);
        ph_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ph","6.5:"+ Hex((ColorDrawable) ph_3.getBackground()));
            }
        });
        ph_4 = (Button) findViewById(R.id.ph_4);
        ph_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ph","7.0:"+ Hex((ColorDrawable) ph_4.getBackground()));
            }
        });
        ph_5 = (Button) findViewById(R.id.ph_5);
        ph_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ph","7.5:"+ Hex((ColorDrawable) ph_5.getBackground()));
            }
        });
        ph_6 = (Button) findViewById(R.id.ph_6);
        ph_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ph","8.0:"+ Hex((ColorDrawable) ph_6.getBackground()));
            }
        });
        ph_7 = (Button) findViewById(R.id.ph_7);
        ph_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ph","8.5:"+ Hex((ColorDrawable) ph_6.getBackground()));
            }
        });

        /*END ph*/

        /*blo*/
        blo_1 = (Button) findViewById(R.id.blo_1);
        blo_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("blo"," - NEGATIVE:"+ Hex((ColorDrawable) blo_1.getBackground()));
            }
        });
        blo_2 = (Button) findViewById(R.id.blo_2);
        blo_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("blo","+-TRACE (NON-HEMOLYZED):"+ Hex((ColorDrawable) blo_2.getBackground()));
            }
        });
        blo_3 = (Button) findViewById(R.id.blo_3);
        blo_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("blo","+-MODERATE (NON-HEMOLYZED):"+ Hex((ColorDrawable) blo_3.getBackground()));
            }
        });
        blo_4 = (Button) findViewById(R.id.blo_4);
        blo_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("blo","HEMOLYZED TRACE:"+ Hex((ColorDrawable) blo_4.getBackground()));
            }
        });
        blo_5 = (Button) findViewById(R.id.blo_5);
        blo_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("blo","+ SMALL:"+ Hex((ColorDrawable) blo_5.getBackground()));
            }
        });
        blo_6 = (Button) findViewById(R.id.blo_6);
        blo_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("blo","++ MODERATE:"+ Hex((ColorDrawable) blo_6.getBackground()));
            }
        });
        blo_7 = (Button) findViewById(R.id.blo_6);
        blo_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("blo","+++ LARGE:"+ Hex((ColorDrawable) blo_7.getBackground()));
            }
        });

        /*END blo*/
        /*sg*/
        sg_1 = (Button) findViewById(R.id.sg_1);
        sg_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("sg","1.000:"+ Hex((ColorDrawable) sg_1.getBackground()));
            }
        });
        sg_2 = (Button) findViewById(R.id.sg_2);
        sg_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("sg","1.005:"+ Hex((ColorDrawable) sg_2.getBackground()));
            }
        });
        sg_3 = (Button) findViewById(R.id.sg_3);
        sg_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("sg","1.010:"+ Hex((ColorDrawable) sg_3.getBackground()));
            }
        });
        sg_4 = (Button) findViewById(R.id.sg_4);
        sg_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("sg","1.015:"+ Hex((ColorDrawable) sg_4.getBackground()));
            }
        });
        sg_5 = (Button) findViewById(R.id.sg_5);
        sg_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("sg","1.020:"+ Hex((ColorDrawable) sg_5.getBackground()));
            }
        });
        sg_6 = (Button) findViewById(R.id.sg_6);
        sg_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("sg","1.025:"+ Hex((ColorDrawable) sg_6.getBackground()));
            }
        });
        sg_7 = (Button) findViewById(R.id.sg_7);
        sg_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("sg","1.030:"+ Hex((ColorDrawable) sg_7.getBackground()));
            }
        });

        /*END sg*/
        /*ket*/
        ket_1 = (Button) findViewById(R.id.ket_1);
        ket_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ket","NEGATIVE-:"+ Hex((ColorDrawable) ket_1.getBackground()));
            }
        });
        ket_2 = (Button) findViewById(R.id.ket_2);
        ket_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ket","TRACE 5:"+ Hex((ColorDrawable) ket_2.getBackground()));
            }
        });
        ket_3 = (Button) findViewById(R.id.ket_3);
        ket_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ket","SMALL 15 :"+ Hex((ColorDrawable) ket_3.getBackground()));
            }
        });
        ket_4 = (Button) findViewById(R.id.ket_4);
        ket_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ket","MODERATE 40:"+ Hex((ColorDrawable) ket_4.getBackground()));
            }
        });
        ket_5 = (Button) findViewById(R.id.ket_5);
        ket_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ket","LARGE 80:"+ Hex((ColorDrawable) ket_5.getBackground()));
            }
        });
        ket_6 = (Button) findViewById(R.id.ket_6);
        ket_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("ket","LARGE 160:"+ Hex((ColorDrawable) ket_6.getBackground()));
            }
        });

        /*END ket*/
        /*bil*/
        bil_1 = (Button) findViewById(R.id.bil_1);
        bil_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("bil","NEGATIVE:"+ Hex((ColorDrawable) bil_1.getBackground()));
            }
        });
        bil_2 = (Button) findViewById(R.id.bil_2);
        bil_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("bil","SMALL +:"+ Hex((ColorDrawable) bil_2.getBackground()));
            }
        });
        bil_3 = (Button) findViewById(R.id.bil_3);
        bil_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("bil","MODERATE ++:"+ Hex((ColorDrawable) bil_3.getBackground()));
            }
        });
        bil_4 = (Button) findViewById(R.id.bil_4);
        bil_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("bil","LARGE +++:"+ Hex((ColorDrawable) bil_4.getBackground()));
            }
        });

        /*END bil*/
        /*glu*/
        glu_1 = (Button) findViewById(R.id.glu_1);
        glu_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("glu","NEGATIVE:"+ Hex((ColorDrawable) glu_1.getBackground()));
            }
        });
        glu_2 = (Button) findViewById(R.id.glu_2);
        glu_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("glu","1/10(IR) 100:"+ Hex((ColorDrawable) glu_2.getBackground()));
            }
        });
        glu_3 = (Button) findViewById(R.id.glu_3);
        glu_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("glu","1/4 250:"+ Hex((ColorDrawable) glu_3.getBackground()));
            }
        });
        glu_4 = (Button) findViewById(R.id.glu_4);
        glu_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("glu","1/2 500:"+ Hex((ColorDrawable) glu_4.getBackground()));
            }
        });
        glu_5 = (Button) findViewById(R.id.glu_5);
        glu_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("glu","1 1000:"+ Hex((ColorDrawable) glu_5.getBackground()));
            }
        });
        glu_6 = (Button) findViewById(R.id.glu_6);
        glu_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit("glu","2 or more 2000 or more:"+ Hex((ColorDrawable) glu_6.getBackground()));
            }
        });

        /*END glu*/
        /*Observation*/
        inputObservation = (EditText) findViewById(R.id.observation);
        saveBtn = (Button) findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit("observation",inputObservation.getText().toString());
            }
        });
    }
    private  String Hex(ColorDrawable color){

        hexColor = String.format("#%06X", (0xFFFFFF &  color.getColor()));
        return hexColor;
    }

    public void CreateSession() {

        String uniqueID = UUID.randomUUID().toString();
        Toast.makeText(SelectActivity.this, "Uploading information  ", Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("sessionID",sessionID);
        params.put("patientID", util.USER_ID);

        client.post(util.Url + "mobile/session", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String ret = new String(responseBody);
                // Toast.makeText(getApplicationContext(), " "+ret, Toast.LENGTH_LONG).show();
                try {
                    JSONObject j = new JSONObject(ret);
                    Toast.makeText(SelectActivity.this, j.get("status").toString(), Toast.LENGTH_LONG).show();

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
                    Toast.makeText(SelectActivity.this, "Requested resource not found", Toast.LENGTH_LONG).show();

                } else if (statusCode == 500) {
                    Toast.makeText(SelectActivity.this, "Something went wrong at server end", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(SelectActivity.this, "Error:" + statusCode + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Save");
        builder.setMessage("Do you want to save collected information?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                finish();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cancel();
                // Do nothing
                dialog.dismiss();
                finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    private ConnectionDetector cd =new ConnectionDetector(this);
    public void Submit(String col,String val) {

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getApplicationContext()," INTERNET CONNECTION REQUIRED.......", Toast.LENGTH_LONG).show();
            return;
        }

        String uniqueID = UUID.randomUUID().toString();
        Toast.makeText(SelectActivity.this, "Uploading information ", Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("sessionID",sessionID);
        params.put("patientID", util.USER_ID);
        params.put("col", col);
        params.put("val", val);

        client.post(util.Url + "mobile/submission", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String ret = new String(responseBody);
                // Toast.makeText(getApplicationContext(), " "+ret, Toast.LENGTH_LONG).show();
                try {
                    JSONObject j = new JSONObject(ret);
                    Toast.makeText(SelectActivity.this, j.get("status").toString(), Toast.LENGTH_LONG).show();

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
                    Toast.makeText(SelectActivity.this, "Requested resource not found", Toast.LENGTH_LONG).show();

                } else if (statusCode == 500) {
                    Toast.makeText(SelectActivity.this, "Something went wrong at server end", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(SelectActivity.this, "Error:" + statusCode + error.getMessage(), Toast.LENGTH_LONG).show();

                }
            }


        });

    }

    public void Cancel() {

        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getApplicationContext()," INTERNET CONNECTION REQUIRED.......", Toast.LENGTH_LONG).show();
            return;
        }

        String uniqueID = UUID.randomUUID().toString();
        Toast.makeText(SelectActivity.this, "Cancelling session information  ", Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("sessionID",sessionID);
        params.put("patientID", util.USER_ID);


        client.post(util.Url + "mobile/cancel", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String ret = new String(responseBody);
                // Toast.makeText(getApplicationContext(), " "+ret, Toast.LENGTH_LONG).show();
                try {
                    JSONObject j = new JSONObject(ret);
                    Toast.makeText(SelectActivity.this, j.get("status").toString(), Toast.LENGTH_LONG).show();

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
                    Toast.makeText(SelectActivity.this, "Requested resource not found", Toast.LENGTH_LONG).show();

                } else if (statusCode == 500) {
                    Toast.makeText(SelectActivity.this, "Something went wrong at server end", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(SelectActivity.this, "Error:" + statusCode + error.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }

}

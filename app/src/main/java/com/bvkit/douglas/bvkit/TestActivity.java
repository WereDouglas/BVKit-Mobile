package com.bvkit.douglas.bvkit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.UUID;

public class TestActivity extends AppCompatActivity {

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();
    private ConnectedThread mConnectedThread;
    TextView txtArduino, txtLeu,txtNit;
    Handler h;
    private static final String TAG = "bluetooth2";
    final int RECIEVE_MESSAGE = 1;
    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Button btnOn, btnStop, btnStart, btnRun, btnLeu,btnNit,btnUro,btnPro,btnPH,btnBlo,btnSg,btnKet,btnBil,btnGlu,btnAsc;

    // MAC-address of Bluetooth module (you must edit this line)
    private static String MAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        // util.SESSION_ID = UUID.randomUUID().toString();
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnLeu = (Button) findViewById(R.id.leu);
        btnNit = (Button) findViewById(R.id.nit);
        btnUro   = (Button) findViewById(R.id.uro);
        btnPro = (Button) findViewById(R.id.pro);
        btnPH = (Button) findViewById(R.id.pH);
        btnBlo = (Button) findViewById(R.id.blo);
        btnSg = (Button) findViewById(R.id.sg);
        btnKet = (Button) findViewById(R.id.ket);
        btnBil = (Button) findViewById(R.id.bil);
        btnGlu = (Button) findViewById(R.id.glu);
        btnAsc = (Button) findViewById(R.id.asc);

        btnLeu = (Button) findViewById(R.id.leu);
        txtArduino = (TextView) findViewById(R.id.txtState);
        final ProgressDialog progressDialog = new ProgressDialog(TestActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Setting up.......");
        progressDialog.show();

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            //handle the case where device doesn't support Bluetooth
            txtArduino.setText("Bluetooth not supported on your device");
        } else {
            txtArduino.setText("Initialising process");
            // mConnectedThread.write("1");
            runBluetooth();

            progressDialog.cancel();
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnStart.setEnabled(false);
                btnRun.setEnabled(false);
                mConnectedThread.write("1");
                runBluetooth();
            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnStop.setEnabled(false);
                mConnectedThread.write("0");    // Send "0" via Bluetooth
                //Toast.makeText(getBaseContext(), "Turn off LED", Toast.LENGTH_SHORT).show();
                btnRun.setEnabled(true);
            }
        });
        //  Session();
        //  btnStart.setEnabled(false);
    }

    String currentColor="";

    private void runBluetooth() {
        sb.setLength(0);
        checkBTState();
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:
                        // sb.setLength(0);
                        // if receive massage
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);                 // create string from bytes array
                        sb.append(strIncom);
                       // txtArduino.setText(strIncom);
                        Log.d(TAG, "FULL MSG: #" + sb.toString() + "");
                        // Log.d(TAG, "The int color" + Integer.parseInt(strIncom, 16) + "");

                        //currentColor = "";
                        int endOfLineIndex = sb.indexOf("~");                            // determine the end-of-line
                        if (endOfLineIndex > 0) {                                            // if end-of-line,

                            try {
                                // DecimalFormat f = new DecimalFormat("##.000");
                                txtArduino.setTextColor(Color.BLUE);
                                String sbprint = sb.substring(0, endOfLineIndex);               // extract string
                                sb.delete(0, sb.length());
                                Log.d("FULL STRING :", sbprint.toString());// and clear
                                txtArduino.setText("Color results:" + sbprint.toString());
                                DevelopStrip(sbprint.toString());
                               // DevelopStripPosition(sbprint.toString());
                                String[] colors = sbprint.split("-");

                                try {
                                    txtArduino.setTextColor(Color.parseColor(colors[2].toString()));
                                    //Log.e("COLOR:",colors[2].toString());

                                }catch (Exception g) {

                                    Log.d(TAG, "Exception in Else: " + g + "");
                                }
                              //  String CurrentString = sbprint.toString();


                            } catch (Exception g) {

                                Log.d(TAG, "Exception in IF: " + g + "");
                            }
                            // btnOff.setEnabled(true);
                            //sb.setLength(0);
                        } else {

                            String[] strips = sb.toString().split(",");

                            for (String s: strips ) {
                                //Do your stuff here
                                System.out.println(s);
                                Log.e("STRIP :", s);
                                txtArduino.setText("STRIP:" + s.toString());
                                String[] colors = s.split("-");

                                try {
                                   txtArduino.setTextColor(Color.parseColor(colors[2].toString()));
                                    //Log.e("COLOR:",colors[2].toString());

                                }catch (Exception g) {

                                    Log.d(TAG, "Exception in Else: " + g + "");
                                }
                            }
                            try {
                                Log.d(" NO DATA ", "...String:" + sb.toString());



                                // txtArduino.setText("Data values not enough:");
                              //  txtArduino.setTextColor(Color.GRAY);

                               // DevelopStrip(sb.toString());
                                DevelopStripPosition(sb.toString());

                            }catch (Exception g) {

                                Log.d(TAG, "Exception in Else: " + g + "");
                            }
                            // btnOff.s
                            //sb.setLength(0);
                        }
                        //Log.d(TAG, "...String:"+ sb.toString() +  "Byte:" + msg.arg1 + "...");
                        break;

                }
            }

            ;
        };
    }

    private void DevelopStripPosition(String source){

        String[] separated = source.split("-");
        txtArduino.setTextColor(Color.parseColor(separated[2].toString()));

        if(separated[0].equals("0")){

            btnLeu.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("LUE :", separated[0].toString() + " "+separated[1].toString());
        }
        if(separated[0].equals("1")){

            btnNit.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[0].equals("2")){

            btnUro.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[0].equals("3")){

            btnPro.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[0].equals("4")){

            btnPH.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[0].equals("5")){

            btnBlo.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[0].equals("6")){

            btnSg.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[0].equals("7")){

            btnKet.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[0].equals("8")){

            btnBil.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[0].equals("9")){

            btnGlu.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[0].equals("10")){

            btnAsc.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
    }

    private void DevelopStrip(String source){

        String[] separated = source.split("-");
        txtArduino.setTextColor(Color.parseColor(separated[2].toString()));

        if(separated[1].equals("LEU")){

            btnLeu.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("LUE :", separated[0].toString() + " "+separated[1].toString());
        }
        if(separated[1].equals("NIT")){

            btnNit.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[1].equals("URO")){

            btnUro.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[1].equals("PRO")){

            btnPro.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[1].equals("pH")){

            btnPH.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[1].equals("BLO")){

            btnBlo.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[1].equals("SG")){

            btnSg.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[1].equals("KET")){

            btnKet.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[1].equals("BIL")){

            btnBil.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[1].equals("GLU")){

            btnGlu.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
        if(separated[1].equals("ASC")){

            btnAsc.setBackgroundColor(Color.parseColor(separated[2].toString()));
            Log.d("NIT :", separated[0].toString() +" "+ separated[1].toString());
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        if (Build.VERSION.SDK_INT >= 10) {
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});

                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection", e);
            }
        }
        return device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "...onResume - try connect...");


        //    MAC = "00:15:83:35:60:95";
        MAC = "98:D3:31:FB:15:3F";


        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(MAC);
        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting...");
        try {
            btSocket.connect();
            Log.d(TAG, "....Connection ok...");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Create Socket...");

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "...In onPause()...");

        try {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }


    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if (btAdapter == null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private void errorExit(String title, String message) {
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);        // Get number of bytes and message in "buffer"
                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();     // Send to message queue Handler
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }
    }


}

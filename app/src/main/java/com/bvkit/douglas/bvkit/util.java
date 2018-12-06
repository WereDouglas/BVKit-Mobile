package com.bvkit.douglas.bvkit;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bvkit.douglas.bvkit.Model.Strip;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


public class util {


    //public static String Url = "http://10.0.0.251/bvkit/index.php/";
   // public static String UploadUrl = "http://10.0.0.251/bvkit/uploads/uploading.php";
   // public static String DownloadUrl = "http://10.0.0.251/bvkit/uploads/";

    public static String Url = "http://bvkit.net/index.php/";
    public static String UploadUrl = "http://bvkit.net/uploads/uploading.php";
    public static String DownloadUrl = "http://bvkit.net/uploads/";

    public static final String PREFS_NAME = "MyPrefsFile";
    public static String USER_NAME;
    public static String USER_IMAGE;
    public static String USER_CONTACT;
    public static String USER_ID;
    public static String USER_EMAIL;
    public static String LAST_SYNC;
    public static String USER_AGE;
    public static String USER_LOC;
    public static String SYNC;
    public static String RECOMMENDATION;
    public static ArrayList<Strip> stripList;


    //192.168.1.196
    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }

    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    static Integer serverResponseCode;
    static String Upload_Image_URL = util.UploadUrl;

    public static void uploadFile(String imagePath) {

        Log.e("uploadFile", "Source File   :" + imagePath);
        String fileName = imagePath;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(imagePath);

        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File not exist :" + imagePath);
        } else {
            Log.e("Uploading process ", "Source File exists :" + imagePath);
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(Upload_Image_URL);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.e("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Upload file to server", "error: " + e.getMessage(), e);
            }

        } // End else block
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {

        if (!sourceFile.exists()) {
            return;
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    public static void LoadProfile(Context context) {

        SharedPreferences myPrefs = context.getSharedPreferences(util.PREFS_NAME, 0);
        util.USER_NAME = myPrefs.getString("name", "").toString();
        util.USER_ID = myPrefs.getString("userID", "").toString();
        util.USER_CONTACT = myPrefs.getString("contact", "").toString();
        util.USER_AGE = myPrefs.getString("age", "").toString();
        util.USER_LOC = myPrefs.getString("location", "").toString();
        util.USER_EMAIL = myPrefs.getString("email", "").toString();
        util.USER_IMAGE = myPrefs.getString("image", "").toString();
        util.SYNC = myPrefs.getString("sync", "").toString();

    }
    /**Download image and save to application root directory **/
    public static void DownloadImage(Context context, String DownloadUrl, String fileName) {

        File dir = new File(context.getFilesDir().getPath() + "/");
        URL url = null;
        try {
            url = new URL(DownloadUrl + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Connection error ", "URL error " + url + "" + e.getMessage());
        }
        File file = new File(dir, fileName);
        long startTime = System.currentTimeMillis();
        Log.e("DownloadManager", "download url:" + url);
        Log.e("DownloadManager", "download file name:" + fileName);

        URLConnection uconn = null;
        try {
            uconn = url.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Connection error ", "connection error" + e.getMessage());
        }
        uconn.setReadTimeout(5000);
        uconn.setConnectTimeout(5000);

        InputStream is = null;
        try {
            is = uconn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Connection error ", "stream error " + e.getMessage());
        }
        BufferedInputStream bufferinstream = new BufferedInputStream(is);
        ByteArrayBuffer baf = new ByteArrayBuffer(5000);
        int current = 0;
        try {
            while ((current = bufferinstream.read()) != -1) {
                baf.append((byte) current);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Connection error ", "buffer stream error" + e.getMessage());
        }
        int dotindex;
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(baf.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Connection error ", "file output stream error" + e.getMessage());
        }
        Log.e("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + "sec");
        dotindex = fileName.lastIndexOf('.');
        if (dotindex >= 0) {
            fileName = fileName.substring(0, dotindex);
        }
    }

    public static void saveData(Context context, String mJsonResponse, String fileName) {
        try {
            FileWriter file = new FileWriter(context.getFilesDir().getPath() + "/" + fileName);
            file.write("");
            file.write(mJsonResponse);
            Log.e("SAVING STRING ", mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
        }

    }
    public static String getData(Context context,String fileName) {
        try {
            File f = new File(context.getFilesDir().getPath() + "/" + fileName);
            //check whether file exists
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}

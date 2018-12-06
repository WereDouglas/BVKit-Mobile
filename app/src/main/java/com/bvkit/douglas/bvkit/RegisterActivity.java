package com.bvkit.douglas.bvkit;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.Manifest.permission.READ_CONTACTS;
import static android.os.Build.ID;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView,_nameText,_contactText,_emailText,_passwordText,_confirmText,_ageText;
    private View mProgressView;
    private View mLoginFormView;
    private TextView  login;
    Button btnPickImage,btnSnap,_signupButton;
    String mediaPath;
    String encodedImage;
    ImageView imgView;
    Spinner spinner_gender;
    private static final int CAMERA_REQUEST = 1888;
    private static final String TAG = "RegisterActivity";
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
         _nameText = (EditText) findViewById(R.id.input_name);
       _contactText = (EditText) findViewById(R.id.input_contact);
        _ageText = (EditText) findViewById(R.id.input_age);
       _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _confirmText = (EditText) findViewById(R.id.input_password_confirm);

        login = (TextView) findViewById(R.id.link_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
        btnPickImage = (Button) findViewById(R.id.pick_img);
        btnSnap = (Button) findViewById(R.id.snap_img);
        imgView = (ImageView) findViewById(R.id.preview);
        _signupButton = (Button) findViewById(R.id.btn_signup);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
        btnSnap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        _signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signup();
            }
        });


        spinner_gender = (Spinner) findViewById(R.id.gender_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_purchased = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter_purchased.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_gender.setAdapter(adapter_purchased);
        spinner_gender.setOnItemSelectedListener(this);

    }
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        Spinner spinner = (Spinner) adapterView;
         if (spinner.getId() == R.id.gender_spinner) {
             gender = adapterView.getItemAtPosition(pos).toString();
         }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    String filename;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri tempUri= null;
        File finalFile = null;
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgView.setImageBitmap(photo);

            tempUri = getImageUri(RegisterActivity.this.getApplicationContext(), photo);
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile = new File(getRealPathFromURI(tempUri));

            System.out.println(finalFile);
            mediaPath =getRealPathFromURI(tempUri);

          /*  Bitmap bm = BitmapFactory.decodeFile(mediaPath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);*/
        }
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                // Get the Image from data
                tempUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = RegisterActivity.this.getContentResolver().query(tempUri,filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                imgView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                finalFile = new File(getRealPathFromURI(tempUri));
               /* Bitmap bm = BitmapFactory.decodeFile(mediaPath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);*/
                cursor.close();
            } else {
                Toast.makeText(RegisterActivity.this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
        try {
            filename = finalFile.getName();
        } catch (Exception p) {
            imgView.buildDrawingCache();
            Bitmap bitmap = imgView.getDrawingCache();
            imgView.setImageBitmap(bitmap);
            tempUri = getImageUri(getApplicationContext().getApplicationContext(), bitmap);
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile = new File(getRealPathFromURI(tempUri));
            filename =finalFile.getName();
            Log.e("try catching new name ", filename);
        }

        imagePath = getApplicationContext().getFilesDir().getPath() + "/" + filename ;
        File f = new File(imagePath);
        if (!f.exists())
        {
            try {
                f.createNewFile();
                util.copyFile(finalFile, f);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    String imagePath = "";
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Process incomplete", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }
    public void signup() {

        Log.d(TAG, "Signup");
        if (!validate()) {
            onSignupFailed();
            return;
        }
        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        //Register();
                        Submit();
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        // finish();
    }
    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String contact = _contactText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirm = _confirmText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (confirm.isEmpty() || !confirm.equals(password)) {
            _confirmText.setError("Passwords do not match ");
            valid = false;
        } else {
            _confirmText.setError(null);
        }

        if (contact.isEmpty() || contact.length() < 3 || !android.text.TextUtils.isDigitsOnly(contact)) {
            _contactText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void Register() {

        if(TextUtils.isEmpty(filename)){

            Uri tempUri= null;
            File finalFile = null;
            imgView.buildDrawingCache();
            Bitmap bitmap = imgView.getDrawingCache();

            imgView.setImageBitmap(bitmap);
            tempUri = getImageUri(getApplicationContext().getApplicationContext(), bitmap);
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile = new File(getRealPathFromURI(tempUri));
            filename =finalFile.getName();
            Log.e("try catching new name ", filename);
            imagePath = getApplicationContext().getFilesDir().getPath() + "/" + filename;
            File f = new File(imagePath);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    util.copyFile(finalFile, f);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        _signupButton.setEnabled(false);
        //Toast.makeText(getApplicationContext(), "Submitting your information ", Toast.LENGTH_SHORT).show();
        String uniqueID = UUID.randomUUID().toString();
        SharedPreferences myPrefs = getApplicationContext().getSharedPreferences(util.PREFS_NAME, 0);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putBoolean("hasLoggedIn", true);
        editor.putString("email",_emailText.getText().toString() + "");
        editor.putString("name", _nameText.getText().toString());
        editor.putString("contact",_contactText.getText().toString());
        editor.putString("gender",gender);
        editor.putString("age",_ageText.getText().toString());
        editor.putString("userID", uniqueID);
        //  editor.putString("image",  encodedImage);
        editor.putString("image", filename);
        editor.putString("password",  _passwordText.getText().toString());
        editor.putString("sync","false");
        editor.apply();
        editor.commit();
        try {
            util.LoadProfile(this);
            Intent startLocation = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(startLocation);
            finish();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();
        }
    }

    public void Submit() {

        if(TextUtils.isEmpty(filename)){
            Uri tempUri= null;
            File finalFile = null;
            imgView.buildDrawingCache();
            Bitmap bitmap = imgView.getDrawingCache();

            imgView.setImageBitmap(bitmap);
            tempUri = getImageUri(getApplicationContext().getApplicationContext(), bitmap);
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile = new File(getRealPathFromURI(tempUri));
            filename =finalFile.getName();
            Log.e("try catching new name ", filename);
            imagePath = getApplicationContext().getFilesDir().getPath() + "/" + filename;
            File f = new File(imagePath);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    util.copyFile(finalFile, f);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        String uniqueID = UUID.randomUUID().toString();
        Toast.makeText(RegisterActivity.this, "Uploading information  ", Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("email",_emailText.getText().toString() + "");
        params.put("name", _nameText.getText().toString());
        params.put("contact",_contactText.getText().toString());
        params.put("id", uniqueID);
        params.put("gender",gender);
        params.put("age",_ageText.getText().toString());
        params.put("image", filename);
        params.put("password",  _passwordText.getText().toString());
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);//upload file to server from here

            util.uploadFile(getApplication().getFilesDir().getPath() + "/" +filename.toString());
        }
        client.post(util.Url + "mobile/register", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String ret = new String(responseBody);
                // Toast.makeText(getApplicationContext(), " "+ret, Toast.LENGTH_LONG).show();
                try {
                    JSONObject j = new JSONObject(ret);
                    Toast.makeText(RegisterActivity.this, j.get("status").toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(RegisterActivity.this, j.get("info").toString(), Toast.LENGTH_LONG).show();

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
                    Toast.makeText(RegisterActivity.this, "Requested resource not found", Toast.LENGTH_LONG).show();

                } else if (statusCode == 500) {
                    Toast.makeText(RegisterActivity.this, "Something went wrong at server end", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(RegisterActivity.this, "Error:" + statusCode + error.getMessage(), Toast.LENGTH_LONG).show();

                }
            }


        });

    }

}


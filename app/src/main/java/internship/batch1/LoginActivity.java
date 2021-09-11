package internship.batch1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    TextView signup;
    EditText email, password;
    Button login_btn;

    SharedPreferences sharedPreferences;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(ConstantURL.PREF,MODE_PRIVATE);
        signup = findViewById(R.id.signup);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(LoginActivity.this, SignupActivity.class);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().trim().equals("")){
                    email.setError("Email Id Required!!");
                }
                else if(!email.getText().toString().trim().matches(emailPattern)){
                    email.setError("Valid Email Id Required!!");
                }
                else if(password.getText().toString().trim().equals("")){
                    password.setError("Password Required!!");
                }
                else if(password.getText().toString().matches(passwordPattern)){
                   password.setError("Strong Password Required!!");
                }
                else{
                    if (new ConnectionDetector(LoginActivity.this).isConnectingToInternet()){
                            new loginData().execute();
                    }
                    else {
                        new ConnectionDetector(LoginActivity.this).connectiondetect();
                    }
                    /*if(email.getText().toString().trim().equals("admin@gmail.com") && password.getText().toString().trim().equals("11ADMIN@007")){
                        System.out.println("Login Successfully");
                        Log.d("LOGIN","Login Successfully");
                        Log.e("LOGIN", "Login Successfully");

                        new CommonMethod(LoginActivity.this,"Login Successfully"); //Toast
                        //new CommonMethod(view,"Login Successfully"); //Snackbar
                        new CommonMethod(LoginActivity.this, HomeActivity.class); //Intent
                    }
                    else{
                        new CommonMethod(LoginActivity.this, "Login Unsuccessfully");//Toast
                        //new CommonMethod(view,"Login Unsuccessfully"); //Snackbar
                    }*/
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            //onBackPressed();
            alertMethod();
        }
        return super.onOptionsItemSelected(item);
    }

    private void alertMethod() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

        builder.setTitle("Exit Alert!");
        builder.setIcon(R.drawable.ic_baseline_error);
        builder.setMessage("Are You Sure Want To Exit?");

        builder.setPositiveButton(Html.fromHtml("<font color='#0C8F5B'>Yes</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

        builder.setNegativeButton(Html.fromHtml("<font color='#A50F0F'>No</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Rate Us", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new CommonMethod(LoginActivity.this,"Rate Us");
                dialog.dismiss();;
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        alertMethod();
    }

    private class loginData extends AsyncTask<String,String,String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("email",email.getText().toString());
            hashMap.put("password",password.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantURL.url+"login.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("Status")==true){
                    new CommonMethod(LoginActivity.this,jsonObject.getString("Message"));
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        sharedPreferences.edit().putString(ConstantURL.ID,object.getString("id")).commit();
                        sharedPreferences.edit().putString(ConstantURL.NAME,object.getString("name")).commit();
                        sharedPreferences.edit().putString(ConstantURL.EMAIL,object.getString("email")).commit();
                        sharedPreferences.edit().putString(ConstantURL.CONTACT,object.getString("contact")).commit();
                        sharedPreferences.edit().putString(ConstantURL.PASSWORD,object.getString("password")).commit();
                        sharedPreferences.edit().putString(ConstantURL.DOB,object.getString("dob")).commit();
                        sharedPreferences.edit().putString(ConstantURL.GENDER,object.getString("gender")).commit();
                        sharedPreferences.edit().putString(ConstantURL.STATE,object.getString("state")).commit();
                        sharedPreferences.edit().putString(ConstantURL.CITY,object.getString("city")).commit();

                        new CommonMethod(LoginActivity.this,HomeActivity.class);
                    }
                }
                else {
                    new CommonMethod(LoginActivity.this,jsonObject.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
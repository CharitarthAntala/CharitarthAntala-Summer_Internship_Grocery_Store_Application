package internship.batch1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText name,email,contact,password,confirmPassword,dob;
    ImageView dob_iv,showPassword_iv,hidePassword_iv,showConfirmPassword,hideConfirmPassword;
    Spinner cityspinner,statespinner;
    Button signup_btn;
    TextView login_tv;
    RadioGroup gender;
    Calendar calendar;

    String sGender,sCity,sState;

    ArrayList<String> cityArrayList;
    ArrayList<String> stateArrayList;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        contact = findViewById(R.id.signup_contact);
        password = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.signup_confirmpassword);
        dob = findViewById(R.id.signup_dob);
        dob_iv = findViewById(R.id.signup_calender);
        cityspinner = findViewById(R.id.signup_city);
        statespinner = findViewById(R.id.signup_state);
        signup_btn = findViewById(R.id.signup_button);
        login_tv = findViewById(R.id.signup_login);
        gender = findViewById(R.id.signup_gender);
        hidePassword_iv = findViewById(R.id.signup_hide_password);
        showPassword_iv = findViewById(R.id.signup_show_password);
        hideConfirmPassword = findViewById(R.id.signup_hide_confrimpassword);
        showConfirmPassword = findViewById(R.id.signup_show_confirmpassword);


        hidePassword_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword_iv.setVisibility(View.VISIBLE);
                hidePassword_iv.setVisibility(View.GONE);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        showPassword_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePassword_iv.setVisibility(View.VISIBLE);
                showPassword_iv.setVisibility(View.GONE);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        hideConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmPassword.setVisibility(View.VISIBLE);
                hideConfirmPassword.setVisibility(View.GONE);
                confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        showConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideConfirmPassword.setVisibility(View.VISIBLE);
                showConfirmPassword.setVisibility(View.GONE);
                confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });


        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = gender.getCheckedRadioButtonId();
                RadioButton rb = findViewById(id);
                sGender = rb.getText().toString();
                new CommonMethod(SignupActivity.this, rb.getText().toString());
            }
        });

        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, dayOfMonth);

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                dob.setText(format.format(calendar.getTime()));
            }
        };

        dob_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(SignupActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.MONTH));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        stateArrayList = new ArrayList<>();
        stateArrayList.add("Gujarat");
        stateArrayList.add("Maharashtra");
        stateArrayList.add("Rajasthan");

        ArrayAdapter stateAdapter = new ArrayAdapter(SignupActivity.this, android.R.layout.simple_list_item_1, stateArrayList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        statespinner.setAdapter(stateAdapter);

        statespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sState = stateArrayList.get(position);
                new CommonMethod(SignupActivity.this, sState);
                setCityData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().equalsIgnoreCase("")){
                    name.setError("Name Required!");
                }
                else if(email.getText().toString().trim().equalsIgnoreCase("")){
                    email.setError("Email Id Required!");
                }
                else if(!email.getText().toString().matches(emailPattern)){
                    email.setError("Valid Email Id Required!");
                }
                else if(contact.getText().toString().trim().equalsIgnoreCase("")){
                    contact.setError("Contact No. Required!");
                }
                else if(password.getText().toString().trim().equalsIgnoreCase("")){
                    password.setError("Password Required!");
                }
                else if(!password.getText().toString().matches(passwordPattern)){
                    password.setError("Strong Password Required!");
                }
                else if(confirmPassword.getText().toString().trim().equalsIgnoreCase("")){
                    confirmPassword.setError("Password Required!");
                }
                else if(!confirmPassword.getText().toString().matches(password.getText().toString())){
                    confirmPassword.setError("Password Does Not Match!");
                }
                else if(dob.getText().toString().equalsIgnoreCase("")){
                    dob.setError("Date of Birth Required!");
                }
                else if(gender.getCheckedRadioButtonId() == -1){
                    new CommonMethod(SignupActivity.this,"Please Select Gender!");
                }
                else{
                    /*new CommonMethod(SignupActivity.this,"Sing Up Successfully");
                    new CommonMethod(SignupActivity.this,LoginActivity.class);*/
                    if (new ConnectionDetector(SignupActivity.this).isConnectingToInternet()){
                            new signupData().execute();
                    }
                    else {
                            new ConnectionDetector(SignupActivity.this).connectiondetect();
                    }                }

            }
        });

        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                new CommonMethod(SignupActivity.this,LoginActivity.class);
            }
        });



    }

    private void setCityData(int position) {
        cityArrayList = new ArrayList<>();

        if(position == 0) {
            cityArrayList.add("Ahmebad");
            cityArrayList.add("Vadodara");
            cityArrayList.add("Surat");
            cityArrayList.add("Anand");
            cityArrayList.add("Test");

            cityArrayList.set(0, "Ahmedabad");
            cityArrayList.remove(4);
        }
        if(position == 1){
            cityArrayList.add("Mumbai");
            cityArrayList.add("Pune");
            cityArrayList.add("Nagpur");
        }
        if(position == 2){
            cityArrayList.add("Jaipur");
            cityArrayList.add("Kota");
            cityArrayList.add("Udaipur");
        }

        ArrayAdapter cityAdapter = new ArrayAdapter(SignupActivity.this, android.R.layout.simple_list_item_1,cityArrayList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        cityspinner.setAdapter(cityAdapter);

        cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCity = cityArrayList.get(position);
                new CommonMethod(SignupActivity.this,sCity);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id== android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private class signupData extends AsyncTask<String,String,String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SignupActivity.this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("username",name.getText().toString());
            hashMap.put("email",email.getText().toString());
            hashMap.put("contact",contact.getText().toString());
            hashMap.put("password",password.getText().toString());
            hashMap.put("dob",dob.getText().toString());
            hashMap.put("gender",sGender);
            hashMap.put("state",sState);
            hashMap.put("city",sCity);
            return new MakeServiceCall().MakeServiceCall(ConstantURL.url+"signup.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("Status")==true){
                    new CommonMethod(SignupActivity.this,jsonObject.getString("Message"));
                    onBackPressed();
                }
                else {
                    new CommonMethod(SignupActivity.this,jsonObject.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
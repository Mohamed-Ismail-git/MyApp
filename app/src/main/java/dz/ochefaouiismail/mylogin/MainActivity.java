package dz.ochefaouiismail.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView Signup;
    TextView LoginBtn;
    EditText email;
    TextInputEditText password;
    private ProgressDialog progressDialog;
    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //variable
        email=findViewById(R.id.ETEMail);
        password=findViewById(R.id.ETPAssword);
        progressDialog = new ProgressDialog(this);
        //initilize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //init textviews
        init();
    }

    public void init (){
        Signup= findViewById(R.id.textViewSignup);
        LoginBtn = findViewById(R.id.loginButton);

        Signup.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, signup.class );
                startActivity(intent);
                finish();
            }
        }
        );
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(Valide()){
                   Toast.makeText(getApplicationContext(),"the requet for login not enable now just valid Enter now...",Toast.LENGTH_LONG).show();
                   Intent intent = new Intent (MainActivity.this, PrincScreen.class );
                   startActivity(intent);
                   finish();
               }


            }
        });


    }
    private void RegisterUsr(){
        Toast.makeText(getApplicationContext(),"secess",Toast.LENGTH_LONG).show();
        progressDialog.setMessage("registering User...");
        progressDialog.show();


    }
    private void RegisterUser(){
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();
        progressDialog.setMessage("registering User...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("success"),Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("email", Email);
                params.put("password", Password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
    private void Register(){
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();
        progressDialog.setMessage("registering User...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
               // Toast.makeText(getApplicationContext(),"login ",Toast.LENGTH_LONG).show();
                JSONObject jsonObject,USER;

                try {
                    jsonObject = new JSONObject(response);
                    USER = jsonObject.getJSONObject("message");
                    if(jsonObject.getBoolean("success")) {
                    SharedPreferences user = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor edit= user.edit();
                    edit.apply();
                    Toast.makeText(getApplicationContext(),"login succes",Toast.LENGTH_LONG).show();
                         startActivity(new Intent(MainActivity.this,PrincScreen.class));
                         finish();

                    }else if(!(jsonObject.getBoolean("success"))){
                        Toast.makeText(getApplicationContext(),"login not succes",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),USER.getString("email"),Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),USER.getString("telephone"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.hide();

               Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
              //  Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
                // Toast.makeText(getApplicationContext(),"login not succes",Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("name",Password );
                params.put("email", Email);
                params.put("password", Password);
               params.put("role", "teacher");
                params.put("telephone", Email);
               params.put("sexe", "male");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
    private boolean Valide(){


        //Validtion formule

        awesomeValidation.addValidation(this,R.id.ETEMail,
                Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        awesomeValidation.addValidation(this,R.id.ETPAssword,
                ".{5,}",R.string.invalid_Password);




        return awesomeValidation.validate();
    }

}

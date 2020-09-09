package dz.ochefaouiismail.mylogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView Signup;
    TextView LoginBtn;
    EditText email;
    Intent intent;
    public SharedPreferences USER ;
    public SharedPreferences.Editor edit;
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
        intent = new Intent (MainActivity.this, PrincScreen.class );
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
                   LoginUser();
                  // Toast.makeText(getApplicationContext(), USER.getString("email","empty"),Toast.LENGTH_LONG).show();

                  // startActivity(intent);
                  // finish();
               }


            }
        });


    }


    private void LoginUser(){
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();
        progressDialog.setMessage("login User...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                JSONObject jsonObject,user;
                try {
                    jsonObject = new JSONObject(response);
                   user=jsonObject.getJSONObject("user");
                     USER = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
                     edit= USER.edit();
                   // edit.putString("token",jsonObject.getString("token"));
                    edit.putString("name",user.getString("name"));
                    edit.putString("email",user.getString("email"));
                    edit.putString("id",user.getString("id"));
                    edit.commit();
                    edit.apply();
                    Toast.makeText(getApplicationContext(), "Bonjour"+"   "+USER.getString("name","empty"),Toast.LENGTH_LONG).show();
                    callintent();
                    //startActivity(intent);
                   // finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
             //   if(!error.getMessage().equals(""))
              //  Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
               // else
                Toast.makeText(getApplicationContext(),"Failed Login \nEmail or Password false !",Toast.LENGTH_LONG).show();


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

    private void callintent() {
        intent.putExtra("name",USER.getString("name",""));
        intent.putExtra("email",USER.getString("email",""));
        intent.putExtra("id",USER.getString("id",""));
        startActivity(intent);
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

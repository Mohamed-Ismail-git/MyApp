package dz.ochefaouiismail.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class signup extends AppCompatActivity {
    int which =0;
    TextView Login;
    TextView SignUpBtn,role,sexe;
    EditText name,email,telephone,cpassword;
    RadioGroup radioGroup;
    RadioButton male,female;
    TextInputEditText password;
    Button choise;
    AwesomeValidation awesomeValidation;
    private ProgressDialog progressDialoge;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.ETName);
        email = findViewById(R.id.ETEmail);
        telephone = findViewById(R.id.ETtelephone);
        role = findViewById(R.id.ETRole);
        sexe= findViewById(R.id.ETSexe);
        radioGroup = findViewById(R.id.Radio);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        password = findViewById(R.id.ETPassword);
        cpassword = findViewById(R.id.ETCpasword);
        choise = findViewById(R.id.role);
        progressDialoge = new ProgressDialog(this);


        //initilize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //init textviews
        init();
    }


    public void init (){
        Login= findViewById(R.id.TVLogin);
        SignUpBtn = findViewById(R.id.SignUpButton);


        Login.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (signup.this, MainActivity.class );
                startActivity(intent);
                finish();
            }
        }
        );
        //sign up button
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Valide()) {
                    RegisterUsers();
                }

            }
        });
        //chek butoun for gender
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexe.setText(male.getText());
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexe.setText(female.getText());
            }
        });
        // choix for role button
        choise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which = 0;
                String[] liste = getResources().getStringArray(R.array.role_item);
                dialog(liste,role);
            }
        });




    }

    private void dialog(final String[] list, final TextView text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose role");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);

        builder.setSingleChoiceItems(list, which, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                which = i;
//               Toast.makeText(SignalLayout.this,"You have selected " +
//                   list[which], Toast.LENGTH_LONG).show();
            }

        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                text.setText("Please choise role");


            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                text.setText(list[which]);


            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private boolean Valide(){
        boolean v;
        v=false;

        //Validtion formule
        awesomeValidation.addValidation(this,R.id.ETName,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.ETtelephone,
                "[0-7]{1}[0-9]{9}$",R.string.invalid_phoneN);
        awesomeValidation.addValidation(this,R.id.ETEmail,
                Patterns.EMAIL_ADDRESS,R.string.invalid_email);
     //   awesomeValidation.addValidation(this,R.id.ETRole,
       //         RegexTemplate.NOT_EMPTY,R.string.invalid_role);
        //awesomeValidation.addValidation(this,R.id.ETSexe,
          //      "Male,Female",R.string.invalid_sexe);
        awesomeValidation.addValidation(this,R.id.ETPassword,
                ".{5,}",R.string.invalid_Password);
        awesomeValidation.addValidation(this,R.id.ETCpasword,
                R.id.ETPassword,R.string.invalid_CPassword);
        if(sexe.getText().toString().equals("male") || sexe.getText().toString().equals("female")){
            if(role.getText().toString().equals("teacher") || role.getText().toString().equals("student")|| role.getText().toString().equals("ATS") ) {
                v = true;
            }else{
                Toast.makeText(getApplicationContext(),R.string.invalid_role,Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),R.string.invalid_sexe,Toast.LENGTH_LONG).show();

        }

    return awesomeValidation.validate() && v;
    }
    private void RegisterUsers(){
        final String Name =name.getText().toString().trim();
        final String Telephone = telephone.getText().toString().trim();
        final String Role = role.getText().toString().trim();
        final String Sexe = sexe.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();
        progressDialoge.setMessage("registering User...");
        progressDialoge.show();

        StringRequest sRequest = new StringRequest(Request.Method.POST, Constants.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialoge.dismiss();
                // Toast.makeText(getApplicationContext(),"login ",Toast.LENGTH_LONG).show();
                JSONObject jsonObject,USER;

                try {
                    jsonObject = new JSONObject(response);
                    USER = jsonObject.getJSONObject("message");
                    if(jsonObject.getBoolean("success")) {
                        SharedPreferences user = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor edit= user.edit();
                        edit.apply();
                        Toast.makeText(getApplicationContext(),"Account created successeful !",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent (signup.this, MainActivity.class );
                        startActivity(intent);
                        finish();

                    }else if(!(jsonObject.getBoolean("success"))){
                       // USER = jsonObject.getJSONObject("errors");
                       // Toast.makeText(getApplicationContext(),USER.getString("email")+"\n"+USER.getString("telephone"),Toast.LENGTH_LONG).show();
                       // Toast.makeText(getApplicationContext(),"login not success",Toast.LENGTH_SHORT).show();
                        if(!USER.getString("email").equals("")){
                            Toast.makeText(getApplicationContext(),USER.getString("email"),Toast.LENGTH_LONG).show();
                        }
                        if(!USER.getString("telephone").equals("")){
                            Toast.makeText(getApplicationContext(),USER.getString("telephone"),Toast.LENGTH_LONG).show();
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialoge.hide();

                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                //  Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
                // Toast.makeText(getApplicationContext(),"login not succes",Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("name",Name );
                params.put("email", Email);
                params.put("password", Password);
                params.put("role", Role);
                params.put("telephone", Telephone);
                params.put("sexe", Sexe);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sRequest);


    }
}
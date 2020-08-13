package dz.ochefaouiismail.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import mx.jesusmartinoza.widget.SweetPassword;

public class signup extends AppCompatActivity {
    int which =0;
    TextView Login;
    TextView SignUpBtn,role,sexe;
    EditText name,email,telephone,cpassword;
    RadioGroup radioGroup;
    RadioButton male,female;
    TextInputEditText password;
    Button choise;


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

    }
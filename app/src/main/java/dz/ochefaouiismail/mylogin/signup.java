package dz.ochefaouiismail.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class signup extends AppCompatActivity {
    TextView Login;
    TextView SignUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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
    }
}
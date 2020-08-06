package dz.ochefaouiismail.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView Signup;
    TextView LoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                startActivity(new Intent(MainActivity.this,PrincScreen.class));
                finish();

            }
        });


    }

}

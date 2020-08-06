package dz.ochefaouiismail.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincScreen extends AppCompatActivity {
    Button Info,Deconnexion,NewSignal ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_princ_screen);
        Info = findViewById(R.id.Info);
        Deconnexion = findViewById(R.id.Deconnexion);
        NewSignal = findViewById(R.id.newsignal);


        NewSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrincScreen.this ,SignalLayout.class));
                finish();
            }
        });
    }

    public void Login(View v) {
        startActivity(new Intent(this, MainActivity.class));

    }

    public void INFO(View v) {
        startActivity(new Intent(this, User_Account.class));

    }

}

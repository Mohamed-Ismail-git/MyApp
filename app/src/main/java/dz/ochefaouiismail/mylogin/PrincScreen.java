package dz.ochefaouiismail.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincScreen extends AppCompatActivity {
    Button MyButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_princ_screen);
        MyButton = findViewById(R.id.button3);
    }

    public void Login(View v) {
        startActivity(new Intent(this, MainActivity.class));

    }

    public void INFO(View v) {
        startActivity(new Intent(this, User_Account.class));

    }
}

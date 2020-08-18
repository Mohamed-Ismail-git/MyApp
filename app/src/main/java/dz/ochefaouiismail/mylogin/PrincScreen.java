package dz.ochefaouiismail.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PrincScreen extends AppCompatActivity {
    Button Info,Deconnexion,NewSignal ;
    String name,email,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_princ_screen);
        Info = findViewById(R.id.Info);
        Deconnexion = findViewById(R.id.Deconnexion);
        NewSignal = findViewById(R.id.newsignal);
        Intent intent = getIntent();
        name=intent.getStringExtra("name");
        email=intent.getStringExtra("email");
        id=intent.getStringExtra("id");
    }

    public void Login(View v) {
        startActivity(new Intent(this, MainActivity.class));

    }

    public void INFO(View v) {
        Intent intent =new Intent(this, User_Account.class);
        intent.putExtra("name",name);
        intent.putExtra("email",email);
        intent.putExtra("id",id);
        startActivity(intent);

    }
    public void Signle(View v) {
        Intent intent = new Intent(this, SignalLayout.class);
        intent.putExtra("name",name);
        intent.putExtra("email",email);
        intent.putExtra("id",id);
        startActivity(intent);
        finish();
    }

}

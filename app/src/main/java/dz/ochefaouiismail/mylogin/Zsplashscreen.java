package dz.ochefaouiismail.mylogin;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Zsplashscreen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;
    //variables
    Animation topAnim, bottomAnim;
    ImageView imageView;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        //Animations
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        imageView = findViewById(R.id.imageView2);
        logo = findViewById(R.id.textView);


        imageView.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Zsplashscreen.this , MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

    }


}

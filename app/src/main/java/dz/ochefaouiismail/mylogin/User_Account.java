package dz.ochefaouiismail.mylogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class User_Account extends AppCompatActivity {
    ImageView IV ;
    private static final int I_P_C=1000;
    private static final int P_C=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__account);

        //Views
        IV=findViewById(R.id.imageView3);

        //hundle boutton click

    }

    public void Click(View v){
        //check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED) {
                //permission not garanted
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show messge
                requestPermissions(permission,P_C);
            }
            else {
                //permission garanted
                pickImageFromGallery();

            }
        }
        else{
            // system is less then marchemallow

        }

    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,I_P_C);

    }


    // handle result of runtime


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // hundle result

        switch (requestCode){
            case P_C: {
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //permission granted
                    pickImageFromGallery();
                }
                else {
                    //permission denied
                    Toast.makeText(this ,"permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((resultCode == RESULT_OK) && (requestCode == I_P_C)){
            IV.setImageURI(data.getData());
        }
    }
}

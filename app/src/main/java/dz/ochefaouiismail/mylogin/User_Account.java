package dz.ochefaouiismail.mylogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Account extends AppCompatActivity {
    CircleImageView IV;
    TextView gallery,camera,name,email;
    String nameU,emailU,idU;
    Uri image_uri;
    int i;
    private static final int I_P_C=1000;
    private static final int P_C=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__account);
        // text view

        gallery = findViewById(R.id.galleryText);
        camera = findViewById(R.id.cameraText);
        name = findViewById(R.id.TVName);
        email = findViewById(R.id.TVEmail);

        //Views
        IV=findViewById(R.id.UserImage);

        //hundle boutton click
        //get data for user
        Intent intent = getIntent();
        nameU=intent.getStringExtra("name");
        emailU=intent.getStringExtra("email");
        idU=intent.getStringExtra("id");
        //write data
        name.setText(nameU);
        email.setText(emailU);

    }
    public void Princscreen(View v) {
        Intent intent =new Intent(this, PrincScreen.class);
        intent.putExtra("name",nameU);
        intent.putExtra("email",emailU);
        intent.putExtra("id",idU);
        startActivity(intent);

    }

    public void Click(View v){
        i=0;
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



    public void CameraF(View v){
        i=1;
        //check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED||checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED) {
                //permission not garanted
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.CAMERA };
                //show messge
                requestPermissions(permission,P_C);
            }
            else {
                //permission garanted
                pickImageFromCamera();

            }
        }
        else{
            // system is less then marchemallow

        }

    }

    private void pickImageFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //intent to pick image from camera
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT , image_uri);
        startActivityForResult(cameraintent,I_P_C);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // hundle result

        switch (requestCode){
            case P_C: {
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //permission granted

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
        if((resultCode == RESULT_OK) && (requestCode == I_P_C)&& (i == 0)){
            IV.setImageURI(data.getData());

        }
        if((resultCode == RESULT_OK) && (requestCode == I_P_C)&& (i == 1)){
            IV.setImageURI(image_uri);

        }
    }


}

package dz.ochefaouiismail.mylogin;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;

public class SignalLayout extends AppCompatActivity  {
    int which = 0;
    private TextView tvDisplayChoice, tvDisplayLieu, NatureText;
    private FusedLocationProviderClient fusedLocationClient;
    private GeofencingClient geofencingClient;
    Button Nature;
    Uri image_uri;
    int i;
    private static final int I_P_C=1000;
    private static final int P_C=1001;
    ImageView IV;
    AwesomeValidation awesomeValidation1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        awesomeValidation1 = new AwesomeValidation(ValidationStyle.BASIC);
        setContentView(R.layout.activity_signal_layout);
        tvDisplayChoice = findViewById(R.id.teDisplayCause);
        tvDisplayLieu = findViewById(R.id.teDisplayLieu);
        NatureText = findViewById(R.id.teNature);
        Nature = findViewById(R.id.NatureButton);
        Button btnSelectChoice = findViewById(R.id.btnSelectCause);
        Button btnSelectLieu = findViewById(R.id.btnSelectLieu);
        Button btnSelectLocation = findViewById(R.id.btnSelectLocation);
        Button btnevoyer = findViewById(R.id.envoyer);
        Button btnCamera = findViewById(R.id.camera);
        Button btngallery= findViewById(R.id.gallery);
        IV= findViewById(R.id.IView);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geofencingClient = LocationServices.getGeofencingClient(this);


        btnSelectChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                which = 0;
                String[] lis = getResources().getStringArray(R.array.choice_items);
                dialog(lis, tvDisplayChoice);
            }
        });

        btnSelectLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                which = 0;
                String[] lis = getResources().getStringArray(R.array.choice_lieux);
                dialog(lis, tvDisplayLieu);
            }
        });

        btnSelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignalLayout.this,MapsActivity.class);
                startActivity(intent);
            }
        });


        Nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which = 0;
                String[] liste = getResources().getStringArray(R.array.choice_lieux);
                dialog(liste,NatureText);
            }
        });

        btnevoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validate())
                    Toast.makeText(getApplicationContext(),"welcome ",Toast.LENGTH_LONG).show();
                else
                Toast.makeText(getApplicationContext(),"can not use empty item ",Toast.LENGTH_LONG).show();
            }
        });



    }


private void dialog(final String[] list, final TextView text){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Choose any item");

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
            text.setText("");
            text.setHint("Please choise item");



        }}).setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            text.setText(list[which]);


        }});

        //    builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            NatureText.setText(list[which]);
//            Toast.makeText(SignalLayout.this,"You have selected " +
//                    list[which], Toast.LENGTH_LONG).show();
//        }
//    });
    AlertDialog dialog = builder.create();
    dialog.show();

    }
private void dialogMap(int id){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Choose a location");

    AlertDialog dialog = builder.create();
    dialog.show();

    }


    public void Gallery(View v){
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



    public void Camera(View v){
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
    public void retour(View v) {
        startActivity(new Intent(this, PrincScreen.class));

    }

    private boolean Validate(){


        //Validtion formule
        awesomeValidation1.addValidation(this,R.id.teDisplayLieu,
                RegexTemplate.NOT_EMPTY,R.string.invalid_lieu);
        awesomeValidation1.addValidation(this,R.id.teNature,
                RegexTemplate.NOT_EMPTY,R.string.invalid_nature);
     //   awesomeValidation1.addValidation(this,R.id.TELoc,
       //         RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation1.addValidation(this,R.id.teDisplayCause,
                RegexTemplate.NOT_EMPTY,R.string.invalid_cause);




        return awesomeValidation1.validate() ;
    }
}



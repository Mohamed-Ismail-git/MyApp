package dz.ochefaouiismail.mylogin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignalLayout extends AppCompatActivity  {
    int which = 0;
    private EditText EtDisplayCause, EtDisplayLieu, EtNature ,EtLocation, description;
   // private FusedLocationProviderClient fusedLocationClient;
   // private GeofencingClient geofencingClient;
    private ProgressDialog progressDialoge;
    Button Nature;
    Uri image_uri;
    public SharedPreferences signal ;
    public SharedPreferences.Editor edit;
    int i;
    int j =0;
    int PLCE_PICKER_REQUEST=1;
    private static final int I_P_C=1000;
    private static final int P_C=1001;
    ImageView IV;
    AwesomeValidation awesomeValidation1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal_layout);
        awesomeValidation1 = new AwesomeValidation(ValidationStyle.BASIC);
        progressDialoge = new ProgressDialog(this);
        setContentView(R.layout.activity_signal_layout);
        EtDisplayCause = findViewById(R.id.teDisplayCause);
        EtDisplayLieu = findViewById(R.id.teDisplayLieu);
        EtNature = findViewById(R.id.teNature);
        EtLocation = findViewById(R.id.TELoc);
        description = findViewById(R.id.Description);
        Nature = findViewById(R.id.NatureButton);
        Button btnSelectChoice = findViewById(R.id.btnSelectCause);
        Button btnSelectLieu = findViewById(R.id.btnSelectLieu);
        Button btnSelectLocation = findViewById(R.id.btnSelectLocation);
        Button btnevoyer = findViewById(R.id.envoyer);
        Button btnCamera = findViewById(R.id.camera);
        Button btngallery= findViewById(R.id.gallery);
        IV= findViewById(R.id.IView);

       // fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
       // geofencingClient = LocationServices.getGeofencingClient(this);


        btnSelectChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                which = 0;
                String[] lis = getResources().getStringArray(R.array.choice_Cause);
                dialog(lis, EtDisplayCause,"la cause:");
            }
        });

        btnSelectLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                which = 0;
                String[] lis = getResources().getStringArray(R.array.choice_lieux);
                dialog(lis, EtDisplayLieu,"lieu");
            }
        });

        btnSelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent = new Intent (SignalLayout.this, MapsActivity.class );
               // startActivity(intent);
                //finish();
mapLocation();

            }
        });



        Nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which = 0;
                String[] liste = getResources().getStringArray(R.array.choice_Nature);
                dialog(liste,EtNature,"Nature");
            }
        });

        btnevoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validate())
                    EnvoyerSignal();
                else
                    Toast.makeText(getApplicationContext(),"remplir tous les champs SVP !!",Toast.LENGTH_LONG).show();


            }
        });



    }

    private void mapLocation() {
        j=3;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(SignalLayout.this)
                    ,PLCE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }




    private void dialog(final String[] list, final TextView text,final String str){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(str);

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
        if(j==3){
        if(requestCode == PLCE_PICKER_REQUEST){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data,this);
                StringBuilder stringBuilder =new StringBuilder();
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
               // stringBuilder.append("Latitude");
                stringBuilder.append(latitude);
                stringBuilder.append("\t");
               // stringBuilder.append("longitude");
                stringBuilder.append(longitude);
                EtLocation.setText(stringBuilder.toString());
                j=0;
            }
        }
    }}
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

    private void EnvoyerSignal(){
        final String Nature =EtNature.getText().toString().trim();
        final String Lieu = EtDisplayLieu.getText().toString().trim();
        final String Location = EtLocation.getText().toString().trim();
       // final String Location = EtDisplayLieu.getText().toString().trim();
        final String Cause = EtDisplayCause.getText().toString().trim();
        final String photo = "image_uri.getPath().trim()".trim();
        final String descript = description.getText().toString().trim();
       // final String Email = email.getText().toString().trim();
        //final String Password = password.getText().toString().trim();
        progressDialoge.setMessage("registering User...");
        progressDialoge.show();

        StringRequest sRequest = new StringRequest(Request.Method.POST, Constants.URL_Signalisation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialoge.dismiss();
                // Toast.makeText(getApplicationContext(),"login ",Toast.LENGTH_LONG).show();
                JSONObject jsonObject,USER;

                try {
                    jsonObject = new JSONObject(response);
                  //  USER = jsonObject.getJSONObject("errors");
                   // if(jsonObject.getBoolean("success")) {
                       signal = getApplicationContext().getSharedPreferences("user", getApplicationContext().MODE_PRIVATE);
                       edit= signal.edit();
                    // edit.putString("token",jsonObject.getString("token"));
                    edit.putString("id",jsonObject.getString("id"));
                    edit.putString("lieu",jsonObject.getString("lieu"));
                    edit.putString("desc",jsonObject.getString("desc"));
                    edit.putString("localisation",jsonObject.getString("localisation"));
                    edit.putString("photo",jsonObject.getString("photo"));
                    edit.putString("nature",jsonObject.getString("nature"));
                    edit.putString("cause",jsonObject.getString("cause"));

                    edit.commit();
                    edit.apply();
                       // edit.apply();
                   // Toast.makeText(getApplicationContext(),jsonObject.getString("id"),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"Signal created Succeful !!",Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),signal.getString("lieu","")+"  "+signal.getString("localisation",""),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent (SignalLayout.this, PrincScreen.class );
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialoge.hide();

               // Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                //  Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
                // Toast.makeText(getApplicationContext(),"login not succes",Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("lieu",Lieu );
                params.put("nature", Nature);
                params.put("localisation", Location);
                params.put("desc", descript);
                params.put("photo", photo);
                params.put("cause", Cause);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sRequest);


    }






}



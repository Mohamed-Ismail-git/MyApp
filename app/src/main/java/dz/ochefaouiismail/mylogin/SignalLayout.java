package dz.ochefaouiismail.mylogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class SignalLayout extends AppCompatActivity  {
    int which = 0;
    private TextView tvDisplayChoice, tvDisplayLieu, NatureText;
    private FusedLocationProviderClient fusedLocationClient;
    private GeofencingClient geofencingClient;
    Button Nature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signal_layout);
        tvDisplayChoice = findViewById(R.id.tvDisplayChoice);
        tvDisplayLieu = findViewById(R.id.tvDisplayLieu);
        NatureText = findViewById(R.id.Nature);
        Nature = findViewById(R.id.NatureButton);
        Button btnSelectChoice = findViewById(R.id.btnSelectChoice);
        Button btnSelectLieu = findViewById(R.id.btnSelectLieu);
        Button btnSelectLocation = findViewById(R.id.btnSelectLocation);

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
            text.setText("Please choise item");


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


}



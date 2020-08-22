package dz.ochefaouiismail.mylogin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class MapsActivity extends AppCompatActivity
      {

   // private GoogleMap mMap;
    int PLCE_PICKER_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
      maps();
    }
          public void maps(){
              PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
              try {
                  startActivityForResult(builder.build(MapsActivity.this)
                          ,PLCE_PICKER_REQUEST);
              } catch (GooglePlayServicesRepairableException e) {
                  e.printStackTrace();
              } catch (GooglePlayServicesNotAvailableException e) {
                  e.printStackTrace();
              }
          }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PLCE_PICKER_REQUEST){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data,this);
                StringBuilder stringBuilder =new StringBuilder();
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                stringBuilder.append("Latitude");
                stringBuilder.append(latitude);
                stringBuilder.append("\n");
                stringBuilder.append("longitude");
                stringBuilder.append(longitude);

            }
        }
    }
}
package dz.ochefaouiismail.mylogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SignalLayout extends AppCompatActivity  {
    int which = 0;
    private TextView tvDisplayChoice, NatureText;
    Button Nature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signal_layout);
        tvDisplayChoice = findViewById(R.id.tvDisplayChoice);
        NatureText = findViewById(R.id.Nature);
        Nature = findViewById(R.id.NatureButton);
        Button btnSelectChoice = findViewById(R.id.btnSelectChoice);




        btnSelectChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                which = 0;
                String[] lis = getResources().getStringArray(R.array.choice_items);
                dialog(lis,tvDisplayChoice);
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


}



package dz.ochefaouiismail.mylogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignalLayout extends AppCompatActivity implements SignleChoiceDialogFragment.SingleChoiceListener  {
    private TextView tvDisplayChoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signal_layout);
        tvDisplayChoice = findViewById(R.id.tvDisplayChoice);

        Button btnSelectChoice = findViewById(R.id.btnSelectChoice);
        btnSelectChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment singleChoiceDialog = new SignleChoiceDialogFragment();
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getSupportFragmentManager(), "Single Choice Dialog");
            }
        });

    }


    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        tvDisplayChoice.setText("Selected Item = " + list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {
        tvDisplayChoice.setText("Dialog cancel");
    }
}



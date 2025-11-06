package com.example.lab4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edtName;
    DatePicker datePicker;
    RadioGroup genderGroup;
    Button btnSave;

    SharedPreferences sharedPreferences;
    public static final String PREF_NAME = "UserPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        datePicker = findViewById(R.id.datePicker);
        genderGroup = findViewById(R.id.genderGroup);
        btnSave = findViewById(R.id.btnSave);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        edtName.setText(sharedPreferences.getString("name", ""));
        datePicker.updateDate(
                sharedPreferences.getInt("year", 2000),
                sharedPreferences.getInt("month", 0),
                sharedPreferences.getInt("day", 1)
        );

        String gender = sharedPreferences.getString("gender", "");
        if(gender.equals("Male")){
            genderGroup.check(R.id.radioMale);
        } else if(gender.equals("Female")){
            genderGroup.check(R.id.radioFemale);
        }

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            int selectedId = genderGroup.getCheckedRadioButtonId();
            RadioButton selectedRadio = findViewById(selectedId);
            String genderValue = selectedRadio.getText().toString();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.putInt("day", day);
            editor.putInt("month", month);
            editor.putInt("year", year);
            editor.putString("gender", genderValue);
            editor.apply();

            Toast.makeText(MainActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
        });
    }
}
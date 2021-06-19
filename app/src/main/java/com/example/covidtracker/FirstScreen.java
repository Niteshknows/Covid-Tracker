package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class FirstScreen extends AppCompatActivity {
EditText etUserName;
ImageButton nextBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        etUserName = findViewById(R.id.username);
        nextBtn = findViewById(R.id.btnNext);

        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        String value = getShared.getString("str", "");

        if(value.length()>0){
            Intent i = new Intent(FirstScreen.this, MainActivity.class);
            startActivity(i);
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etUserName.getText().toString();
                if(name.trim().length()>0) {
                    SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shrd.edit();
                    editor.putString("str", name);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

       //use shared preferences to make a string username. if its value is null, thne only implement this screen.
        //if user does logout, then set String username as null

    }
}
package com.example.calenderandclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button viewCalButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewCalButton = (Button) findViewById(R.id.viewCalenderButton);
        viewCalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCalendarActivity();
            }
        });
    }
    public void viewCalendarActivity(){
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
package com.dataflair.foundandlost.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dataflair.foundandlost.R;

public class StartingActivity extends AppCompatActivity {

    Button getStartedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        //Button To navigate to next Activity
        getStartedBtn = (Button) findViewById(R.id.GetStartedBtn);
        //OnClick Implementation to navigate Between activities
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
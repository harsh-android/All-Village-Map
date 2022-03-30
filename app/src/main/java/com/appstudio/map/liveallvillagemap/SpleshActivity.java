package com.appstudio.map.liveallvillagemap;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SpleshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(SpleshActivity.this,StartActivity.class);
                startActivity(intent);
                finish();

            }
        },2000);
    }

}

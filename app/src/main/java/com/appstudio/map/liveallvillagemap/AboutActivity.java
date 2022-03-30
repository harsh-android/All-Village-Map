package com.appstudio.map.liveallvillagemap;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AboutActivity extends AppCompatActivity {


    LinearLayout card1,card2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        card1 = (LinearLayout) findViewById(R.id.card1);
        card2 = (LinearLayout) findViewById(R.id.card2);
        android.support.v7.widget.Toolbar mActionBar = (Toolbar) findViewById(R.id.toolbar);
        mActionBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        mActionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TL_BR
                ,new int[] {Color.parseColor("#076585"),Color.parseColor("#ffffff")});

        GradientDrawable card11 = new GradientDrawable(GradientDrawable.Orientation.TR_BL
                ,new int[] {Color.parseColor("#076585"),Color.parseColor("#ffffff")});


//        card1.setBackgroundDrawable(card11);
//        card2.setBackgroundDrawable(gd);

        GradientDrawable gd1 = new GradientDrawable(GradientDrawable.Orientation.TR_BL
                ,new int[] {Color.parseColor("#164384"),Color.parseColor("#d63624")});


    }
}

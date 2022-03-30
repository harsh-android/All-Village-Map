package com.appstudio.map.liveallvillagemap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.GridLayout;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class test_village extends AppCompatActivity {
    String s_dis, ss_nam;
    int position;
    private RecyclerView villageRecyclerView;
    public static List<VillageModelData> villageModelData =new ArrayList<>();
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;

    //Facebook ADS

    private RelativeLayout facebookad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_village);

        //Facebook ADDD
        facebookad = findViewById(R.id.facebokad);




//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        s_dis = getIntent().getStringExtra("s_dis");
        ss_nam = getIntent().getStringExtra("ss_nam");
        position = getIntent().getIntExtra("position", -1);
        Log.e("dfddcxfdgfgfgfgfdfdfdf", "onCreate: " + s_dis + "      " + ss_nam + "      " + position);


        //Toolabar CODE START
        Toolbar mActionBar = (Toolbar) findViewById(R.id.toolbar);
        mActionBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        mActionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TextView talukaTextview = (TextView) findViewById(R.id.talukaTextview);
        talukaTextview.setSelected(true);
        talukaTextview.setText("Village of "+ss_nam+"");

        //Toolabar CODE Finish

        villageRecyclerView=(RecyclerView)findViewById(R.id.villageRecyclerView);

        VillageAdapter villageAdapter=new VillageAdapter();

        RecyclerView.LayoutManager manager=new GridLayoutManager(test_village.this,2, GridLayout.VERTICAL,false);
        villageRecyclerView.setLayoutManager(manager);
        villageRecyclerView.setAdapter(villageAdapter);

        try {
            beginJsonParsing();
        }
        catch (Exception e)
        {

        }



    }


    private void beginJsonParsing() {


        try {
            JSONObject reader = new JSONObject(loadJsonFromAsset());
            JSONArray jsonArray = reader.getJSONArray(s_dis);
            villageModelData.clear();


            JSONObject object = jsonArray.getJSONObject(position);

            JSONArray jsonArray1 = object.getJSONArray(ss_nam);

            for (int j = 0; j < jsonArray1.length(); j++) {


                String village = jsonArray1.getString(j);
                Log.e("village", "beginJsonParsing: " + village);

                VillageModelData data=new VillageModelData();
                data.setVillage(village);
                villageModelData.add(data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String loadJsonFromAsset() {
        String json = null;

        try {

            InputStream inputStream = this.getAssets().open("district/" + s_dis + ".json");

            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }


    //VILLAGE ADAPTER CODE

    private class VillageAdapter extends RecyclerView.Adapter<ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view=getLayoutInflater().inflate(R.layout.village_item,viewGroup,false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {



            viewHolder.disNameTextview.setText(villageModelData.get(i).getVillage());

            final String villagename = viewHolder.disNameTextview.getText().toString();

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String strUri = "http://maps.google.com/maps?q="+villagename+","+s_dis;
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);


                }
            });
        }

        @Override
        public int getItemCount() {
            return villageModelData.size();
        }
    }
    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView disNameTextview,disCodeTextview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            disNameTextview=(TextView)itemView.findViewById(R.id.disNameTextview);
            disCodeTextview=(TextView)itemView.findViewById(R.id.disCodeTextview);
            disNameTextview.setSelected(true);

            Typeface typeface = Typeface.createFromAsset(test_village.this.getAssets(), "Fonts/my_font.otf");
            disNameTextview.setTypeface (typeface);
            disCodeTextview.setTypeface(typeface);

        }
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected();
        }
        return false;
    }

    public void onStart() {
        super.onStart();
        try {
            if (isConnected()) {
                loadBannerAd();
            }
        } catch (Exception ignored) {
        }
    }

    private void loadBannerAd() {
        try {
            facebookad.addView(AdsConnection.getFacebookInstance(this).setFacebookBannerAD(this, new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                }

                @Override
                public void onAdLoaded(Ad ad) {

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            }));

        } catch (Exception ignored) {
        }
    }

    //Faceboopkm ADS CODE Finish



}

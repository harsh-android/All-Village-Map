package com.appstudio.map.liveallvillagemap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;

import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CityActivity extends AppCompatActivity {

    private RecyclerView cityRecyclerView;
    public static List<CityModelData> cityModelData = new ArrayList<>();
    public static int pos;

    String state;
    private InterstitialAd mInterstitialAd;

    //Facebook ADS
    private com.facebook.ads.InterstitialAd mFBInterstitialAd;
    private RelativeLayout facebookad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);


        //Facebook ADDD
        facebookad = findViewById(R.id.facebokad);
        mFBInterstitialAd = AdsConnection.getFacebookInstance(this).createFacebookInterstitialAD(this);

        //ToolBar Code Start
        Toolbar mActionBar = (Toolbar) findViewById(R.id.toolbar);
        mActionBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        mActionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        state = StartActivity.stateModelClasses.get(StartActivity.pos).getStatename();
        TextView stateTextview = (TextView) findViewById(R.id.stateTextview);
        stateTextview.setSelected(true);
        stateTextview.setText(" District of "+state+"");

        //ToolBar Code Finish



        cityRecyclerView = (RecyclerView) findViewById(R.id.cityRecyclerView);

        CityAdapter cityAdapter = new CityAdapter();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(CityActivity.this, LinearLayout.VERTICAL, false);
        cityRecyclerView.setLayoutManager(manager);
        cityRecyclerView.setAdapter(cityAdapter);

        beginJsonParsing();

    }

    //JSON CODE

    private void beginJsonParsing() {


//        state = StartActivity.stateModelClasses.get(StartActivity.pos).getStatename();
        cityModelData.clear();

        try {
            JSONObject reader = new JSONObject(loadJsonFromAsset());
            JSONArray jsonArray = reader.getJSONArray(state);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);


                String disname = object.getString("disName");
                String discode = object.getString("disCode");

                CityModelData data = new CityModelData();
                data.setDisname(disname);
                data.setDiscode(discode);
                cityModelData.add(data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public String loadJsonFromAsset() {
        String json = null;

        try {

            InputStream inputStream = this.getAssets().open(state + ".json");
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

    //CITY ADAPTER CODE

    private class CityAdapter extends RecyclerView.Adapter<ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = getLayoutInflater().inflate(R.layout.city_item, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


            viewHolder.disNameTextview.setText(cityModelData.get(i).disname);
            viewHolder.disCodeTextview.setText(cityModelData.get(i).discode);


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos = i;


                    if (i % 2 != 0) {

                        //Facebook ADD
                        AdsConnection.getFacebookInstance(CityActivity.this).setFacebokInterstitialAD(mFBInterstitialAd, new InterstitialAdListener() {
                            public void onInterstitialDisplayed(Ad p0) {
                            }

                            public void onAdClicked(Ad p0) {
                            }

                            public void onInterstitialDismissed(Ad p0) {

//                                Toast.makeText(CityActivity.this, "onInterstitialDismissed", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), SubDistrictActivity.class);
                                startActivity(intent);
                            }

                            public void onError(Ad p0, AdError p1) {

                                Toast.makeText(CityActivity.this, "Error", Toast.LENGTH_SHORT).show();

//                                Intent intent = new Intent(getApplicationContext(), SubDistrictActivity.class);
//                                startActivity(intent);
                            }

                            public void onAdLoaded(Ad p0) {
                            }

                            public void onLoggingImpression(Ad p0) {
                            }
                        });

                        if (mFBInterstitialAd != null && mFBInterstitialAd.isAdLoaded()) {
                            mFBInterstitialAd.show();
                            mFBInterstitialAd = AdsConnection.getFacebookInstance(CityActivity.this).createFacebookInterstitialAD(CityActivity.this);

                        } else {
                            Intent intent = new Intent(getApplicationContext(), SubDistrictActivity.class);
                            startActivity(intent);
                            mFBInterstitialAd = AdsConnection.getFacebookInstance(CityActivity.this).createFacebookInterstitialAD(CityActivity.this);

                        }

                    } else {
                        Intent intent = new Intent(CityActivity.this, SubDistrictActivity.class);
                        startActivity(intent);

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return cityModelData.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView disNameTextview, disCodeTextview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            disNameTextview = (TextView) itemView.findViewById(R.id.disNameTextview);
            disCodeTextview = (TextView) itemView.findViewById(R.id.disCodeTextview);

            Typeface typeface = Typeface.createFromAsset(CityActivity.this.getAssets(), "Fonts/my_font.otf");
            disNameTextview.setTypeface(typeface);
            disCodeTextview.setTypeface(typeface);
        }
    }


    //Faceboopkm ADS CODE Start

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

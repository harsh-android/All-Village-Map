package com.appstudio.map.liveallvillagemap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SubDistrictActivity extends AppCompatActivity {

    private RecyclerView sub_DistrictRecyclerView;
    public static List<Sub_DistrictModelData> sub_districtModelData = new ArrayList<>();
    private String subdistrict;
    public static int pos;
    String[] arr;
    public static List<VillageModelData> village_data = new ArrayList<>();
    public static String ss_nam;
    private InterstitialAd mInterstitialAd;

    //Facebook ADS
    private com.facebook.ads.InterstitialAd mFBInterstitialAd;
    private RelativeLayout facebookad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_district);

        //Facebook ADDD
        facebookad = findViewById(R.id.facebokad);
        mFBInterstitialAd = AdsConnection.getFacebookInstance(this).createFacebookInterstitialAD(this);

        //Toolabar CODE START
        Toolbar mActionBar = (Toolbar) findViewById(R.id.toolbar);
        mActionBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        mActionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        subdistrict = CityActivity.cityModelData.get(CityActivity.pos).getDisname();
        TextView districtTextview = (TextView) findViewById(R.id.districtTextview);
        districtTextview.setSelected(true);
        districtTextview.setText("Taluka of "+subdistrict+"");

        //Toolabar CODE Finish


        sub_DistrictRecyclerView = (RecyclerView) findViewById(R.id.sub_DistrictRecyclerView);

        Sub_DistrictAdapter sub_districtAdapter = new Sub_DistrictAdapter();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(SubDistrictActivity.this, LinearLayout.VERTICAL, false);
        sub_DistrictRecyclerView.setLayoutManager(manager);
        sub_DistrictRecyclerView.setAdapter(sub_districtAdapter);

        beginJsonParsing();
    }


    private void beginJsonParsing() {




        sub_districtModelData.clear();
        try {
            JSONObject reader = new JSONObject(loadJsonFromAsset());
            JSONArray jsonArray = reader.getJSONArray(subdistrict);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);

                String cityName = object.getString("cityName");
                String cityCode = object.getString("cityCode");

                Sub_DistrictModelData data = new Sub_DistrictModelData();
                data.setCityname(cityName);
                data.setCitycode(cityCode);

                sub_districtModelData.add(data);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public String loadJsonFromAsset() {
        String json = null;

        try {

            InputStream inputStream = this.getAssets().open("district/" + subdistrict + ".json");

            ss_nam=subdistrict;
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

    private class Sub_DistrictAdapter extends RecyclerView.Adapter<ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = getLayoutInflater().inflate(R.layout.city_item, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


            viewHolder.disNameTextview.setText(sub_districtModelData.get(i).cityname);
            viewHolder.disCodeTextview.setText(sub_districtModelData.get(i).citycode);


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pos = i;


                    if(i%2 == 0)
                    {



                        //Facebook ADD
                        AdsConnection.getFacebookInstance(SubDistrictActivity.this).setFacebokInterstitialAD(mFBInterstitialAd, new InterstitialAdListener() {
                            public void onInterstitialDisplayed(Ad p0) {
                            }

                            public void onAdClicked(Ad p0) {
                            }

                            public void onInterstitialDismissed(Ad p0) {

//
                                Intent intent = new Intent(getApplicationContext(), test_village.class);


                                intent.putExtra("s_dis",subdistrict);
                                intent.putExtra("position",i);
                                intent.putExtra("ss_nam",sub_districtModelData.get(i).cityname);
                                startActivity(intent);

                            }

                            public void onError(Ad p0, AdError p1) {

//                                Intent intent = new Intent(getApplicationContext(), test_village.class);
//
//
//                                intent.putExtra("s_dis",subdistrict);
//                                intent.putExtra("position",i);
//                                intent.putExtra("ss_nam",sub_districtModelData.get(i).cityname);
//                                startActivity(intent);
                            }

                            public void onAdLoaded(Ad p0) {
                            }

                            public void onLoggingImpression(Ad p0) {
                            }
                        });

                        if (mFBInterstitialAd != null && mFBInterstitialAd.isAdLoaded()) {
                            mFBInterstitialAd.show();
                            mFBInterstitialAd = AdsConnection.getFacebookInstance(SubDistrictActivity.this).createFacebookInterstitialAD(SubDistrictActivity.this);


                        } else {

                            Intent intent = new Intent(getApplicationContext(), test_village.class);


                            intent.putExtra("s_dis",subdistrict);
                            intent.putExtra("position",i);
                            intent.putExtra("ss_nam",sub_districtModelData.get(i).cityname);
                            startActivity(intent);



                        }


                    }
                    else {


                        Intent intent=new Intent(SubDistrictActivity.this,test_village.class);
                        intent.putExtra("s_dis",subdistrict);
                        intent.putExtra("position",i);
                        intent.putExtra("ss_nam",sub_districtModelData.get(i).cityname);
                        startActivity(intent);

                    }


                }
            });

        }

        @Override
        public int getItemCount() {
            return sub_districtModelData.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView disNameTextview, disCodeTextview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            disNameTextview = (TextView) itemView.findViewById(R.id.disNameTextview);
            disCodeTextview = (TextView) itemView.findViewById(R.id.disCodeTextview);

            Typeface typeface = Typeface.createFromAsset(SubDistrictActivity.this.getAssets(), "Fonts/my_font.otf");
            disNameTextview.setTypeface (typeface);
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

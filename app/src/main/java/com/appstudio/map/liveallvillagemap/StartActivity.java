package com.appstudio.map.liveallvillagemap;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import com.google.android.gms.ads.reward.RewardedVideoAd;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.facebook.ads.*;

public class StartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button button;
   public static List<StateModelClass> stateModelClasses =new ArrayList<>();


    public RecyclerView stateRecyclerView;
    StateAdapter stateAdapter;

    public  static int pos;
    InputStream inputStream;


    //Facebook ADS
    private com.facebook.ads.InterstitialAd mFBInterstitialAd;
    private RelativeLayout facebookad;

    //SEARCH BAR CODE
    private ArrayList<StateModelClass> mExampleList;
    private RecyclerView.LayoutManager mLayoutManager;
    private InterstitialAd mInterstitialAd;
    private Toolbar toolbar;
    private RewardedVideoAd mRewardedVideoAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if(isConnected())
        {

            //Facebook ADDD
            facebookad = findViewById(R.id.facebokad);
            mFBInterstitialAd = AdsConnection.getFacebookInstance(this).createFacebookInterstitialAD(this);


            TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "Fonts/my_font.otf");


//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

            beginJsonParsing();

            stateRecyclerView=(RecyclerView)findViewById(R.id.stateRecyclerView);
            stateRecyclerView.setHasFixedSize(true);
            stateRecyclerView.setItemViewCacheSize(50);
            stateRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

            stateAdapter=new StateAdapter();

            RecyclerView.LayoutManager manager=new GridLayoutManager(StartActivity.this,2, GridLayout.VERTICAL,false);
            stateRecyclerView.setLayoutManager(manager);
            stateRecyclerView.setAdapter(stateAdapter);


            //NAVIGATION DRAWER CODE....
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            DrawerLayout drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(StartActivity.this, drawer_layout, toolbar, R.string.open, R.string.close);
            drawer_layout.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            
        }
        else 
        {


            Dialog dialog = new Dialog(StartActivity.this,android.R.style.Theme_Translucent_NoTitleBar);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.netconnection_item);
            dialog.show();

        }
        

     


    }


    //SEARCH BAR CODE

    private void filter(String text) {
        ArrayList<StateModelClass> filteredList = new ArrayList<>();

        for (StateModelClass item : mExampleList) {
            if (item.getStatename().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        stateAdapter.filterList(filteredList);
    }

//    private void buildRecyclerView() {
//        stateRecyclerView = findViewById(R.id.stateRecyclerView);
//        stateRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        stateAdapter = new StateAdapter(mExampleList);
//
//        stateRecyclerView.setLayoutManager(mLayoutManager);
//        stateRecyclerView.setAdapter(stateAdapter);
//    }




    //GET ALL DATA JSON CODE

    private void beginJsonParsing() {

        stateModelClasses.clear();

        try {
            JSONObject reader = new JSONObject(loadJsonFromAsset());
            JSONArray jsonArray=reader.getJSONArray("Country");
            for(int i=0; i<jsonArray.length() ; i++)
            {

                JSONObject object=jsonArray.getJSONObject(i);

                String disname=object.getString("stateName");
                String discode=object.getString("stateCode");
                String pic=object.getString("pic");

                StateModelClass data = new StateModelClass();
                data.setStatename(disname);
                data.setStatecode(discode);
                data.setPic(pic);
                stateModelClasses.add(data);
                mExampleList = new ArrayList<>();
                mExampleList.add(data);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String loadJsonFromAsset() {
        String json = null;

        try {

            InputStream inputStream = this.getAssets().open("country.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }

        return  json;
    }
    
    //NAVIGATION METHOD CALLL

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.Dashboard:
                break;

            case R.id.AboutUs:
                Intent intent=new Intent(StartActivity.this,AboutActivity.class);
                startActivity(intent);
                break;

            case R.id.ShareApp:

                Intent s = new Intent(android.content.Intent.ACTION_SEND);
                s.setType("text/plain");
                s.putExtra(android.content.Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + StartActivity.this.getPackageName());
                startActivity(Intent.createChooser(s, "Share App"));

                break;

            case R.id.RateApp:

                Uri uri = Uri.parse("  market://details?id=" + StartActivity.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + StartActivity.this.getPackageName())));
                }

                break;

            case R.id.MoreApp:


                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:App Mine")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=App Mine")));
                }

                break;

            case R.id.PrivacyPolicy:
                Intent intent1=new Intent(StartActivity.this,privacy_policyactivity.class);
                startActivity(intent1);
                break;



        }

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }



    //STATE ADAPTER CODE

    private class StateAdapter extends RecyclerView.Adapter<ViewHolder> {


        public StateAdapter() {


        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view=getLayoutInflater().inflate(R.layout.state_item,viewGroup,false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


            viewHolder.disNameTextview.setText(StartActivity.stateModelClasses.get(i).statename);
            viewHolder.disCodeTextview.setText(StartActivity.stateModelClasses.get(i).statecode);

            Glide.with(StartActivity.this).load(StartActivity.stateModelClasses.get(i).getPic()).into(viewHolder.imageview);


            Log.e("url", "onBindViewHolder: "+StartActivity.stateModelClasses.get(i).getPic() );

//            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pos=i;

                        //Facebook ADD
                        AdsConnection.getFacebookInstance(StartActivity.this).setFacebokInterstitialAD(mFBInterstitialAd, new InterstitialAdListener() {
                            public void onInterstitialDisplayed(Ad p0) {
                            }

                            public void onAdClicked(Ad p0) {
                            }

                            public void onInterstitialDismissed(Ad p0) {

                                Intent intent = new Intent(getApplicationContext(), CityActivity.class);
                                startActivity(intent);
                            }

                            public void onError(Ad p0, AdError p1) {

                                Toast.makeText(StartActivity.this, "Error", Toast.LENGTH_SHORT).show();
//
//                                Intent intent = new Intent(getApplicationContext(), CityActivity.class);
//                                startActivity(intent);
                            }

                            public void onAdLoaded(Ad p0) {
                            }

                            public void onLoggingImpression(Ad p0) {
                            }
                        });

                        if (mFBInterstitialAd != null && mFBInterstitialAd.isAdLoaded()) {
                            mFBInterstitialAd.show();
                            mFBInterstitialAd = AdsConnection.getFacebookInstance(StartActivity.this).createFacebookInterstitialAD(StartActivity.this);

                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(), CityActivity.class);
                            startActivity(intent);
                            mFBInterstitialAd = AdsConnection.getFacebookInstance(StartActivity.this).createFacebookInterstitialAD(StartActivity.this);

                        }


                }
            });
        }

        public void filterList(ArrayList<StateModelClass> filteredList) {
            mExampleList = filteredList;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return stateModelClasses.size();
        }
    }



    //Faceboopkm ADS

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
//                loadRectAd();

            }
        } catch (Exception ignored) {
        }
    }

    private void loadBannerAd() {
        try {
            facebookad.addView(AdsConnection.getFacebookInstance(this).setFacebookBannerAD(this, new AdListener() {
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







    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView disNameTextview,disCodeTextview;
        private final ImageView imageview;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            disNameTextview=(TextView)itemView.findViewById(R.id.disNameTextview);
            disCodeTextview=(TextView)itemView.findViewById(R.id.disCodeTextview);
            imageview=(ImageView)itemView.findViewById(R.id.imageview);

            Typeface typeface = Typeface.createFromAsset(StartActivity.this.getAssets(), "Fonts/my_font.otf");
            disNameTextview.setTypeface (typeface);
            disCodeTextview.setTypeface(typeface);

            disNameTextview.setSelected(true);

        }
    }

}

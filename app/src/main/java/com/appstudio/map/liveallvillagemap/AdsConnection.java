package com.appstudio.map.liveallvillagemap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("StaticFieldLeak")
public class AdsConnection
{
    static final String Facebook_Medium_Rectangle_AD = "2114213305298765_2115668398486589";
    static final String Facebook_Native_Banner_AD    = "2114213305298765_2115668208486608";
    static final String Facebook_Banner_AD           = "1985876778384366_1985879528384091";
    static final String Facebook_Interstitial_AD     = "1985876778384366_1988720318100012";
    static final String Facebook_Native_AD           = "2114213305298765_2114300371956725";



    private static AdsConnection adsConnection;

    private static InterstitialAd mFBInterstitialAd;

//    public void setFacebokNativeAD(final Context context, final ViewGroup viewGroup, final NativeAdListener adListener) {
//
//        final NativeAd mFBNativeAd = new NativeAd(context, Facebook_Native_AD);
//
//        mFBNativeAd.setAdListener(new NativeAdListener() {
//            public void onAdClicked(Ad p0) {
//            }
//
//            public void onMediaDownloaded(Ad p0) {
//            }
//
//            public void onError(Ad p0, AdError p1) {
//                try {
//                    adListener.onError(p0, p1);
//                } catch (Exception ignored) {
//                }
//            }
//
//            public void onAdLoaded(Ad p0) {
//                try {
//
//                    if (mFBNativeAd != p0) {
//                        adListener.onAdLoaded(p0);
//                    }
//
//                    mFBNativeAd.unregisterView();
//
//                    LayoutInflater inflater = LayoutInflater.from(context);
//                    View adView = inflater.inflate(R.layout.facebook_native, viewGroup, false);
//                    viewGroup.addView(adView);
//                    viewGroup.setVisibility(View.VISIBLE);
//
//                    LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
//                    AdChoicesView adChoicesView = new AdChoicesView(context, mFBNativeAd, true);
//                    adChoicesContainer.addView(adChoicesView, 0);
//
//                    AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
//                    TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
//                    TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
//                    MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
//                    TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
//
//                    TextView nativeAdSocialContext =
//                            adView.findViewById(R.id.native_ad_social_context);
//                    Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);
//
//                    // Setting the Text
//                    nativeAdSocialContext.setText(mFBNativeAd.getAdSocialContext());
//                    nativeAdCallToAction.setText(mFBNativeAd.getAdCallToAction());
//                    nativeAdCallToAction.setVisibility(
//                            mFBNativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
//                    nativeAdTitle.setText(mFBNativeAd.getAdvertiserName());
//                    nativeAdBody.setText(mFBNativeAd.getAdBodyText());
//                    sponsoredLabel.setText(mFBNativeAd.getSponsoredTranslation());
//
//                    // You can use the following to specify the clickable areas.
//                    List<View> clickableViews = new ArrayList<>();
//                    clickableViews.add(nativeAdIcon);
//                    clickableViews.add(nativeAdMedia);
//                    clickableViews.add(nativeAdCallToAction);
//                    mFBNativeAd.registerViewForInteraction(viewGroup, nativeAdMedia, nativeAdIcon, clickableViews);
//
//                    mFBNativeAd.destroy();
//                    adListener.onAdLoaded(p0);
//                } catch (Exception ignored) {
//                }
//
//            }
//
//            public void onLoggingImpression(Ad p0) {
//            }
//        });
//        mFBNativeAd.loadAd();
//
//    }

//    public void setFacebookNativeBannerAD(final Context context, final ViewGroup viewGroup, final NativeAdListener adListener) {
//
//        final NativeBannerAd mFBNativeBannerAd = new NativeBannerAd(context, Facebook_Native_Banner_AD);
//        mFBNativeBannerAd.setAdListener(new NativeAdListener() {
//            public void onAdClicked(Ad p0) {
//            }
//
//            public void onMediaDownloaded(Ad p0) {
//            }
//
//            public void onError(Ad p0, AdError p1) {
//                try {
//                    adListener.onError(p0, p1);
//                } catch (Exception ignored) {
//                }
//            }
//
//            public void onAdLoaded(Ad p0) {
//                try {
//
//                    if (mFBNativeBannerAd != p0) {
//                        adListener.onAdLoaded(p0);
//                    }
//
//                    mFBNativeBannerAd.unregisterView();
//
//                    LayoutInflater inflater = LayoutInflater.from(context);
//                    View adView = inflater.inflate(R.layout.facebook_native_banner, viewGroup, false);
//                    viewGroup.addView(adView);
//                    viewGroup.setVisibility(View.VISIBLE);
//
//                    // Add the AdChoices icon
//                    RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
//                    AdChoicesView adChoicesView = new AdChoicesView(context, mFBNativeBannerAd, true);
//                    adChoicesContainer.addView(adChoicesView, 0);
//
//                    // Create native UI using the ad metadata.
//                    TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
//                    TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
//                    TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
//                    AdIconView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
//                    Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);
//
//                    // Set the Text.
//                    nativeAdCallToAction.setText(mFBNativeBannerAd.getAdCallToAction());
//                    nativeAdCallToAction.setVisibility(
//                            mFBNativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
//                    nativeAdTitle.setText(mFBNativeBannerAd.getAdvertiserName());
//                    nativeAdSocialContext.setText(mFBNativeBannerAd.getAdSocialContext());
//                    sponsoredLabel.setText(mFBNativeBannerAd.getSponsoredTranslation());
//
//                    // Register the Title and CTA button to listen for clicks.
//                    List<View> clickableViews = new ArrayList<>();
//                    clickableViews.add(nativeAdTitle);
//                    clickableViews.add(nativeAdCallToAction);
//                    mFBNativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
//
//                    mFBNativeBannerAd.destroy();
//                    adListener.onAdLoaded(p0);
//                } catch (Exception ignored) {
//                }
//
//            }
//
//            public void onLoggingImpression(Ad p0) {
//            }
//        });
//        mFBNativeBannerAd.loadAd();
//    }

    public InterstitialAd createFacebookInterstitialAD(Activity activity) {
        InterstitialAd mFBInterstitialAd = new InterstitialAd(activity, Facebook_Interstitial_AD);
        mFBInterstitialAd.loadAd();
        return mFBInterstitialAd;
    }

    public InterstitialAd setFacebokInterstitialAD(final InterstitialAd mFBInterstitialAd, final InterstitialAdListener adListener) {

        mFBInterstitialAd.setAdListener(new InterstitialAdListener() {
            public void onError(Ad ad, AdError adError) {
                adListener.onError(ad, adError);
            }

            public void onAdLoaded(Ad ad) {
                adListener.onAdLoaded(ad);
            }

            public void onAdClicked(Ad ad) {
                adListener.onAdClicked(ad);
            }

            public void onLoggingImpression(Ad ad) {
                adListener.onLoggingImpression(ad);
            }

            public void onInterstitialDisplayed(Ad ad) {
                adListener.onInterstitialDisplayed(ad);
            }

            public void onInterstitialDismissed(Ad ad) {
                mFBInterstitialAd.loadAd();
                adListener.onInterstitialDismissed(ad);
            }
        });

        return mFBInterstitialAd;
    }

    public AdView setFacebookBannerAD(Context context, final AdListener adListener) {
        AdView mAdView = new AdView(context, Facebook_Banner_AD, AdSize.BANNER_HEIGHT_50);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                adListener.onError(ad, adError);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                adListener.onAdLoaded(ad);
            }

            @Override
            public void onAdClicked(Ad ad) {
                adListener.onAdClicked(ad);
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                adListener.onLoggingImpression(ad);
            }
        });
        mAdView.loadAd();
        return mAdView;
    }

    public AdView setFacebookMediumRectBannerAD(Context context, final AdListener adListener) {

        Toast.makeText(context, "ADView SETRECTBANNER", Toast.LENGTH_SHORT).show();

        AdView mAdView = new AdView(context, Facebook_Medium_Rectangle_AD, AdSize.RECTANGLE_HEIGHT_250);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                adListener.onError(ad, adError);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                adListener.onAdLoaded(ad);
            }

            @Override
            public void onAdClicked(Ad ad) {
                adListener.onAdClicked(ad);
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                adListener.onLoggingImpression(ad);
            }
        });
        mAdView.loadAd();
        return mAdView;
    }

    public static AdsConnection getFacebookInstance(Activity activity)
    {

        if (AdsConnection.adsConnection == null)
        {
            AdsConnection.adsConnection = new AdsConnection();

            AdsConnection.mFBInterstitialAd = new InterstitialAd(activity, Facebook_Interstitial_AD);
            AdsConnection.mFBInterstitialAd.loadAd();
//            AdSettings.addTestDevice("2d7f13c6-808a-4d24-82c0-c566e85cff5e");
        }

        return adsConnection;
    }
}
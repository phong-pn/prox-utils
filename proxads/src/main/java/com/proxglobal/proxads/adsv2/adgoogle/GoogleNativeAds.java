package com.proxglobal.proxads.adsv2.adgoogle;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.proxglobal.proxads.R;
import com.proxglobal.proxads.adsv2.ads.NativeAds;

public class GoogleNativeAds extends NativeAds<NativeAdView> {
    private int layoutAdId;
    private int styleBtnAds;
    private boolean isCustomStyle;
    public static boolean isOpenAds = false;

    public GoogleNativeAds(Activity activity, FrameLayout container, String adId, int layoutAdId) {
        super(activity, container, adId);
        this.layoutAdId = layoutAdId;
        isCustomStyle = false;
    }

    public GoogleNativeAds(Activity activity, FrameLayout container, String adId, int layoutAdId, int styleBtnAds) {
        super(activity, container, adId);
        this.layoutAdId = layoutAdId;
        this.styleBtnAds = styleBtnAds;
        isCustomStyle = true;
    }

    @Override
    public void specificLoadAdsMethod() {
        ads = (NativeAdView) mActivity.getLayoutInflater().inflate(layoutAdId, null);

        AdLoader.Builder builder = new AdLoader.Builder(mActivity, adId).forNativeAd(nativeAd -> {
            if (mActivity.isDestroyed()) {
                if (ads != null) {
                    ads.destroy();
                }
                nativeAd.destroy();
            } else {
                onShowSuccess();

                if (ads == null)
                    ads = (NativeAdView) mActivity.getLayoutInflater().inflate(layoutAdId, null);
                populateNativeAdView(nativeAd, ads);

                mContainer.removeAllViews();
                mContainer.addView(ads);
            }
        });

        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdOpened() {
                super.onAdOpened();
                isOpenAds = true;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                onLoadFailed();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                onLoadSuccess();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                onClosed();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                onShowSuccess();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void specificShowAdsMethod(Activity activity) {

    }

    public void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        Button adCallToAction;

        try {
            if (isCustomStyle) {
                adView.findViewById(R.id.ad_call_to_action).setVisibility(View.GONE);
                adCallToAction = new Button(new ContextThemeWrapper(mActivity, styleBtnAds), null, styleBtnAds);
                adCallToAction.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ((LinearLayout) adView.findViewById(R.id.linear)).addView(adCallToAction);
            } else {
                adView.findViewById(R.id.ad_call_to_action).setVisibility(View.VISIBLE);
                adCallToAction = adView.findViewById(R.id.ad_call_to_action);
            }
        } catch (Exception e) {
            adView.findViewById(R.id.ad_call_to_action).setVisibility(View.VISIBLE);
            adCallToAction = adView.findViewById(R.id.ad_call_to_action);
        }
        adView.setCallToActionView(adCallToAction);

        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));

        // The headline is guaranteed to be in every UnifiedNativeAd.
        try {
            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        try {
            if (nativeAd.getBody() == null) {
                adView.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                adView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (nativeAd.getCallToAction() == null) {
                adView.getCallToActionView().setVisibility(View.INVISIBLE);
            } else {
                adView.getCallToActionView().setVisibility(View.VISIBLE);
                adCallToAction.setText(nativeAd.getCallToAction());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (nativeAd.getIcon() == null) {
                adView.getIconView().setVisibility(View.GONE);
            } else {
                ((ImageView) adView.getIconView()).setImageDrawable(
                        nativeAd.getIcon().getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (nativeAd.getPrice() == null) {
                adView.getPriceView().setVisibility(View.INVISIBLE);
            } else {
                adView.getPriceView().setVisibility(View.VISIBLE);
                ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (nativeAd.getStore() == null) {
                adView.getStoreView().setVisibility(View.INVISIBLE);
                adView.findViewById(R.id.ic_store2).setVisibility(View.GONE);
            } else {
                adView.getStoreView().setVisibility(View.VISIBLE);
                ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
                if (nativeAd.getStore().equalsIgnoreCase("Google Play")) {
                    adView.findViewById(R.id.ic_store2).setVisibility(View.VISIBLE);
                } else {
                    adView.findViewById(R.id.ic_store2).setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (nativeAd.getStarRating() == null) {
                adView.getStarRatingView().setVisibility(View.INVISIBLE);
            } else {
                ((RatingBar) adView.getStarRatingView())
                        .setRating(nativeAd.getStarRating().floatValue());
                adView.getStarRatingView().setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (nativeAd.getAdvertiser() == null) {
                adView.getAdvertiserView().setVisibility(View.INVISIBLE);
            } else {
                ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
                adView.getAdvertiserView().setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd);

    }
}

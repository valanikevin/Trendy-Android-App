package com.aiora.trendy.ads;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aiora.trendy.R;
import com.google.android.gms.ads.formats.NativeContentAdView;

/**
 * Created by HOME on 06-03-2018.
 */

public class NativeContentAdViewHolder extends RecyclerView.ViewHolder {
    NativeContentAdViewHolder(View view) {
        super(view);
        NativeContentAdView adView = (NativeContentAdView) view;

        // Register the view used for each individual asset.
        adView.setHeadlineView(adView.findViewById(R.id.contentad_headline));
        adView.setImageView(adView.findViewById(R.id.contentad_image));
        adView.setBodyView(adView.findViewById(R.id.contentad_body));
        adView.setCallToActionView(adView.findViewById(R.id.contentad_call_to_action));
        adView.setLogoView(adView.findViewById(R.id.contentad_logo));
        adView.setAdvertiserView(adView.findViewById(R.id.contentad_advertiser));
    }
}

package com.aiora.trendy.providers.woocommerce.model;

import android.content.Context;

import com.aiora.trendy.R;

/**
 * This file is part of the Universal template
 * For license information, please check the LICENSE
 * file in the root of this project
 *
 * @author Sherdle
 * Copyright 2017
 */

public class RestAPI {
    private String path = "/wp-json/wc/v2/";
    private String checkout = "/checkout/";
    private String checkout_complete = "/checkout/order-received/";

    private static String currencyFormat = "$%s";
    private static String unit_size = "cm";
    private static String unit_weight = "kg";

    private Context context;
    public RestAPI(Context context) {
        this.context = context;
    }

    public String getHost() {
        return context.getResources().getString(R.string.woocommerce_url);
    }

    public String getPath() {
        return path;
    }

    public String getCheckout() {
        return checkout;
    }

    public String getCheckoutComplete() {
        return checkout_complete;
    }

    public String getCustomerKey() {
        return context.getResources().getString(R.string.woocommerce_consumer_key);
    }

    public String getCustomerSecret() {
        return context.getResources().getString(R.string.woocommerce_consumer_secret);
    }

    public static String getCurrencyFormat() {
         return currencyFormat;
    }
    public static String getUnitSize() {
        return unit_size;
    }
    public static String getUnitWeight() {
        return unit_weight;
    }
}

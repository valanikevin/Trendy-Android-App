package com.aiora.trendy.providers.woocommerce.checkout;

import android.content.Context;

import com.aiora.trendy.providers.woocommerce.model.RestAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class to add items to the site's shopping cart
 */
public class CartWithCookies {
    private final List<Cookie> mCookieStore = new ArrayList<>();
    private CookieJar mCookies;
    private ProductAddedCallback productAddedCallBack;
    private AllProductsAddedCallback allProductsAddedCallback;
    private Context mContext;

    public CartWithCookies(Context context, AllProductsAddedCallback callback) {
        this.mContext = context;
        this.allProductsAddedCallback = callback;
            this.mCookies = new CookieJar() {

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    //New cookies build upon previous cookies, so we can clear previous cookies
                    mCookieStore.clear();
                    mCookieStore.addAll(cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    return mCookieStore;
                }
            };
    }

    public void addProductsToCart(List<CartProduct> products){
        final List<CartProduct> productsToAdd = new ArrayList<>(products);

        productAddedCallBack = new ProductAddedCallback() {
            @Override
            public void success(CartProduct product) {
                productsToAdd.remove(product);
                if (productsToAdd.size() > 0){
                    addProductToCart(productsToAdd.get(0),
                            productAddedCallBack);
                } else {
                    allProductsAddedCallback.success(mCookieStore);
                }
            }

            @Override
            public void failure() {
                allProductsAddedCallback.failure();
            }
        };

        addProductToCart(productsToAdd.get(0),
                productAddedCallBack);
    }

    /**
     * Add a product to the cart
     * @param product product to add
     */
    private void addProductToCart(final CartProduct product, final ProductAddedCallback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(mCookies).build();

        int productId = product.getVariation() == null ? product.getProduct().getId() :
                product.getVariation().getId();

        RestAPI api = new RestAPI(mContext);
        Request request = new Request.Builder()
                .url(api.getHost() + "?add-to-cart=" + productId
                        + "&quantity=" + product.getQuantity())
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

                callback.failure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("RESPONSE CODE: "+response.code());
                callback.success(product);

                response.close();
            }
        });
    }

    /**
     * Callback used to inform when the cart is completed
     */
    public interface AllProductsAddedCallback{
        void success(List<Cookie> cookies);
        void failure();
    }

    /**
     * Callback used internally to alert when a product has been added to the cart
     */
    private interface ProductAddedCallback{
        void success(CartProduct product);
        void failure();
    }
}

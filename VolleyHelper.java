package com.finoit.weather.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.finoit.weather.Activities.MainActivity;
import com.finoit.weather.Listeners.OnJSONReturn;

import org.json.JSONObject;

/**
 * Created by emp272 on 8/26/2016.
 */
public class VolleyHelper {
    private static VolleyHelper mVolleyInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mContext;

    private VolleyHelper(Context context) {

        mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);

                    }
                });
        //mImageLoader = new ImageLoader()

    }

    public static void makeJSONObjectRequest(final Context context, int requestMethod, String url){

        //requestMethod = requestMethod.toUpperCase();
        final Bundle results = new Bundle();
        switch (requestMethod) {
            case Request.Method.GET:
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        results.putInt(MainActivity.RESULT_STATUS, MainActivity.STATUS_OK);
                        results.putString(MainActivity.RESULT, response.toString());

                        Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();

                        ((OnJSONReturn)context).onJSONReturn(results);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        results.putInt(MainActivity.RESULT_STATUS, MainActivity.STATUS_ERROR);
                        results.putString(MainActivity.RESULT, error.toString());

                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

                        ((OnJSONReturn)context).onJSONReturn(results);
                    }
                });
                getInstance(context).addToRequestQueue(request);
                break;
            default:
                Toast.makeText(context, "WRONG Method Supplied", Toast.LENGTH_LONG);
                break;
        }
    }

    public static synchronized VolleyHelper getInstance(Context context) {
        if (mVolleyInstance == null) {
            mVolleyInstance = new VolleyHelper(context);
        }
        return mVolleyInstance;
    }

    public void addToRequestQueue(Request request) {
        getRequestQueue().add(request);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }
}

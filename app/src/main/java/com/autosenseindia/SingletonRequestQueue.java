package com.autosenseindia;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonRequestQueue {

    private static SingletonRequestQueue singletonRequestQueue;
    private RequestQueue requestQueue;
    private Context context;

    private SingletonRequestQueue(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized SingletonRequestQueue getInstance(Context context) {
        if (singletonRequestQueue == null) {
            singletonRequestQueue = new SingletonRequestQueue(context);
        }
        return singletonRequestQueue;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
}

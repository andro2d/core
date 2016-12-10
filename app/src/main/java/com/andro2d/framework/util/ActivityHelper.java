package com.andro2d.framework.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by antony on 11/20/16.
 */

public final class ActivityHelper {

    private static ActivityHelper instance;

    private ActivityHelper() {}

    public static ActivityHelper getInstance() {
        if (instance == null) {
            instance = new ActivityHelper();
        }

        return instance;
    }

    public Activity getActivity(Context context) {
        return (Activity) context;
    }

    public Bundle getExtras(Activity activity) {
        return activity.getIntent().getExtras();
    }

    public void changeActivity(Activity activity, Class<? extends Activity> c){
        changeActivity(activity, null);
    }

    public void changeActivity(Activity activity, Class<? extends Activity> c, HashMap<String, String> map) {
        Intent in = new Intent(activity, c);
        if(map != null) {
            for(Map.Entry<String, String> en : map.entrySet()) {
                in.putExtra(en.getKey(), en.getValue());
            }
        }
        activity.startActivity(in);
        activity.finish();
    }
}

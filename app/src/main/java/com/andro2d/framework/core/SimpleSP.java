package com.andro2d.framework.core;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public final class SimpleSP {
	
	private SharedPreferences prefs;
	
	public SimpleSP(String prefkey, Activity activity) {
		this.prefs = activity.getSharedPreferences(prefkey, Context.MODE_PRIVATE);
	}
	
	public void putInt(String key, int integer){
		Editor editor = prefs.edit();
		editor.putInt(key, integer);
		editor.commit();
	}
	
	public void putString(String key, String value){
		Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public int getInt(String key){
		return prefs.getInt(key, 0);
	}
	
	public String getString(String key){
		return prefs.getString(key, "");
	}
}

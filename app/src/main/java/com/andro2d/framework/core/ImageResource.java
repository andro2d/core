package com.andro2d.framework.core;

import android.graphics.Bitmap;

import com.andro2d.framework.graphics.scene.SimpleView;

public final class ImageResource {

	private String resourceName;

	public ImageResource(String resourcename, String rname, SimpleView sview) {
		this.resourceName = resourcename;
		GameResource.getInstance().addBitmap(resourcename, rname, sview);
	}
	
	public Bitmap getImage(){
		return GameResource.getInstance().getBitmap(resourceName);
	}
}

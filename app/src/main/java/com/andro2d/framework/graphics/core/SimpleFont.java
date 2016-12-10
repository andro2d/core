package com.andro2d.framework.graphics.core;

import android.graphics.Typeface;

public final class SimpleFont{

	public Typeface typeface;
	public float size;
	public int style;
	
	public SimpleFont(Typeface typeface, float size, int style) {
		this.typeface = typeface;
		this.size = size;
		this.style = style;
	}
}

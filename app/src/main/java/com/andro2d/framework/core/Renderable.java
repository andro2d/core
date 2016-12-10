package com.andro2d.framework.core;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Serializable;

public interface Renderable extends Serializable {
	void update(float fps);
	void render(Canvas canvas, Paint paint);
}

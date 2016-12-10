package com.andro2d.framework.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.andro2d.framework.core.Renderable;
import com.andro2d.framework.core.SimpleScript;
import com.andro2d.framework.graphics.scene.SimpleView;
import com.andro2d.framework.math.Vector2D;

import java.util.HashMap;
import java.util.Map.Entry;

public abstract class Entity implements Renderable {

	private HashMap<String, SimpleScript> scripts;
	public Vector2D location;
	public Entity parent;
	public int width, height;
	public String name;
	public SimpleView sview;
	public int multiplier;
	public boolean render, deleteFlag;
	public float centerRotation;
    public boolean flipHorizontal = false;
    public boolean flipVertical = false;
	protected Bitmap image;
    protected Rect src;

	public Entity(int width, int height, Bitmap image, boolean rescale, SimpleView sview) {
		this.location = new Vector2D();
		this.scripts = new HashMap<>();
		this.width = width;
		this.height = height;
		this.sview = sview;
		this.multiplier = 1;
		this.render = true;
		this.deleteFlag = false;

		if(image != null) {
			if(rescale) {
				this.image = Bitmap.createScaledBitmap(image, width, height, false);
			} else {
				this.image = image;
			}
		}
	}
	
	public Entity(Bitmap image, boolean rescale, SimpleView sview) {
		this(image.getWidth(), image.getWidth(), image, rescale, sview);
	}

	public void addScript(String name, SimpleScript script){
		scripts.put(name, script);
	}
	
	public void removeScript(String name) {
		scripts.get(name).deleteFlag = true;
	}
	
    public HashMap<String, SimpleScript> getScripts(){
		return this.scripts;
	}

    public Rect getBounds() {
        return new Rect(
                (int)location.x, (int)location.y,
                width + ((int)location.x), height + ((int)location.y));
    }
	
	@SuppressWarnings("unchecked")
	public <T extends SimpleScript> T getScript(String name, Class<T> c) {
		if(scripts.containsKey(name)) {
			return (T) scripts.get(name);
		}
		return null;
	}

	@Override
	public void update(float fps) {
		updateScripts(fps);
	}
	
	@Override
	public void render(Canvas canvas, Paint paint) {
		if(!render) {
			return;
		}

        if(image != null) {
            canvas.save();
            canvas.rotate(this.centerRotation, location.x+width/2, location.y+height/2);
            int x = (int) location.x;
            int y = (int) location.y;

            if (parent != null) {
                x += parent.location.x;
                y += parent.location.y;
            }

//            TODO: fix later
//            if(flipHorizontal) {
//                canvas.scale(-1, 1, width / 2, height / 2);
//            }
//
//            if(flipVertical) {
//                canvas.scale(1, -1, width / 2, height / 2);
//            }

            canvas.drawBitmap(image, src,
                new Rect(
                    x,
                    y,
                    x + width,
                    y + height), paint);

            transformedRender(canvas, paint);
            canvas.restore();
        }

        renderScripts(canvas, paint);
	}

    protected void transformedRender(Canvas canvas, Paint paint) {}
	
	protected void renderScripts(Canvas canvas, Paint paint) {
		for(Entry<String, SimpleScript> en : scripts.entrySet()) {
			if(en.getValue().deleteFlag) {
				scripts.remove(en.getKey());
				break;
			} else {
				en.getValue().render(canvas, paint);
			}
		}
	}

	protected void updateScripts(float fps) {
		for(Entry<String, SimpleScript> en : scripts.entrySet()) {
			if(en.getValue().deleteFlag) {
				scripts.remove(en.getKey());
				break;
			} else {
				en.getValue().update(fps);
			}
		}
	}
}

package com.andro2d.framework.graphics.scene;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.andro2d.studio.util.GameLoader;
import com.andro2d.framework.core.Node;
import com.andro2d.framework.core.Renderable;
import com.andro2d.framework.core.SimpleScript;
import com.andro2d.framework.input.Touchable;
import com.andro2d.framework.sprite.Entity;
import com.andro2d.framework.util.ActivityHelper;

import java.util.Map;
import java.util.Vector;

public abstract class SimpleView extends SurfaceView implements Runnable, Renderable, SurfaceHolder.Callback {

    private Activity activity;
	private Paint paint;
	private SurfaceHolder holder;
	private Canvas canvas;
    private Node mainNode;
	private Thread gameThread;
    private ActivityHelper activityHelper;
    private GameLoader gameLoader;
	private volatile boolean playing;
	private long timeThisFrame;
	private float fps;
    public boolean debug;

	public SimpleView(Context context) {
		super(context);
        this.debug = true;
		this.paint = new Paint();
        this.mainNode = new Node(this);
		this.holder = getHolder();
        this.holder.addCallback(this);
        this.activityHelper = ActivityHelper.getInstance();
        this.gameLoader = new GameLoader(this, this.mainNode.getChildren());
        this.activity = this.activityHelper.getActivity(context);
        this.playing = true;
	}

    public void preRender(Canvas canvas, Paint paint) {}

	public void postRender(Canvas canvas, Paint paint) {}

    private void updateInternal() {
        this.updateInternal(this.fps);
    }

    private void drawInternal() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            canvas.drawColor(Color.BLACK);

            renderInternal(canvas);

            if (debug) {
                paint.setColor(Color.MAGENTA);
                paint.setTextSize(45);
                canvas.drawText("FPS:" + fps, 20, 40, paint);
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }
	
	private void updateInternal(float delta){
        this.mainNode.update(delta);
		this.update(delta);
	}

	private void renderInternal(Canvas canvas){
		this.preRender(canvas, paint);
        this.mainNode.render(canvas, paint);
        this.render(canvas, paint);
		this.postRender(canvas, paint);
	}

	public Activity getActivity(){
		return this.activity;
	}

	public float getFPS(){
		return this.fps;
	}

	public void addRenderable(Renderable renderable) {
		this.mainNode.addChild(renderable);
	}

	public void removeRenderable(Renderable renderable){
		this.mainNode.removeChild(renderable);
	}

	public void pause() {
		playing = false;

		try {
			gameThread.join();
		} catch (InterruptedException e) {
			Log.e("Error:", "joining thread");
		}
	}

	public void resume() {
		playing = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        SimpleView.this.resume();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void run() {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();

            if (fps > 0) {
                updateInternal();
                drawInternal();
            }

            timeThisFrame = System.currentTimeMillis() - startFrameTime;

            if (timeThisFrame > 0) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Vector<Renderable> renderables = this.mainNode.getChildren();
        synchronized (renderables) {
            for(Renderable r : renderables){
                if(!(r instanceof Entity)) { break; }

                Entity en = (Entity) r;

                if(en.deleteFlag){
                    continue;
                }

                if(en instanceof Touchable){
                    if(en.getBounds().contains((int)event.getX(), (int)event.getY())) {
                        ((Touchable) en).onTouch(event);
                        break;
                    }
                }

                boolean check = false;

                for(Map.Entry<String, SimpleScript> entry : en.getScripts().entrySet()) {
                    if(check || entry.getValue().entity.getBounds()
                            .contains((int)event.getX(), (int)event.getY())) {
                        entry.getValue().onTouch(event);
                        check = true;
                    }
                }
            }
            return super.onTouchEvent(event);
        }
    }
}

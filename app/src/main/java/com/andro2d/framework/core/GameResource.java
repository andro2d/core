package com.andro2d.framework.core;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.andro2d.framework.graphics.scene.SimpleView;

import java.util.HashMap;

public final class GameResource {
	private static GameResource instance;
	private static HashMap<String, Bitmap> bitmaps;
	private static HashMap<String, Integer> sounds;
	private static HashMap<String, MediaPlayer> musics;
	private static SoundPool soundPool;
	private boolean sfxon = true, bgmon = true;
		
	private GameResource(){
		bitmaps = new HashMap<String, Bitmap>();
		sounds = new HashMap<String, Integer>();
		musics = new HashMap<String, MediaPlayer>();
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	}
	
	public void load(Activity a){
		SimpleSP sp = new SimpleSP("setting", a);
		if(!sp.getString("sfx").equals("")){
			boolean b = Boolean.parseBoolean(sp.getString("sfx"));
			this.sfxon = b;
		}
		if(!sp.getString("bgm").equals("")){
			boolean b = Boolean.parseBoolean(sp.getString("bgm"));
			this.bgmon = b;
		}
	}
	
	public void setSFX(boolean b, Activity a){
		this.sfxon = b;
		save(a);
	}
	public void setBGM(boolean b, Activity a){
		this.bgmon = b;
		save(a);
	}
	
	public boolean isSFXOn(){return this.sfxon;}
	public boolean isBGMOn(){return this.bgmon;}
	
	public void save(Activity a){
		SimpleSP sp = new SimpleSP("setting", a);
		Boolean b1 = sfxon;
		Boolean b2 = bgmon;
		sp.putString("sfx", b1.toString());
		sp.putString("bgm", b2.toString());
	}
	
	public static GameResource getInstance(){
		if(instance == null){
			instance = new GameResource();
		}
		return instance;
	}
	public void addSound(String name, int rid, SimpleView sview){
		if(!sounds.containsKey(name)){
			sounds.put(name, soundPool.load(sview.getContext(), rid, 1));
		}
	}
	public void addMusic(String name, int rid, SimpleView sview){
		if(!musics.containsKey(name)){
			MediaPlayer mp = MediaPlayer.create(sview.getContext(), rid);
			mp.setLooping(true);
			mp.setVolume(100, 100);
			musics.put(name, mp);
		}
	}
	public void addBitmap(String name, int rid, SimpleView sview){
		if(!bitmaps.containsKey(name)){
			Bitmap b = BitmapFactory.decodeResource(sview.getResources(), rid);
			bitmaps.put(name, b);
		}
	}
	public void addBitmap(String name, String rname, SimpleView sview){
			String[]tmp = rname.split("[.]");
			if(tmp.length > 1){
				rname = tmp[0];
			}
			int rid = sview.getResources().getIdentifier(rname, "drawable", sview.getContext().getPackageName());
			addBitmap(name, rid, sview);
	}
	public int getBitmapCount(){
		return bitmaps.size();
	}
	public void removeBitmap(String name){
		bitmaps.remove(name);
	}
	public MediaPlayer getMusic(String name){
		return musics.get(name);
	}
	public Bitmap getBitmap(String name){
		return bitmaps.get(name);
	}
	public int getSound(String name){
		return sounds.get(name);
	}
	public void playMusic(String name){
		if(bgmon){
			musics.get(name).start();
		}
	}
	public void stopMusic(String name){
		if(bgmon){
			musics.get(name).pause();
		}
	}
	public void playSound(String name, int left, int right){
		soundPool.play(getSound(name), 100, 100, 1, 0, 1);
	}
	public void playSound(String name){
		if(sfxon){
			playSound(name, 100, 100);
		}
	}
}

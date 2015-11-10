package com.pike.games.managers;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.content.Context;

import com.pike.games.eggs.GameActivity;

public class ResourceManager {

	// Singleton pattern
	private static ResourceManager pInstance = new ResourceManager();

	ResourceManager() {

	}

	public synchronized static ResourceManager getInstance() {
		if (pInstance == null) {
			pInstance = new ResourceManager();
		}
		return pInstance;
	}

	// ================================
	// Variables
	public Music mMusic;
	public Sound mSound;

	public Font mFont;

	public ITextureRegion mGameBgTR;

	private GameActivity activity;
	private Engine engine;
	private Context context;
	private Camera camera;

	// ================================
	// Methods

	// setup ResourceManager
	public void setup(GameActivity activity, Engine engine, Context context,
			Camera camera) {
		this.activity = activity;
		this.engine = engine;
		this.context = context;
		this.camera = camera;
	}

	public synchronized void loadGameTextures() {

		// set base path
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/eggs/");

		// load textures
		BuildableBitmapTextureAtlas mGameBgT = new BuildableBitmapTextureAtlas(
				activity.getTextureManager(), 480, 640);
		mGameBgTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mGameBgT, activity, "bg.png");

		try {
			mGameBgT.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 1));
			mGameBgT.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

	}

	public synchronized void unloadGameTextures() {
		// unload textures
		BuildableBitmapTextureAtlas mGameGbT = (BuildableBitmapTextureAtlas) mGameBgTR
				.getTexture();
		mGameGbT.unload();

		// once all textures have been unloaded, try to invoke garbage collector
		System.gc();
	}

	public synchronized void loadSounds() {
		// set search path for SoundFactory and MusicFactory
		SoundFactory.setAssetBasePath("sfx/");
		MusicFactory.setAssetBasePath("sfx/");

		// load sound.mp3 into sound object
		try {
			mSound = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), context, "bounce.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// load music.ogg into music object
		try {
			mMusic = MusicFactory.createMusicFromAsset(
					activity.getMusicManager(), context, "music.ogg");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public synchronized void unloadSounds() {
		if (!mSound.isReleased()) {
			mSound.release();
		}
	}

	public synchronized void loadFonts() {

	}

	public synchronized void unloadFonts() {

	}

}

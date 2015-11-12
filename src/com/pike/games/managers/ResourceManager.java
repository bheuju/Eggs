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
	private Camera camera;

	// ================================
	// Methods

	/**
	 * Setup ResourceManager
	 * 
	 * @param activity
	 * @param engine
	 * @param camera
	 * 
	 */
	public void setup(GameActivity activity, Engine engine, Camera camera) {
		this.activity = activity;
		this.engine = engine;
		this.camera = camera;
	}

	// Getters and Setters for common resources
	public Engine getEngine() {
		return engine;
	}

	public void loadGameResources() {
		loadGameTextures();
		loadGameAudio();
		loadGameFonts();
	}

	public void loadMenuResources() {
		loadMenuTextures();
		loadMenuAudio();
		loadMenuFonts();
	}

	// =====================================
	public void loadSplashScreen() {

	}

	public void loadGameTextures() {

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

	public void unloadGameTextures() {
		// unload textures
		BuildableBitmapTextureAtlas mGameGbT = (BuildableBitmapTextureAtlas) mGameBgTR
				.getTexture();
		mGameGbT.unload();

		// once all textures have been unloaded, try to invoke garbage collector
		System.gc();
	}

	public void loadMenuTextures() {

	}

	public void unloadMenuTextures() {

	}

	public void loadGameAudio() {
		// set search path for SoundFactory and MusicFactory
		SoundFactory.setAssetBasePath("sfx/");
		MusicFactory.setAssetBasePath("sfx/");

		// load sound.mp3 into sound object
		try {
			mSound = SoundFactory.createSoundFromAsset(
					activity.getSoundManager(), activity, "bounce.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// load music.ogg into music object
		try {
			mMusic = MusicFactory.createMusicFromAsset(
					activity.getMusicManager(), activity, "music.ogg");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void unGameAudio() {
		if (!mSound.isReleased()) {
			mSound.release();
		}
	}

	public void loadMenuAudio() {

	}

	public void unloadMenuAudio() {

	}

	public void loadGameFonts() {

	}

	public void unloadGameFonts() {

	}

	public void loadMenuFonts() {

	}

	public void unloadMenuFonts() {

	}

}

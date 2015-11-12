package com.pike.games.managers;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.pike.games.eggs.GameActivity;

public class ResourceManager {

	// Singleton pattern
	private static ResourceManager pInstance = new ResourceManager();

	ResourceManager() {

	}

	public static ResourceManager getInstance() {
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
	public ITextureRegion mSplashTR;

	private GameActivity activity;
	private Engine engine;
	private Camera camera;

	private TextureManager texManager;
	private VertexBufferObjectManager vboManager;

	// Paths
	private String pathGfxSplash = "gfx/splash/";
	private String pathGfxGame = "gfx/game/";
	private String pathGfxMenu = "gfx/menu/";

	private String pathSfxGame = "sfx/game/";
	private String pathSfxMenu = "sfx/menu/";

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

		this.texManager = activity.getTextureManager();
		this.vboManager = activity.getVertexBufferObjectManager();
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

	public void loadSplashResources() {
		loadSplashTexture();
		loadSplashAudio();
	}

	// =====================================
	public void loadSplashTexture() {

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(pathGfxSplash);

		BuildableBitmapTextureAtlas mSplashTA = new BuildableBitmapTextureAtlas(
				texManager, 512, 512);
		mSplashTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mSplashTA, activity, "splash.png");
		try {
			mSplashTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 0));
			mSplashTA.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
	}

	public synchronized void unloadSplashScreen() {
		// unload texture
		BuildableBitmapTextureAtlas mSplashTA = (BuildableBitmapTextureAtlas) mSplashTR
				.getTexture();
		mSplashTA.unload();

		// try invoke garbage collector
		System.gc();
	}

	public void loadGameTextures() {

		// set base path
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(pathGfxGame);

		// load textures
		BuildableBitmapTextureAtlas mGameBgTA = new BuildableBitmapTextureAtlas(
				texManager, 480, 640);
		mGameBgTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mGameBgTA, activity, "bg.png");

		try {
			mGameBgTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 1));
			mGameBgTA.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

	}

	public void unloadGameTextures() {
		// unload textures
		BuildableBitmapTextureAtlas mGameBgTA = (BuildableBitmapTextureAtlas) mGameBgTR
				.getTexture();
		mGameBgTA.unload();

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

	public void unloadGameAudio() {
		if (!mSound.isReleased()) {
			mSound.release();
		}
	}

	public void loadSplashAudio() {

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

	// Getters and Setters for common resources
	public Engine getEngine() {
		return engine;
	}

	public VertexBufferObjectManager getVertexBufferObjectManager() {
		return vboManager;
	}

}

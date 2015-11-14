package com.pike.games.managers;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.BitmapShader;
import android.graphics.Color;

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

	private String pathFonts = "fonts/";

	// Texture Regions
	public ITextureRegion mGameBgTR;
	public ITextureRegion mSplashTR;

	public ITextureRegion mMenuBgTR;
	public ITextureRegion mMenuPlayTR;
	public ITextureRegion mMenuOptionsTR;
	public ITextureRegion mMenuLogoTR;

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

	public void loadSplashResources() {
		loadSplashTexture();
		loadSplashAudio();
	}

	public void loadMenuResources() {
		loadMenuTextures();
		loadMenuAudio();
		loadMenuFonts();
	}

	public void loadGameResources() {
		loadGameTextures();
		loadGameAudio();
		loadGameFonts();
	}

	// =====================================
	// ---- SPLASH ----

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

	public void unloadSplashScreen() {
		// unload texture
		BuildableBitmapTextureAtlas mSplashTA = (BuildableBitmapTextureAtlas) mSplashTR
				.getTexture();
		mSplashTA.unload();

		// try invoke garbage collector
		System.gc();
	}

	public void loadSplashAudio() {

	}

	// ---- MENU ----
	public void loadMenuTextures() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(pathGfxMenu);
		BuildableBitmapTextureAtlas mMenuTA = new BuildableBitmapTextureAtlas(
				texManager, 512, 1024);

		mMenuLogoTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mMenuTA, activity, "logo.png");
		mMenuBgTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mMenuTA, activity, "bg.png");
		mMenuPlayTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mMenuTA, activity, "play.png");
		mMenuOptionsTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mMenuTA, activity, "options.png");

		try {
			mMenuTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 1));
			mMenuTA.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

	}

	public void unloadMenuTextures() {
		BuildableBitmapTextureAtlas mMenuTA = (BuildableBitmapTextureAtlas) mMenuPlayTR
				.getTexture();
		mMenuTA.unload();

		mMenuTA = (BuildableBitmapTextureAtlas) mMenuOptionsTR.getTexture();
		mMenuTA.unload();

		System.gc();
	}

	public void loadMenuAudio() {

	}

	public void unloadMenuAudio() {

	}

	public void loadMenuFonts() {
		FontFactory.setAssetBasePath(pathFonts);
		final ITexture mFontTA = new BitmapTextureAtlas(texManager, 256, 256);

		mFont = FontFactory.createStrokeFromAsset(activity.getFontManager(),
				mFontTA, activity.getAssets(), "font.ttf", 50, true,
				Color.GRAY, 2, Color.BLACK);
		mFont.load();
	}

	public void unloadMenuFonts() {
		mFont.unload();
	}

	// ---- GAME ----
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

	public void loadGameFonts() {

	}

	public void unloadGameFonts() {

	}

	// =====================================
	// Getters and Setters for common resources
	public Engine getEngine() {
		return engine;
	}

	public VertexBufferObjectManager getVertexBufferObjectManager() {
		return vboManager;
	}

	public Camera getCamera() {
		return camera;
	}

}

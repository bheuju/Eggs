package com.pike.games.managers;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
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
	private BoundCamera camera;

	private TextureManager texManager;
	private VertexBufferObjectManager vboManager;

	// Paths
	private String pathGfxSplash = "gfx/splash/";
	private String pathGfxGame = "gfx/game/";
	private String pathGfxMainMenu = "gfx/menu/main/";
	private String pathGfxPauseMenu = "gfx/menu/pause/";
	private String pathGfxQuitMenu = "gfx/menu/quit/";

	private String pathSfxGame = "sfx/game/";
	private String pathSfxMenu = "sfx/menu/";

	private String pathFonts = "fonts/";

	// Texture Regions
	// =================
	// splash
	public ITextureRegion mSplashTR;
	// main menu
	public ITextureRegion mMenuBgTR;
	public ITextureRegion mMenuPlayTR;
	public ITextureRegion mMenuSettingsTR;
	public ITextureRegion mMenuCreditsTR;
	// pause menu
	public ITextureRegion mPauseMenuBoardTR;
	public ITextureRegion mPauseMenuPlayButtonTR;
	public ITextureRegion mPauseMenuReloadButtonTR;
	public ITextureRegion mPauseMenuMenuButtonTR;
	// quit menu
	public ITextureRegion mQuitMenuBoardTR;
	public ITextureRegion mQuitMenuAcceptButtonTR;
	public ITextureRegion mQuitMenuRejectButtonTR;
	// game
	public ITextureRegion mGameBgTR;
	public ITextureRegion mEggTR;
	public ITextureRegion mNestTR;
	public ITextureRegion mNestFrontTR;

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
	public void setup(GameActivity activity, Engine engine, BoundCamera camera) {
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
				texManager, 512, 256);
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
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath(pathGfxMainMenu);
		BuildableBitmapTextureAtlas mMenuTA = new BuildableBitmapTextureAtlas(
				texManager, 512, 1024);

		// mMenuLogoTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
		// mMenuTA, activity, "logo.png");
		mMenuBgTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mMenuTA, activity, "bg-logo.png");
		mMenuPlayTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mMenuTA, activity, "play.png");
		mMenuSettingsTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mMenuTA, activity, "settings.png");
		mMenuCreditsTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mMenuTA, activity, "credits.png");

		try {
			mMenuTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 1));
			mMenuTA.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

		// load quit menu textures
		loadQuitMenuTextures();

	}

	public void loadQuitMenuTextures() {
		// pause menu textures
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath(pathGfxQuitMenu);

		BuildableBitmapTextureAtlas mQuitMenuTA = new BuildableBitmapTextureAtlas(
				texManager, 512, 256);
		mQuitMenuBoardTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mQuitMenuTA, activity, "quit-menu-board.png");
		mQuitMenuAcceptButtonTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mQuitMenuTA, activity, "accept.png");
		mQuitMenuRejectButtonTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mQuitMenuTA, activity, "reject.png");

		try {
			mQuitMenuTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 1));
			mQuitMenuTA.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

	}

	public void unloadQuitMenuTextures() {
		BuildableBitmapTextureAtlas mQuitMenuTA = (BuildableBitmapTextureAtlas) mQuitMenuBoardTR
				.getTexture();
		mQuitMenuTA.unload();
	}

	public void unloadMenuTextures() {
		BuildableBitmapTextureAtlas mMenuTA = (BuildableBitmapTextureAtlas) mMenuBgTR
				.getTexture();
		mMenuTA.unload();

		// mMenuTA = (BuildableBitmapTextureAtlas) mMenuSettingsTR.getTexture();
		// mMenuTA.unload();

		// unload quitmenu textures

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
				mFontTA, activity.getAssets(), "CarterOne.ttf", 30, true,
				Color.YELLOW, 1, Color.RED);
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
		BuildableBitmapTextureAtlas mGameTA = new BuildableBitmapTextureAtlas(
				texManager, 512, 1024);
		mGameBgTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mGameTA, activity, "bg.png");
		mNestTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mGameTA, activity, "nest.png");
		mEggTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mGameTA, activity, "egg.png");
		mNestFrontTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mGameTA, activity, "nestFront.png");

		try {
			mGameTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 1));
			mGameTA.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}

		// load pause menu
		loadPauseMenuTextures();

	}

	public void loadPauseMenuTextures() {
		// pause menu textures
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath(pathGfxPauseMenu);

		BuildableBitmapTextureAtlas mPauseMenuTA = new BuildableBitmapTextureAtlas(
				texManager, 512, 256);
		mPauseMenuBoardTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mPauseMenuTA, activity, "pause-menu-board.png");
		mPauseMenuPlayButtonTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mPauseMenuTA, activity, "play.png");
		mPauseMenuReloadButtonTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mPauseMenuTA, activity, "reload.png");
		mPauseMenuMenuButtonTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mPauseMenuTA, activity, "menu.png");

		try {
			mPauseMenuTA
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 1));
			mPauseMenuTA.load();
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

	public void unloadPauseMenuTextures() {
		BuildableBitmapTextureAtlas mPauseMenuTA = (BuildableBitmapTextureAtlas) mPauseMenuBoardTR
				.getTexture();
		mPauseMenuTA.unload();
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
	public GameActivity getActivity() {
		return activity;
	}

	public Engine getEngine() {
		return engine;
	}

	public VertexBufferObjectManager getVertexBufferObjectManager() {
		return vboManager;
	}

	public BoundCamera getCamera() {
		return camera;
	}

}

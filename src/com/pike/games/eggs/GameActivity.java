package com.pike.games.eggs;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RelativeResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

// Main Activity for the game

public class GameActivity extends BaseGameActivity {

	private final int CAMERA_WIDTH = 400;
	private final int CAMERA_HEIGHT = 640;

	private Camera mCamera;
	private Scene mScene;

	Music mMusic;
	Sound mSound;

	// =========== CREATE ENGINE ===========
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new FixedStepEngine(pEngineOptions, 50);
	}

	// =========== CREATE ENGINE OPTIONS ===========
	@Override
	public EngineOptions onCreateEngineOptions() {

		// define camera object
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		// define engine options to be applied to engine object
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_SENSOR, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);

		// enable sounds and music
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);

		return engineOptions;

	}

	// =========== CREATE RESOURCES ===========
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {

		// ----------------- Sound -----------------
		// set search path for SoundFactory and MusicFactory
		SoundFactory.setAssetBasePath("sfx/");
		MusicFactory.setAssetBasePath("sfx/");

		// load sound.mp3 into sound object
		try {
			mSound = SoundFactory.createSoundFromAsset(getSoundManager(), this,
					"bounce.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// load music.ogg into music object
		try {
			mMusic = MusicFactory.createMusicFromAsset(getMusicManager(), this,
					"music.ogg");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ----------------- Textures -----------------
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/eggs/");

		mBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 500,
				500);
		mRectangle1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mBitmapTextureAtlas, this, "egg.png", 0, 0);
		mBitmapTextureAtlas.load();
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	BitmapTextureAtlas mBitmapTextureAtlas;
	ITextureRegion mRectangle1, mRectangle2;

	// =========== CREATE SCENES ===========
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {

		mScene = new Scene();
		mScene.setBackground(new Background(0.09804f, 0.6274f, 0));

		pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	}

	// =========== POPULATE SCENE ===========
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {

		Sprite egg = new Sprite(100, 100, mRectangle1,
				getVertexBufferObjectManager());
		mScene.attachChild(egg);

		pOnPopulateSceneCallback.onPopulateSceneFinished();

	}

	@Override
	public synchronized void onResumeGame() {

		if (mMusic != null && !mMusic.isPlaying()) {
			mMusic.play();
			Log.d("Music", "Playing");
		}

		super.onResumeGame();
	}

	@Override
	public synchronized void onPauseGame() {

		if (mMusic != null && mMusic.isPlaying()) {
			mMusic.pause();
		}

		super.onPauseGame();
	}

}

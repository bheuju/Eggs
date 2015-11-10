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
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import com.pike.games.managers.ResourceManager;

import android.util.Log;

// Main Activity for the game

public class GameActivity extends BaseGameActivity {

	private final int CAMERA_WIDTH = 400;
	private final int CAMERA_HEIGHT = 640;

	private Camera mCamera;
	private Scene mScene;

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

		// setup resource manager
		ResourceManager.getInstance().setup(this, getEngine(),
				getApplicationContext(), mCamera);

		// load sounds
		ResourceManager.getInstance().loadSounds();

		// load textures
		ResourceManager.getInstance().loadGameTextures();

		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

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

		Sprite egg = new Sprite(200, 320,
				ResourceManager.getInstance().mGameBgTR,
				getVertexBufferObjectManager());
		mScene.attachChild(egg);

		pOnPopulateSceneCallback.onPopulateSceneFinished();

	}

	@Override
	public synchronized void onResumeGame() {

		Music mMusic = ResourceManager.getInstance().mMusic;
		if (mMusic != null && !mMusic.isPlaying()) {
			mMusic.play();
			Log.d("Music", "Playing");
		}

		super.onResumeGame();
	}

	@Override
	public synchronized void onPauseGame() {

		Music mMusic = ResourceManager.getInstance().mMusic;
		if (mMusic != null && mMusic.isPlaying()) {
			mMusic.pause();
		}

		super.onPauseGame();
	}

}

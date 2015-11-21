package com.pike.games.eggs;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;
import android.view.KeyEvent;

import com.pike.games.managers.ResourceManager;
import com.pike.games.managers.SceneManager;

// Main Activity for the game

public class GameActivity extends BaseGameActivity {

	private final int CAMERA_WIDTH = 400;
	private final int CAMERA_HEIGHT = 640;

	private BoundCamera mCamera;
	private Scene mScene;

	// =========== CREATE ENGINE ===========
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new FixedStepEngine(pEngineOptions, 60);
	}

	// =========== CREATE ENGINE OPTIONS ===========
	@Override
	public EngineOptions onCreateEngineOptions() {

		// define camera object
		mCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		// define engine options to be applied to engine object
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(
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
		ResourceManager.getInstance().setup(this, this.getEngine(), mCamera);

		// load gameResources
		// ResourceManager.getInstance().loadGameResources();

		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	// =========== CREATE SCENES ===========
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {

		// create splash scene
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);

		// pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	}

	// =========== POPULATE SCENE ===========
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {

		mEngine.registerUpdateHandler(new TimerHandler(2f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						SceneManager.getInstance().createMenuScene();

					}
				}));

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

	@Override
	protected void onDestroy() {
		System.exit(0);
		super.onDestroy();
	}

	// Handling back key press
	@Override
	public synchronized boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		}
		return false;
		// return super.onKeyDown(keyCode, event);
	}

}

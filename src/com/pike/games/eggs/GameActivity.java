package com.pike.games.eggs;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

public class GameActivity extends BaseGameActivity {

	private final int CAMERA_WIDTH = 800;
	private final int CAMERA_HEIGHT = 1280;

	private Camera mCamera;
	private Scene mScene;

	Music mMusic;
	Sound mSound;

	@Override
	public EngineOptions onCreateEngineOptions() {

		// defining camera object
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		// declare and define engine options to be applied to engine object
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_SENSOR, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);

		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);

		return engineOptions;

	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {

		// set search path for SoundFactory and MusicFactory
		SoundFactory.setAssetBasePath("sfx/");
		MusicFactory.setAssetBasePath("sfx/");

		// load sound.mp3 into sound object
		try {
			mSound = SoundFactory.createSoundFromAsset(getSoundManager(), this,
					"sound.mp3");
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

		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {

		mScene = new Scene();
		mScene.setBackground(new Background(0.09804f, 0.6274f, 0));

		pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {

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

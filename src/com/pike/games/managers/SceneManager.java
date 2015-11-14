package com.pike.games.managers;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import Scenes.GameScene;
import Scenes.LoadingScene;
import Scenes.MainMenuScene;
import Scenes.SplashScene;

import android.util.Log;

import com.pike.games.eggs.BaseScene;

public class SceneManager {

	// Singleton pattern
	private static SceneManager pInstance = new SceneManager();

	public SceneManager() {

	}

	public static SceneManager getInstance() {
		if (pInstance == null) {
			pInstance = new SceneManager();
		}
		return pInstance;
	}

	// Scenes
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene gameScene;
	private BaseScene loadingScene;

	// Scene enum
	public enum SceneType {
		SCENE_SPLASH, SCENE_MENU, SCENE_GAME, SCENE_LOADING
	}

	private BaseScene mCurrentScene;
	private SceneType mCurrentSceneType;

	// Resources
	private Engine mEngine = ResourceManager.getInstance().getEngine();

	// ====================
	public void setScene(BaseScene scene) {
		mCurrentScene = scene;
		mEngine.setScene(mCurrentScene);
		mCurrentSceneType = scene.getSceneType();
		Log.e("Scene is: ", mCurrentSceneType.toString());
	}

	public void setScene(SceneType sceneType) {
		switch (sceneType) {
		case SCENE_GAME:
			setScene(menuScene);
			break;
		case SCENE_LOADING:
			setScene(loadingScene);
			break;
		case SCENE_MENU:
			setScene(menuScene);
			break;
		case SCENE_SPLASH:
			setScene(splashScene);
			break;
		default:
			break;
		}
	}

	// Scenes methods
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {

		ResourceManager.getInstance().loadSplashResources();

		splashScene = new SplashScene();
		// mCurrentScene = splashScene;
		setScene(splashScene);

		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);

	}

	public void disposeSplashScene() {
		ResourceManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}

	public void createMenuScene() {
		ResourceManager.getInstance().loadMenuResources();
		menuScene = new MainMenuScene();
		loadingScene = new LoadingScene();
		setScene(menuScene);

		disposeSplashScene();
	}

	public void loadGameScene() {
		setScene(loadingScene);
		ResourceManager.getInstance().unloadMenuTextures();

		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						ResourceManager.getInstance().loadGameResources();
						gameScene = new GameScene();
						setScene(gameScene);
					}
				}));
	}

	public synchronized void loadMenuScene() {
		setScene(loadingScene);
		gameScene.disposeScene();
		ResourceManager.getInstance().unloadGameTextures();

		mEngine.registerUpdateHandler(new TimerHandler(2f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						ResourceManager.getInstance().loadMenuTextures();
						menuScene = new MainMenuScene();
						setScene(menuScene);

					}
				}));

	}

	// Getters and Setters
	// ==========================
	public BaseScene getCurrentScene() {
		return mCurrentScene;
	}

	public SceneType getCurrentSceneType() {
		return mCurrentSceneType;
	}
}

package com.pike.games.managers;

import org.andengine.engine.Engine;
import org.andengine.entity.sprite.Sprite;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

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
	private BaseScene menusScene;
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
	}

	public void setScene(SceneType sceneType) {
		switch (sceneType) {
		case SCENE_GAME:
			setScene(menusScene);
			break;
		case SCENE_LOADING:
			setScene(loadingScene);
			break;
		case SCENE_MENU:
			setScene(menusScene);
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
		menusScene = new MainMenuScene();
		setScene(menusScene);

		disposeSplashScene();
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

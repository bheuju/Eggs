package com.pike.games.managers;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;

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

	// Getters and Setters
	// ==========================
	public BaseScene getCurrentScene() {
		return mCurrentScene;
	}

	public SceneType getCurrentSceneType() {
		return mCurrentSceneType;
	}
}

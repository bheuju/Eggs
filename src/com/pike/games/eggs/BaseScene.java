package com.pike.games.eggs;

import org.andengine.entity.scene.Scene;

import com.pike.games.managers.SceneManager.SceneType;

public abstract class BaseScene extends Scene {

	public BaseScene() {

	}

	public abstract void createScene();

	public abstract SceneType getSceneType();

	public abstract void deleteScene();
}
package com.pike.games.eggs;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.pike.games.managers.ResourceManager;
import com.pike.games.managers.SceneManager.SceneType;

public abstract class BaseScene extends Scene {

	protected ResourceManager mResourceManager;
	protected VertexBufferObjectManager mVboManager;

	public BaseScene() {
		mResourceManager = ResourceManager.getInstance();
		mVboManager = ResourceManager.getInstance()
				.getVertexBufferObjectManager();
		createScene();
	}

	public abstract void createScene();

	public abstract void onBackKeyPressed();

	public abstract SceneType getSceneType();

	public abstract void disposeScene();
}
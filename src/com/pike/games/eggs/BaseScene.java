package com.pike.games.eggs;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.pike.games.managers.ResourceManager;
import com.pike.games.managers.SceneManager.SceneType;

public abstract class BaseScene extends Scene {

	protected ResourceManager mResourceManager;
	protected VertexBufferObjectManager mVboManager;
	protected Camera mCamera;
	protected GameActivity mActivity;

	public BaseScene() {
		this.mResourceManager = ResourceManager.getInstance();
		this.mVboManager = ResourceManager.getInstance()
				.getVertexBufferObjectManager();
		this.mCamera = ResourceManager.getInstance().getCamera();
		this.mActivity = ResourceManager.getInstance().getActivity();
		createScene();
	}

	public abstract void createScene();

	public abstract void onBackKeyPressed();

	public abstract SceneType getSceneType();

	public abstract void disposeScene();
}
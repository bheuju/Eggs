package com.pike.games.eggs;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import com.pike.games.managers.ResourceManager;

public class BaseMenuScene extends MenuScene {

	protected ResourceManager mResourceManager;
	protected VertexBufferObjectManager mVboManager;
	protected Camera mCamera;
	protected GameActivity mActivity;

	public BaseMenuScene() {
		super(ResourceManager.getInstance().getCamera());

		this.mResourceManager = ResourceManager.getInstance();
		this.mVboManager = ResourceManager.getInstance()
				.getVertexBufferObjectManager();
		this.mCamera = ResourceManager.getInstance().getCamera();
		this.mActivity = ResourceManager.getInstance().getActivity();

		// back mask background
		this.setBackgroundEnabled(false);
		Rectangle bg = new Rectangle(200, 320, 400, 640, ResourceManager
				.getInstance().getVertexBufferObjectManager());
		bg.setColor(Color.BLACK);
		bg.setAlpha(0.5f);

		attachChild(bg);
	}

}

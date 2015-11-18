package com.pike.games.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.adt.color.Color;

import com.pike.games.eggs.BaseScene;
import com.pike.games.managers.SceneManager.SceneType;

public class SplashScene extends BaseScene {

	public Sprite splash;

	@Override
	public void createScene() {

		this.setBackground(new Background(Color.CYAN));
		splash = new Sprite(0, 0, mResourceManager.mSplashTR, mVboManager);
		splash.setPosition(200, 400);
		this.attachChild(splash);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}

}

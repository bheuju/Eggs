package com.pike.games.scenes;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.IUpdateHandler.IUpdateHandlerMatcher;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityComparator;
import org.andengine.entity.IEntityMatcher;
import org.andengine.entity.IEntityParameterCallable;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierMatcher;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.IVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.IDisposable.AlreadyDisposedException;
import org.andengine.util.adt.color.Color;
import org.andengine.util.adt.transformation.Transformation;

import android.util.Log;

import com.pike.games.eggs.BaseScene;
import com.pike.games.managers.SceneManager;
import com.pike.games.managers.SceneManager.SceneType;

public class MainMenuScene extends BaseScene {

	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_SETTINGS = 1;
	private final int MENU_CREDITS = 2;

	private void createBackground() {
		Sprite bg = new Sprite(200, 320, mResourceManager.mMenuBgTR,
				mVboManager);
		//bg.setScale(1.5625f);
		this.attachChild(bg);
	}

	private void createMenuChildScene() {
		menuChildScene = new MenuScene(mCamera);
		menuChildScene.setPosition(0, 0);

		// Attach logo
		// Sprite logo = new Sprite(200, 500, mResourceManager.mMenuLogoTR,
		// mVboManager);
		// logo.setScale(1.5625f);
		// menuChildScene.attachChild(logo);

		// Attach menu items
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_PLAY, mResourceManager.mMenuPlayTR,
						mVboManager), 0.8f, 1f);
		final IMenuItem settingsMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_SETTINGS,
						mResourceManager.mMenuSettingsTR, mVboManager), 0.8f,
				1f);
		final IMenuItem creditsMenuItems = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_CREDITS,
						mResourceManager.mMenuCreditsTR, mVboManager), 0.8f, 1f);

		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(settingsMenuItem);
		menuChildScene.addMenuItem(creditsMenuItems);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		playMenuItem.setPosition(200, 300);
		settingsMenuItem.setPosition(200, 210);
		creditsMenuItems.setPosition(200, 120);

		menuChildScene
				.setOnMenuItemClickListener(new IOnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClicked(MenuScene pMenuScene,
							IMenuItem pMenuItem, float pMenuItemLocalX,
							float pMenuItemLocalY) {

						switch (pMenuItem.getID()) {
						case MENU_PLAY:
							Log.e("Menu Clicked", "PLAY");
							SceneManager.getInstance().loadGameScene();
							return true;
						case MENU_SETTINGS:
							Log.e("Menu Clicked", "SETTINGS");
							return true;
						case MENU_CREDITS:
							Log.e("Menu Clicked", "CREDITS");
							return true;
						default:
							return false;
						}
					}
				});

		setChildScene(menuChildScene);
	}

	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}

}

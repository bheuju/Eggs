package Scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;

import com.pike.games.eggs.BaseScene;
import com.pike.games.managers.SceneManager.SceneType;

public class MainMenuScene extends BaseScene {

	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;

	private void createBackground() {
		Sprite bg = new Sprite(200, 320, mResourceManager.mMenuBgTR,
				mVboManager);
		bg.setScale(1.5625f);
		this.attachChild(bg);
	}

	private void createMenuChildScene() {
		menuChildScene = new MenuScene(mCamera);
		menuChildScene.setPosition(0, 0);

		// Attach logo
		Sprite logo = new Sprite(200, 500, mResourceManager.mMenuLogoTR,
				mVboManager);
		logo.setScale(1.5625f);
		menuChildScene.attachChild(logo);

		// Attach menu items
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_PLAY, mResourceManager.mMenuPlayTR,
						mVboManager), 1f, 0.8f);
		final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_OPTIONS,
						mResourceManager.mMenuOptionsTR, mVboManager), 1f, 0.8f);

		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(optionsMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		playMenuItem.setPosition(200, 310);
		// playMenuItem.setScale(0.8f);
		optionsMenuItem.setPosition(200, 200);
		// optionsMenuItem.setScale(0.8f);

		menuChildScene
				.setOnMenuItemClickListener(new IOnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClicked(MenuScene pMenuScene,
							IMenuItem pMenuItem, float pMenuItemLocalX,
							float pMenuItemLocalY) {

						switch (pMenuItem.getID()) {
						case MENU_PLAY:
							Log.e("Menu Clicked", "PLAY");
							return true;
						case MENU_OPTIONS:
							Log.e("Menu Clicked", "OPTIONS");
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

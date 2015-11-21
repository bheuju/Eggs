package com.pike.games.menus;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;

import com.pike.games.eggs.BaseMenuScene;
import com.pike.games.managers.SceneManager;
import com.pike.games.scenes.GameScene;

public class PauseMenu extends BaseMenuScene {

	public PauseMenu() {
		attachPauseMenuBoard();
		attachButtons();
	}

	// private MenuScene pauseMenuScene;

	private final int BUTTON_PLAY = 0;
	private final int BUTTON_RELOAD = 1;
	private final int BUTTON_MENU = 2;

	private void attachPauseMenuBoard() {
		Sprite menuBoard = new Sprite(200, 320,
				mResourceManager.mPauseMenuBoardTR, mVboManager);
		this.attachChild(menuBoard);
	}

	private void attachButtons() {
		// pauseMenuScene = new MenuScene(ResourceManager.getInstance()
		// .getCamera());
		// pauseMenuScene.
		setPosition(0, 0);

		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(BUTTON_PLAY,
						mResourceManager.mPauseMenuPlayButtonTR, mVboManager),
				0.8f, 1f);
		final IMenuItem reloadMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(BUTTON_RELOAD,
						mResourceManager.mPauseMenuReloadButtonTR, mVboManager),
				0.8f, 1f);
		final IMenuItem menuMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(BUTTON_MENU,
						mResourceManager.mPauseMenuMenuButtonTR, mVboManager),
				0.8f, 1f);

		// pauseMenuScene.addMenuItem(playMenuItem);
		// pauseMenuScene.addMenuItem(reloadMenuItem);
		// pauseMenuScene.addMenuItem(menuMenuItem);
		addMenuItem(playMenuItem);
		addMenuItem(reloadMenuItem);
		addMenuItem(menuMenuItem);

		// pauseMenuScene.buildAnimations();
		// pauseMenuScene.setBackgroundEnabled(false);
		buildAnimations();
		setBackgroundEnabled(false);

		playMenuItem.setPosition(95 + 4, 70 + 230);
		reloadMenuItem.setPosition(196 + 4, 70 + 230);
		menuMenuItem.setPosition(302 + 4, 70 + 230);

		// pauseMenuScene.
		setOnMenuItemClickListener(new IOnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClicked(MenuScene pMenuScene,
					IMenuItem pMenuItem, float pMenuItemLocalX,
					float pMenuItemLocalY) {

				switch (pMenuItem.getID()) {
				case BUTTON_PLAY:
					Log.e("Pause Menu item clicked: ", "PLAY");
					back();
					return true;
				case BUTTON_RELOAD:
					Log.e("Pause Menu item clicked: ", "RELOAD");
					((GameScene) PauseMenu.this.mParentScene).resetGame();

					// resetGame();
					return true;
				case BUTTON_MENU:
					Log.e("Pause Menu item clicked: ", "MENU");
					// mCamera.setBoundsEnabled(false);
					((GameScene) PauseMenu.this.mParentScene).exitGameScene();
					// SceneManager.getInstance().loadMenuScene();
					return true;
				default:
					return false;
				}

			}
		});

		// setChildScene(pauseMenuScene);

	}

	// @Override
	// public void createScene() {
	// this.setBackgroundEnabled(false);
	// // this.setBackground(new Background(0.5f, 0.5f, 0.5f, 0.5f));
	// Rectangle bg = new Rectangle(200, 320, 400, 640, ResourceManager
	// .getInstance().getVertexBufferObjectManager());
	// // bg.setColor(Color.BLACK_ABGR_PACKED_INT);
	// bg.setColor(Color.BLACK);
	// bg.setAlpha(0.5f);
	//
	// attachChild(bg);
	// attachPauseMenuBoard();
	// attachButtons();
	// }
}

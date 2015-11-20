package com.pike.games.menus;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.adt.color.Color;

import android.util.Log;

import com.pike.games.managers.ResourceManager;
import com.pike.games.managers.SceneManager;
import com.pike.games.managers.SceneManager.SceneType;
import com.pike.games.scenes.GameScene;

public class PauseMenu extends GameScene {

	private MenuScene pauseMenuScene;

	private final int BUTTON_PLAY = 0;
	private final int BUTTON_RELOAD = 1;
	private final int BUTTON_MENU = 2;

	private void attachPauseMenuBoard() {
		Sprite menuBoard = new Sprite(200, 320,
				mResourceManager.mPauseMenuBoardTR, mVboManager);
		this.attachChild(menuBoard);
	}

	private void attachButtons() {
		pauseMenuScene = new MenuScene(ResourceManager.getInstance()
				.getCamera());
		pauseMenuScene.setPosition(0, 230);

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

		pauseMenuScene.addMenuItem(playMenuItem);
		pauseMenuScene.addMenuItem(reloadMenuItem);
		pauseMenuScene.addMenuItem(menuMenuItem);

		pauseMenuScene.buildAnimations();
		pauseMenuScene.setBackgroundEnabled(false);

		playMenuItem.setPosition(95, 70);
		reloadMenuItem.setPosition(196, 70);
		menuMenuItem.setPosition(302, 70);

		pauseMenuScene
				.setOnMenuItemClickListener(new IOnMenuItemClickListener() {

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
							resetGame();
							return true;
						case BUTTON_MENU:
							Log.e("Pause Menu item clicked: ", "MENU");
							SceneManager.getInstance().loadMenuScene();
							return true;
						default:
							return false;
						}

					}
				});

		setChildScene(pauseMenuScene);

	}

	@Override
	public void createScene() {
		this.setBackgroundEnabled(false);
		// this.setBackground(new Background(0.5f, 0.5f, 0.5f, 0.5f));
		Rectangle bg = new Rectangle(200, 320, 400, 640, ResourceManager
				.getInstance().getVertexBufferObjectManager());
		// bg.setColor(Color.BLACK_ABGR_PACKED_INT);
		bg.setColor(Color.BLACK);
		bg.setAlpha(0.5f);

		attachChild(bg);
		attachPauseMenuBoard();
		attachButtons();
	}
}

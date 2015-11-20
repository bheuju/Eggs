package com.pike.games.menus;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.pike.games.eggs.GameActivity;
import com.pike.games.managers.ResourceManager;
import com.pike.games.managers.SceneManager;
import com.pike.games.managers.SceneManager.SceneType;
import com.pike.games.scenes.MainMenuScene;

public class QuitMenu extends MenuScene {

	protected ResourceManager mResourceManager;
	protected VertexBufferObjectManager mVboManager;
	protected Camera mCamera;
	protected GameActivity mActivity;

	public QuitMenu() {
		super(ResourceManager.getInstance().getCamera());

		this.mResourceManager = ResourceManager.getInstance();
		this.mVboManager = ResourceManager.getInstance()
				.getVertexBufferObjectManager();
		this.mCamera = ResourceManager.getInstance().getCamera();
		this.mActivity = ResourceManager.getInstance().getActivity();
		// createScene();

		attachPauseMenuBoard();
		attachButtons();
	}

	// private MenuScene quitMenuScene;

	private final int BUTTON_ACCEPT = 0;
	private final int BUTTON_REJECT = 1;

	private void attachPauseMenuBoard() {
		Sprite menuBoard = new Sprite(200, 320,
				mResourceManager.mQuitMenuBoardTR, mVboManager);
		this.attachChild(menuBoard);
	}

	private void attachButtons() {
		// quitMenuScene = new
		// MenuScene(ResourceManager.getInstance().getCamera());
		// quitMenuScene
		setPosition(0, 0);

		final IMenuItem acceptMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(BUTTON_ACCEPT,
						mResourceManager.mQuitMenuAcceptButtonTR, mVboManager),
				0.8f, 1f);
		final IMenuItem rejectMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(BUTTON_REJECT,
						mResourceManager.mQuitMenuRejectButtonTR, mVboManager),
				0.8f, 1f);

		// quitMenuScene.addMenuItem(acceptMenuItem);
		// quitMenuScene.addMenuItem(rejectMenuItem);
		addMenuItem(acceptMenuItem);
		addMenuItem(rejectMenuItem);

		// quitMenuScene.buildAnimations();
		// quitMenuScene.setBackgroundEnabled(false);
		buildAnimations();
		setBackgroundEnabled(false);

		acceptMenuItem.setPosition(231 + 20, 36 + 218);
		rejectMenuItem.setPosition(125 + 20, 36 + 218);

		// quitMenuScene.
		setOnMenuItemClickListener(new IOnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClicked(MenuScene pMenuScene,
					IMenuItem pMenuItem, float pMenuItemLocalX,
					float pMenuItemLocalY) {

				switch (pMenuItem.getID()) {
				case BUTTON_ACCEPT:
					Log.e("Quit Menu item clicked: ", "ACCEPT");
					System.exit(0);
					return true;
				case BUTTON_REJECT:
					Log.e("Quit Menu item clicked: ", "REJECT");
					back();

					// QuitMenu.this.mParentScene.setChildScene(pChildScene)

					return true;
				default:
					return false;
				}

			}
		});

		// setChildScene(quitMenuScene);

	}

	@Override
	public void back() {
		this.clearChildScene();

		((MainMenuScene) this.mParentScene).createMenuChildScene();

		// if (this.mParentScene != null) {
		// this.mParentScene.clearChildScene();
		// this.mParentScene = null;
		// }

		// setChildScene(menuChildScene);
		// createMenuChildScene();
	}

	// @Override
	// public void createScene() {
	// this.setBackgroundEnabled(false);
	// attachPauseMenuBoard();
	// attachButtons();
	// }
}

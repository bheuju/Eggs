package Scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import android.util.Log;

import com.pike.games.eggs.BaseScene;
import com.pike.games.managers.SceneManager;
import com.pike.games.managers.SceneManager.SceneType;

public class GameScene extends BaseScene {

	private HUD gameHUD;
	private Text lifeText;
	private int lifeCount = 3;

	private void createHUD() {
		gameHUD = new HUD();

		// create lifeCount text
		lifeText = new Text(20, 600, mResourceManager.mFont,
				"Score: 0123456789", mVboManager);
		lifeText.setAnchorCenter(0, 0);
		lifeText.setText("Life: 3");
		gameHUD.attachChild(lifeText);

		mCamera.setHUD(gameHUD);
	}

	private void setLifeCount(int life) {
		lifeText.setText("Score: " + lifeCount);
	}

	private void createBackGround() {
		setBackground(new Background(Color.CYAN));
	}

	@Override
	public void createScene() {
		createBackGround();
		createHUD();

	}

	@Override
	public void onBackKeyPressed() {
		Log.e("Back", "Game back pressed");
		SceneManager.getInstance().loadMenuScene();
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		mCamera.setHUD(null);

	}

}

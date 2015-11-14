package Scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import com.pike.games.eggs.BaseScene;
import com.pike.games.managers.SceneManager.SceneType;

public class GameScene extends BaseScene {

	private void createBackGround() {
		setBackground(new Background(Color.CYAN));
	}

	@Override
	public void createScene() {
		createBackGround();

	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}

}

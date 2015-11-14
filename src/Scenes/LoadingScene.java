package Scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import com.pike.games.eggs.BaseScene;
import com.pike.games.managers.SceneManager.SceneType;

public class LoadingScene extends BaseScene {

	@Override
	public void createScene() {
		this.setBackground(new Background(Color.WHITE));
		Text mText = new Text(200, 320, mResourceManager.mFont, "Loading...",
				mVboManager);
		this.attachChild(mText);
	}

	@Override
	public void onBackKeyPressed() {

	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() {

	}

}

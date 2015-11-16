package Scenes;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.DrawMode;
import org.andengine.entity.primitive.Mesh;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.extension.physics.box2d.util.triangulation.EarClippingTriangulator;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.pike.games.eggs.BaseScene;
import com.pike.games.managers.SceneManager;
import com.pike.games.managers.SceneManager.SceneType;

public class GameScene extends BaseScene {

	private HUD gameHUD;
	private Text lifeText;
	private int lifeCount = 3;

	private FixedStepPhysicsWorld mPhysicsWorld;

	private Body eggBody;
	private Body nestBody;

	private float nestX;
	private float nestY;

	private void createPhysics() {
		mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -17),
				false);
		this.registerUpdateHandler(mPhysicsWorld);

		final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.1f,
				0.3f);

		final Sprite nestSprite = new Sprite(200, 150,
				mResourceManager.mNestTR, mVboManager);
		final Sprite nestFrontSprite = new Sprite(200, 150,
				mResourceManager.mNestFrontTR, mVboManager);
		final Sprite eggSprite = new Sprite(200, 160, mResourceManager.mEggTR,
				mVboManager) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (getY() < 0) {
					Log.d("Game Condition", "GAME IS OVER :(");
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		eggBody = PhysicsFactory.createCircleBody(mPhysicsWorld, eggSprite,
				BodyType.DynamicBody, FIXTURE_DEF);
		eggBody.setUserData("egg");

		List<Vector2> nestBodyVertices = new ArrayList<Vector2>();

		nestBodyVertices.add(new Vector2(-18f, -29f));
		nestBodyVertices.add(new Vector2(-49f, -16f));
		nestBodyVertices.add(new Vector2(-39f, 11f));
		nestBodyVertices.add(new Vector2(-33f, 3f));
		nestBodyVertices.add(new Vector2(-31f, -11f));
		nestBodyVertices.add(new Vector2(0f, -19f));
		nestBodyVertices.add(new Vector2(31f, -11f));
		nestBodyVertices.add(new Vector2(33f, 3f));
		nestBodyVertices.add(new Vector2(39f, 11f));
		nestBodyVertices.add(new Vector2(49f, -16f));
		nestBodyVertices.add(new Vector2(18f, -29f));

		List<Vector2> nestBodyVerticesTriangulated = new EarClippingTriangulator()
				.computeTriangles(nestBodyVertices);

		float[] MeshTriangles = new float[nestBodyVerticesTriangulated.size() * 3];
		for (int i = 0; i < nestBodyVerticesTriangulated.size(); i++) {
			MeshTriangles[i * 3] = nestBodyVerticesTriangulated.get(i).x;
			MeshTriangles[i * 3 + 1] = nestBodyVerticesTriangulated.get(i).y;
			nestBodyVerticesTriangulated.get(i).mul(
					1 / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
		}

		// Creates custom mesh for nest
		// Mesh nestBodyMesh = new Mesh(200f, 150f, MeshTriangles,
		// nestBodyVerticesTriangulated.size(), DrawMode.TRIANGLES,
		// mVboManager);
		// nestBodyMesh.setColor(1f, 0f, 0f);

		nestBody = PhysicsFactory.createTrianglulatedBody(mPhysicsWorld,
				nestSprite, nestBodyVerticesTriangulated,
				BodyType.KinematicBody, FIXTURE_DEF);

		nestBody.setUserData("nest");

		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nestSprite,
				nestBody));
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(eggSprite,
				eggBody));

		nestX = 100f;
		nestY = 400f;

		Sprite nest2 = new Sprite(nestX, nestY, mResourceManager.mNestTR,
				mVboManager);
		attachChild(nest2);

		this.attachChild(nestSprite);
		this.attachChild(eggSprite);
		this.attachChild(nestFrontSprite);
		// this.attachChild(nestBodyMesh);

		this.setOnSceneTouchListener(new IOnSceneTouchListener() {

			@Override
			public boolean onSceneTouchEvent(Scene pScene,
					TouchEvent pSceneTouchEvent) {
				if (pSceneTouchEvent.isActionDown()) {
					jumpEgg();
				}
				return false;
			}
		});

		float nestRelativeMinX = -200f;
		float nestRelativeMaxX = 200f;
		final float nestVelocity = 3f;
		final float nestMinXWC = (nestSprite.getX() + nestRelativeMinX)
				/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		final float nestMaxXWC = (nestSprite.getX() + nestRelativeMaxX)
				/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		nestBody.setLinearVelocity(nestVelocity, 0f);

		this.registerUpdateHandler(new IUpdateHandler() {

			@Override
			public void onUpdate(float pSecondsElapsed) {
				if (nestBody.getWorldCenter().x > nestMaxXWC) {
					nestBody.setTransform(nestMaxXWC,
							nestBody.getWorldCenter().y, nestBody.getAngle());
					nestBody.setLinearVelocity(-nestVelocity, 0f);
				} else if (nestBody.getWorldCenter().x < nestMinXWC) {
					nestBody.setTransform(nestMinXWC,
							nestBody.getWorldCenter().y, nestBody.getAngle());
					nestBody.setLinearVelocity(nestVelocity, 0f);
				}

				// update nestFront as nest updates
				nestFrontSprite.setPosition(nestSprite.getX(),
						nestSprite.getY());
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub

			}

		});
	}

	private void jumpEgg() {
		mResourceManager.mSound.play();
		eggBody.setLinearVelocity(new Vector2(eggBody.getLinearVelocity().x, 15));
	}

	private void createHUD() {
		gameHUD = new HUD();

		// create lifeCount text
		lifeText = new Text(20, 600, mResourceManager.mFont,
				"Life: 0123456789", mVboManager);
		lifeText.setAnchorCenter(0, 0);
		lifeText.setText("Life: 3");
		gameHUD.attachChild(lifeText);

		mCamera.setHUD(gameHUD);
	}

	private void setLifeCount(int life) {
		lifeText.setText("Life: " + lifeCount);
	}

	private void createBackGround() {
		setBackground(new Background(Color.CYAN));
	}

	@Override
	public void createScene() {
		createBackGround();
		createHUD();
		createPhysics();
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

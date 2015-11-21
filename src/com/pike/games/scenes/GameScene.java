package com.pike.games.scenes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
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
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.color.Color;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.IEntityLoader;
import org.andengine.util.level.IEntityLoaderData;
import org.andengine.util.level.IEntityLoaderListener;
import org.andengine.util.level.ILevelLoaderResult;
import org.andengine.util.level.LevelLoader;
import org.andengine.util.level.LevelLoaderContentHandler;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.pike.games.eggs.BaseScene;
import com.pike.games.managers.SceneManager;
import com.pike.games.managers.SceneManager.SceneType;
import com.pike.games.menus.PauseMenu;

public class GameScene extends BaseScene {

	private HUD gameHUD;
	private Text lifeText;
	private int lifeCount = 3;

	private FixedStepPhysicsWorld mPhysicsWorld;

	private Body eggBody;
	// private Body nestBody;
	Sprite eggSprite;

	private boolean isEggJumping = false;

	// Level Loader tags
	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
	private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";
	private static final String TAG_ENTITY_ATTRIBUTE_SCROLL = "scroll";

	private void loadLevel(int levelID) {

		final List<Vector2> nestBodyVertices = new ArrayList<Vector2>();

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

		final List<Vector2> nestBodyVerticesTriangulated = new EarClippingTriangulator()
				.computeTriangles(nestBodyVertices);

		float[] MeshTriangles = new float[nestBodyVerticesTriangulated.size() * 3];
		for (int i = 0; i < nestBodyVerticesTriangulated.size(); i++) {
			// MeshTriangles[i * 3] = nestBodyVerticesTriangulated.get(i).x;
			// MeshTriangles[i * 3 + 1] = nestBodyVerticesTriangulated.get(i).y;
			nestBodyVerticesTriangulated.get(i).mul(
					1 / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
		}

		// scrolling properties
		final float nestRelativeMinX = -150f;
		final float nestRelativeMaxX = 150f;
		final float nestVelocity = 2f;

		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(mVboManager);
		final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.1f,
				0.3f);

		levelLoader
				.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(
						LevelConstants.TAG_LEVEL) {

					@Override
					public IEntity onLoadEntity(String pEntityName,
							IEntity pParent, Attributes pAttributes,
							SimpleLevelEntityLoaderData pEntityLoaderData)
							throws IOException {

						final int width = SAXUtils.getIntAttributeOrThrow(
								pAttributes,
								LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
						final int height = SAXUtils.getIntAttributeOrThrow(
								pAttributes,
								LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);

						mCamera.setBounds(0, 0, width, height);
						mCamera.setBoundsEnabled(true);

						return GameScene.this;
					}
				});

		Log.e("TAG", TAG_ENTITY);

		levelLoader
				.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(
						TAG_ENTITY) {

					@Override
					public IEntity onLoadEntity(String pEntityName,
							IEntity pParent, Attributes pAttributes,
							SimpleLevelEntityLoaderData pEntityLoaderData)
							throws IOException {

						final int x = SAXUtils.getIntAttributeOrThrow(
								pAttributes, TAG_ENTITY_ATTRIBUTE_X);
						final int y = SAXUtils.getIntAttributeOrThrow(
								pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
						final String type = SAXUtils.getAttributeOrThrow(
								pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);
						final boolean scroll = SAXUtils
								.getBooleanAttributeOrThrow(pAttributes,
										TAG_ENTITY_ATTRIBUTE_SCROLL);

						final Sprite levelObject;
						final Body levelObjectBody;
						final Sprite nestFrontSprite;

						if (type.equals("nest")) {
							levelObject = new Sprite(x, y,
									mResourceManager.mNestTR, mVboManager);
							levelObjectBody = PhysicsFactory
									.createTrianglulatedBody(mPhysicsWorld,
											levelObject,
											nestBodyVerticesTriangulated,
											BodyType.KinematicBody, FIXTURE_DEF);
							mPhysicsWorld
									.registerPhysicsConnector(new PhysicsConnector(
											levelObject, levelObjectBody));

							nestFrontSprite = new Sprite(x, y,
									mResourceManager.mNestFrontTR, mVboManager);
						} else {
							throw new IllegalArgumentException();
						}

						final float nestMinXWC = (levelObject.getX() + nestRelativeMinX)
								/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
						final float nestMaxXWC = (levelObject.getX() + nestRelativeMaxX)
								/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

						levelObjectBody.setLinearVelocity(nestVelocity, 0f);

						if (scroll) {

							levelObject
									.registerUpdateHandler(new IUpdateHandler() {

										@Override
										public void reset() {
											// TODO Auto-generated method stub

										}

										@Override
										public void onUpdate(
												float pSecondsElapsed) {

											if (levelObject.getX() > 350) {
												levelObjectBody
														.setLinearVelocity(
																-nestVelocity,
																0f);
											} else if (levelObject.getX() < 50) {
												levelObjectBody
														.setLinearVelocity(
																nestVelocity,
																0f);
											}

											nestFrontSprite.setPosition(
													levelObject.getX(),
													levelObject.getY());

										}
									});
						} else {
							levelObjectBody.setLinearVelocity(0f, 0f);
						}

						levelObject.setCullingEnabled(true);

						nestFrontSprite.setZIndex(3);
						GameScene.this.attachChild(nestFrontSprite);

						levelObject.setZIndex(1);

						return levelObject;
					}

				});
		levelLoader.loadLevelFromAsset(mActivity.getAssets(), "level/"
				+ levelID + ".lvl");

	}

	private void createPhysics() {
		mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -17),
				false);
		this.registerUpdateHandler(mPhysicsWorld);

		final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.1f,
				0.3f);

		// final Sprite nestSprite = new Sprite(200, 150,
		// mResourceManager.mNestTR, mVboManager);
		// final Sprite nestFrontSprite = new Sprite(200, 150,
		// mResourceManager.mNestFrontTR, mVboManager);
		eggSprite = new Sprite(200, 160, mResourceManager.mEggTR, mVboManager) {
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

		// nestBody = PhysicsFactory.createTrianglulatedBody(mPhysicsWorld,
		// nestSprite, nestBodyVerticesTriangulated,
		// BodyType.KinematicBody, FIXTURE_DEF);

		// nestBody.setUserData("nest");

		// mPhysicsWorld.registerPhysicsConnector(new
		// PhysicsConnector(nestSprite,
		// nestBody));
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(eggSprite,
				eggBody));

		// ZIndex
		eggSprite.setZIndex(2);

		// this.attachChild(nestSprite);
		this.attachChild(eggSprite);
		// this.attachChild(nestFrontSprite);
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
	}

	/* Applying moving platform to single nest */
	/*
	 * private void scrolling(final Sprite nestSprite, boolean scroll) { float
	 * nestRelativeMinX = -150f; float nestRelativeMaxX = 150f; final float
	 * nestVelocity;
	 * 
	 * final float nestMinXWC = (nestSprite.getX() + nestRelativeMinX) /
	 * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT; final float nestMaxXWC =
	 * (nestSprite.getX() + nestRelativeMaxX) /
	 * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	 * 
	 * if (scroll) { nestVelocity = 2f; } else { nestVelocity = 0f; }
	 * 
	 * nestBody.setLinearVelocity(nestVelocity, 0f);
	 * 
	 * this.registerUpdateHandler(new IUpdateHandler() {
	 * 
	 * @Override public void onUpdate(float pSecondsElapsed) { if
	 * (nestBody.getWorldCenter().x > nestMaxXWC) {
	 * nestBody.setTransform(nestMaxXWC, nestBody.getWorldCenter().y,
	 * nestBody.getAngle()); nestBody.setLinearVelocity(-nestVelocity, 0f); }
	 * else if (nestBody.getWorldCenter().x < nestMinXWC) {
	 * nestBody.setTransform(nestMinXWC, nestBody.getWorldCenter().y,
	 * nestBody.getAngle()); nestBody.setLinearVelocity(nestVelocity, 0f); }
	 * 
	 * // update nestFront as nest updates //
	 * nestFrontSprite.setPosition(nestSprite.getX(), // nestSprite.getY()); }
	 * 
	 * @Override public void reset() { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * }); }
	 */

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

	public void resetGame() {
		Log.e("RESET", "Warning Game LEVEL RESET");
	}

	private void createBackGround() {
		setBackground(new Background(Color.CYAN));
	}

	public void exitGameScene() {

		mCamera.setChaseEntity(null);
		mCamera.setCenter(200, 320);

		// Log.e("Camera", mCamera.getCenterX() + " : " + mCamera.getCenterY());

		SceneManager.getInstance().loadMenuScene();
	}

	@Override
	public void createScene() {
		createBackGround();
		createHUD();
		createPhysics();
		loadLevel(1);
		sortChildren();

		mCamera.setChaseEntity(eggSprite);
	}

	@Override
	public void onBackKeyPressed() {
		Log.e("Back", "Game back pressed");
		// SceneManager.getInstance().loadMenuScene();

		// attach pauseMenu scene
		// pauses game updates too due to additional parameters set
		Scene pauseMenu = new PauseMenu();
		setChildScene(pauseMenu, false, true, true);

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

package com.mygdx.marc;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class AnimationController extends ApplicationAdapter {

	Texture walkSheet,backGround;
	SpriteBatch spriteBatch;
	TextureRegion frames[] = new TextureRegion[6];
	TextureRegion walkFrame[] = new TextureRegion[6];
	private OrthographicCamera camera;
	TextureRegion leftWalkFrame[] = new TextureRegion[6];
	Animation<TextureRegion> mario, walkMario, leftWalkMario;
	float stateTime;
	SpriteBatch batch;
	float posx, posy;

	Rectangle up, down, left, right, fire;
	final int IDLE=0, UP=1, DOWN=2, LEFT=3, RIGHT=4;

	@Override
	public void create() {
		backGround = new Texture(Gdx.files.internal("backGround.png"));
		walkSheet = new Texture(Gdx.files.internal("mario-Animation.png"));
		posx = 750;
		posy = 450;

		frames[0] = new TextureRegion(walkSheet,0,0,25,29);
		frames[1] = new TextureRegion(walkSheet,31,0,25,29);
		frames[2] = new TextureRegion(walkSheet,64,0,25,29);
		frames[3] = new TextureRegion(walkSheet,95,0,25,29);
		frames[4] = new TextureRegion(walkSheet,127,0,25,29);
		frames[5] = new TextureRegion(walkSheet,159,0,25,29);

		walkFrame[0] = new TextureRegion(walkSheet,0,29,25,29);
		walkFrame[1] = new TextureRegion(walkSheet,31,29,25,29);
		walkFrame[2] = new TextureRegion(walkSheet,64,29,25,29);
		walkFrame[3] = new TextureRegion(walkSheet,95,29,25,29);
		walkFrame[4] = new TextureRegion(walkSheet,127,29,25,29);
		walkFrame[5] = new TextureRegion(walkSheet,159,29,25,29);

		leftWalkFrame[0] = new TextureRegion(walkSheet,0,59,25,29);
		leftWalkFrame[1] = new TextureRegion(walkSheet,31,59,25,29);
		leftWalkFrame[2] = new TextureRegion(walkSheet,64,59,25,29);
		leftWalkFrame[3] = new TextureRegion(walkSheet,95,59,25,29);
		leftWalkFrame[4] = new TextureRegion(walkSheet,127,59,25,29);
		leftWalkFrame[5] = new TextureRegion(walkSheet,159,59,25,29);

		mario = new Animation<TextureRegion>(0.55f,frames);
		walkMario = new Animation<TextureRegion>(0.55f,walkFrame);
		leftWalkMario = new Animation<TextureRegion>(0.55f,leftWalkFrame);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 2900, 1480);
		batch = new SpriteBatch();
		stateTime = 0f;

		up = new Rectangle(0, 1480*2/3, 2900, 1480/3);
		down = new Rectangle(0, 0, 2900, 1480/3);
		left = new Rectangle(0, 0, 2900/3,1480);
		right = new Rectangle(2900*2/3, 0, 2900/3, 1480);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();

		camera.update();

		TextureRegion frame = mario.getKeyFrame(stateTime,true);
		TextureRegion walkCurrentFrame = walkMario.getKeyFrame(stateTime,true);
		TextureRegion leftWalkCurrentFrame = leftWalkMario.getKeyFrame(stateTime,true);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(backGround, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();

		int direction = virtual_joystick_control();

		switch (direction){
			case 0:
				batch.begin();
				mario = new Animation<TextureRegion>(0.55f,frames);
				batch.draw(frame, posx, posy, 0, 0,
						frame.getRegionWidth(),frame.getRegionHeight(),10,10,0);
				batch.end();
				break;
			case 1:
				mario = new Animation<TextureRegion>(0.55f, walkFrame);
				batch.begin();
				posy += 25;
				batch.draw(walkCurrentFrame, posx, posy,0, 0,
						walkCurrentFrame.getRegionWidth(),walkCurrentFrame.getRegionHeight(),10,10,0);
				batch.end();
				break;
			case 2:
				mario = new Animation<TextureRegion>(0.55f, walkFrame);
				batch.begin();
				posy -= 25;
				batch.draw(walkCurrentFrame, posx, posy,0, 0,
						walkCurrentFrame.getRegionWidth(),walkCurrentFrame.getRegionHeight(),10,10,0);
				batch.end();
				break;
			case 3:
				mario = new Animation<TextureRegion>(0.55f, leftWalkFrame);
				batch.begin();
				posx -= 25;
				batch.draw(leftWalkCurrentFrame, posx, posy,0, 0,
						leftWalkCurrentFrame.getRegionWidth(),leftWalkCurrentFrame.getRegionHeight(),10,10,0);
				batch.end();
				break;
			case 4:
				mario = new Animation<TextureRegion>(0.55f, walkFrame);
				batch.begin();
				posx += 25;
				batch.draw(walkCurrentFrame, posx, posy, 0, 0,
						walkCurrentFrame.getRegionWidth(), walkCurrentFrame.getRegionHeight(), 10, 10, 0);
				batch.end();
				break;
		}
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		walkSheet.dispose();
		backGround.dispose();
	}

	protected int virtual_joystick_control() {
		for(int i=0;i<10;i++)
			if (Gdx.input.isTouched(i)) {
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				camera.unproject(touchPos);
				if (up.contains(touchPos.x, touchPos.y)) {
					return UP;
				} else if (down.contains(touchPos.x, touchPos.y)) {
					return DOWN;
				} else if (left.contains(touchPos.x, touchPos.y)) {
					return LEFT;
				} else if (right.contains(touchPos.x, touchPos.y)) {
					return RIGHT;
				}
			}
		return IDLE;
	}
}

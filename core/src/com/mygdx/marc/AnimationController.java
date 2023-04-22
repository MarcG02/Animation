package com.mygdx.marc;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationController extends ApplicationAdapter {

	Texture walkSheet,backGround;
	SpriteBatch spriteBatch;
	TextureRegion frames[] = new TextureRegion[6];
	TextureRegion walkFrame[] = new TextureRegion[6];
	TextureRegion leftWalkFrame[] = new TextureRegion[6];
	Animation<TextureRegion> mario, walkMario, leftWalkMario;
	float stateTime;
	SpriteBatch batch;
	float posx, posy;

	@Override
	public void create() {
		backGround = new Texture(Gdx.files.internal("backGround.png"));
		backGround.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
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

		mario = new Animation<TextureRegion>(0.23f,frames);
		walkMario = new Animation<TextureRegion>(0.23f,walkFrame);
		leftWalkMario = new Animation<TextureRegion>(0.23f,leftWalkFrame);
		batch = new SpriteBatch();
		stateTime = 0f;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();

		TextureRegion frame = mario.getKeyFrame(stateTime,true);
		TextureRegion walkCurrentFrame = walkMario.getKeyFrame(stateTime,true);
		TextureRegion leftWalkCurrentFrame = leftWalkMario.getKeyFrame(stateTime,true);


		batch.begin();
		batch.draw(backGround, 0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			mario = new Animation<TextureRegion>(0.023f, walkFrame);
			batch.begin();
			posx += 25;
			batch.draw(walkCurrentFrame, posx, posy,0, 0,
					walkCurrentFrame.getRegionWidth(),walkCurrentFrame.getRegionHeight(),10,10,0);
			batch.end();
		}else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			mario = new Animation<TextureRegion>(0.023f, leftWalkFrame);
			batch.begin();
			posx -= 25;
			batch.draw(leftWalkCurrentFrame, posx, posy,0, 0,
					leftWalkCurrentFrame.getRegionWidth(),leftWalkCurrentFrame.getRegionHeight(),10,10,0);
			batch.end();
		}else{
			batch.begin();
			mario = new Animation<TextureRegion>(0.23f,frames);
			batch.draw(frame, posx, posy, 0, 0,
					frame.getRegionWidth(),frame.getRegionHeight(),10,10,0);
			batch.end();
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
}

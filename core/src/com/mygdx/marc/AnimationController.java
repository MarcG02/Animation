package com.mygdx.marc;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationController implements ApplicationListener {

	Texture walkSheet;
	SpriteBatch spriteBatch;
	TextureRegion frames[] = new TextureRegion[12];
	//TextureRegion walkFrame[] = new TextureRegion[7];
	Animation<TextureRegion> mario, walkMario;
	float stateTime;
	SpriteBatch batch;

	@Override
	public void create() {
		walkSheet = new Texture(Gdx.files.internal("mario-Animation.png"));

		frames[0] = new TextureRegion(walkSheet,0,0,25,29);
		frames[1] = new TextureRegion(walkSheet,33,0,25,29);
		frames[2] = new TextureRegion(walkSheet,65,0,25,29);
		frames[3] = new TextureRegion(walkSheet,97,0,25,29);
		frames[4] = new TextureRegion(walkSheet,128,0,25,29);
		frames[5] = new TextureRegion(walkSheet,160,0,25,29);

		frames[6] = new TextureRegion(walkSheet,0,29,25,29);
		frames[7] = new TextureRegion(walkSheet,31,29,25,29);
		frames[8] = new TextureRegion(walkSheet,64,29,25,29);
		frames[9] = new TextureRegion(walkSheet,95,29,25,29);
		frames[10] = new TextureRegion(walkSheet,127,29,25,29);
		frames[11] = new TextureRegion(walkSheet,159,29,25,29);

		mario = new Animation<TextureRegion>(0.23f,frames);
		//walkMario = new Animation<TextureRegion>(0.29f,walkFrame);
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

		batch.begin();
		batch.draw(frame, 200, 100, 0, 0,
				frame.getRegionWidth(),frame.getRegionHeight(),10,10,0);

		/*TextureRegion walkFrame = walkMario.getKeyFrame(stateTime,true);

		batch.draw(walkFrame, 200, 100, 0, 0,
				walkFrame.getRegionWidth(),walkFrame.getRegionHeight(),3,3,0);*/
		batch.end();
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
	}
}

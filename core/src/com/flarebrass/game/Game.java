package com.flarebrass.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flarebrass.game.Scenes.Hud;
import com.flarebrass.game.states.GameStateManager;
import com.flarebrass.game.states.MenuState;
public class Game extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE ="Ultimate Jump";
	private GameStateManager gsm;
	private SpriteBatch batch;
	private Music music;
	private Hud hud;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		music =Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
		hud = new Hud(batch);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
	}

	@Override
	public void dispose () {
		batch.dispose();
		music.dispose();
	}
}
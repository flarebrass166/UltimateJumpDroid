package com.flarebrass.game.Scenes;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flarebrass.game.Game;
import com.flarebrass.game.states.PlayState;

public class Hud {
    public Stage stage;
    private Viewport viewport;
    Label scoreLabel;

    public Hud(SpriteBatch sb){
        viewport = new FitViewport(Game.WIDTH, Game.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        scoreLabel = new Label(String.format("%03d", PlayState.getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(scoreLabel).expandX().padTop(10);
        stage.addActor(table);
    }
    public static void updateHud(){
        PlayState.setScore(PlayState.getScore());
    }
}

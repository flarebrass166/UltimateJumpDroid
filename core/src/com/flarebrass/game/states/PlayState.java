package com.flarebrass.game.states;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.flarebrass.game.Game;
import com.flarebrass.game.Scenes.Hud;
import com.flarebrass.game.Sprites.Guy;
import com.flarebrass.game.Sprites.Pipe;

public class PlayState extends State {
    private static final int PIPE_SPACING = 125;
    private static final int PIPE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;
    public static int score;

    private Guy guy;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Array<Pipe> pipes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        guy = new Guy(50,300);
        cam.setToOrtho(false, Game.WIDTH / 2, Game.HEIGHT / 2);
        bg = new Texture("background.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
        pipes = new Array<Pipe>();
        score = 0;

        for (int i = 1; i <= PIPE_COUNT; i++){
            pipes.add(new Pipe(i * (PIPE_SPACING + Pipe.PIPE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            guy.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        Hud.updateHud();
        guy.update(dt);
        cam.position.x = guy.getPosition().x + 80;

        for(int i = 0; i < pipes.size; i++) {
            Pipe pipe = pipes.get(i);

            if (pipe.getPoint(guy.getBounds())){
                System.out.println("Point");
                score++;
            }
            if(cam.position.x - (cam.viewportWidth / 2) > pipe.getPosTopPipe().x + pipe.getTopPipe().getWidth()){
                pipe.rePos(pipe.getPosTopPipe().x + ((Pipe.PIPE_WIDTH + PIPE_SPACING) * PIPE_COUNT ));
            }
            if(pipe.collides(guy.getBounds())){
                gsm.set(new PlayState(gsm));
            }

        }
        if(guy.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
            gsm.set(new PlayState(gsm));
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(guy.getTexture(), guy.getPosition().x, guy.getPosition().y);
        for (Pipe pipe: pipes) {
            sb.draw(pipe.getTopPipe(), pipe.getPosTopPipe().x, pipe.getPosTopPipe().y);
            sb.draw(pipe.getBottomPipe(), pipe.getPosBotPipe().x, pipe.getPosBotPipe().y);
        }
        //sb.draw(score, guy.getPosition().x / 3, guy.getPosition().y * 2);
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        guy.dispose();
        ground.dispose();
        for(Pipe pipe: pipes){
            pipe.dispose();
        }
        System.out.println("Play State Disposed");
    }

    private void updateGround(){
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x -(cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
    public static int getScore() {
        return score;
    }
    public static void setScore(int s){
        score = s;
    }
}

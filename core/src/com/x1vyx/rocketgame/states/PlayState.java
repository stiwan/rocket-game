package com.x1vyx.rocketgame.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.background.Background;
import com.x1vyx.rocketgame.background.Bird;
import com.x1vyx.rocketgame.background.Cloud;
import com.x1vyx.rocketgame.background.Halloween;
import com.x1vyx.rocketgame.background.Star;
import com.x1vyx.rocketgame.objects.Earth;
import com.x1vyx.rocketgame.objects.Ship;
import com.x1vyx.rocketgame.objects.items.Coin;
import com.x1vyx.rocketgame.objects.items.Paint;
import com.x1vyx.rocketgame.objects.items.Torpedo;
import com.x1vyx.rocketgame.tools.Control;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.SoundControl;
import com.x1vyx.rocketgame.ui.DistanceManager;
import com.x1vyx.rocketgame.ui.Mushroom;
import com.x1vyx.rocketgame.ui.Score;
import com.x1vyx.rocketgame.ui.ScoreBoard;
import com.x1vyx.rocketgame.ui.Speed;

import java.awt.HeadlessException;
import java.util.ArrayList;

public class PlayState extends State
{

    // Objects
    private Ship ship;
    private Background b;
    private Control control;
    private Coin coin;
    private Score score;
    private DistanceManager dm;
    private Paint paint;
    private Bird bird;
    private Cloud cloud;
    private Halloween halloween;
    private Star star;
    private Earth earth;
    private Torpedo torpedo;
    private ScoreBoard scoreBoard;

    // TODO: More Birds!

    PlayState(GameStateManager gsm, MenuState ms)
    {
        super(gsm);
        ms.disposeForSwitch();

        // Init
        b = ms.b;
        control = ms.control;
        star = new Star();
        earth = new Earth();
        coin = ms.coin;
        ship = ms.ship;
        paint = ms.paint;
        score = new Score();
        dm = new DistanceManager();
        // TODO birds in menu
        bird = new Bird();
        cloud = new Cloud();
        halloween = new Halloween();
        torpedo = ms.t;
        scoreBoard = new ScoreBoard();
        // Sound
        if (!RocketGame.muted)
            loadSounds();

        // Config
        control.setRS(Control.RS_GAME);
        ship.setDemo(false);
        ship.setSpeed(new Speed());
        torpedo.setDemo(false);

        // Add
        objects = new ArrayList<GameObject>();
        objects.add(star);
        objects.add(earth);
        objects.add(coin);
        objects.add(ship);
        objects.add(paint);
        objects.add(bird);
        objects.add(cloud);
        objects.add(halloween);
        objects.add(torpedo);
        objects.add(scoreBoard);

    }

    private void loadSounds() // TODO load in menu?
    {

    }

    public void updateInput()
    {
        int lastDirection = direction;
        direction = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            direction = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            direction = 2;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.R))
            direction = 3;

        if (Gdx.input.isTouched())
        {
            touchX[0] = Gdx.input.getX(0) / resolutionFactor;
            touchY[0] = 240 - Gdx.input.getY(0) / resolutionFactor; // bug
            touchX[1] = Gdx.input.getX(1) / resolutionFactor;
            touchY[1] = 240 - Gdx.input.getY(1) / resolutionFactor;
        }
        // Restart
        if (Ship.isDead() && direction == 5)
        {

        }

        // Switch mute (removed)
        // Switch boost
        if (direction == 7 && lastDirection != 7)
            RocketGame.keepBoost = !RocketGame.keepBoost;
        if (direction == 3 && lastDirection != 3 && RocketGame.keepBoost)
            ship.switchBoost();

    }

    private void updateCollision() // And draw plus points
    { // TODO: optimize


    }

    private void restart()
    {
        ship.reset();
        score.reset();
        star.reset();
        earth.reset();
        scoreBoard.reset();
        dm.reset();
        cloud.reset();
        halloween.reset();
        paint.reset();
        torpedo.reset();
        control.setRS(1);
        b.switchColor(true);
        coin.reset();
        bird.reset();
        SoundControl.play(SoundControl.BTN);
    }

    @Override
    public void update(float dt)
    {
        control.update(dt);

        if (Ship.isDead() && Control.restart)
        {
            restart();
        }

        b.update(dt);

        for (int i = 0; i < objects.size(); i++)
            if (!objects.get(i).update(dt))
            {
                objects.get(i).dispose();
                objects.remove(i);
                i--;
            }

        /* Collision */
        if (coin.getSprite().y < RocketGame.getHeight() / 3f)
            if (ship.checkCoinCollision(coin))
            {
                score.add();
                objects.add(new Mushroom(1, ship.getX() + 15, ship.getY() + 30));
                SoundControl.play(SoundControl.COIN);
            }
        if (paint.getSprite().y < RocketGame.getHeight() / 3f)
            if (ship.checkPaintCollision(paint))
            {
                objects.add(new Mushroom(3, ship.getX() + 15, ship.getY() + 30));
                score.add(3);
                b.switchColor(true);
                SoundControl.play(SoundControl.PAINT);
            }
        if (torpedo.getSprite().y < RocketGame.getHeight() / 3f)
            if (ship.checkTorpedoCollision(torpedo))
            {
                score.die();
                scoreBoard.activate(score.getValue());
                ship.die();
                control.setRS(2);
                SoundControl.play(SoundControl.DEATH);
            }

        score.update(dt);
        dm.update(dt);
        if (timer > 960)
            timer = 0;
        else
            timer++;

    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(cam.combined);
        /* Background */
        b.render(sb);
        for (GameObject elem : objects)
            elem.render(sb);

        /* UI / Buttons */

        control.render(sb);
        score.render(sb);
        dm.render(sb);
//          Sprite.renderText(sb, "FPS " + Gdx.graphics.getFramesPerSecond(), 0, Sandbox.HEIGHT - 7*2);
//          colorTestRects.render(sb);
    }

    @Override
    public void dispose()
    {
        b.dispose();
        for (GameObject elem : objects)
            elem.dispose();

        score.dispose();
        control.dispose();
        SoundControl.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("PLAY STATE DISPOSED");
    }
}

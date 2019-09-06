package com.x1vyx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.game.background.Background;
import com.x1vyx.game.background.Bird;
import com.x1vyx.game.background.Cloud;
import com.x1vyx.game.background.Star;
import com.x1vyx.game.objects.Earth;
import com.x1vyx.game.objects.Ship;
import com.x1vyx.game.objects.items.CoinGenerator;
import com.x1vyx.game.objects.items.Paint;
import com.x1vyx.game.objects.items.Torpedo;
import com.x1vyx.game.tools.Control;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.RocketGame;
import com.x1vyx.game.ui.DistanceManager;
import com.x1vyx.game.ui.Mushroom;
import com.x1vyx.game.ui.Score;
import com.x1vyx.game.ui.ScoreBoard;
import com.x1vyx.game.ui.Speed;

import java.util.ArrayList;

public class PlayState extends State
{

    // Objects
    private Ship ship;
    private Background b;
    private Control control;
    private CoinGenerator cg;
    private Score score;
    private DistanceManager dm;
    private Paint paint;
    private Bird bird;
    private Cloud cloud;
    private Star star;
    private Earth earth;
    private Torpedo torpedo;
    private ScoreBoard scoreBoard;

    private Sound sndCoin;
    private Sound sndBtn;
    private Sound sndDeath;
    private Sound sndPaint;

    private boolean soundsLoaded;

    // TODO: More Birds!

    PlayState(GameStateManager gsm, MenuState ms)
    {
        super(gsm);

        // Init
        b = ms.b;
        control = ms.control;
        star = new Star();
        earth = new Earth();
        cg = ms.cg;
        ship = ms.ship;
        paint = ms.paint;
        score = new Score();
        dm = new DistanceManager();
        // TODO birds in menu
        bird = new Bird();
        cloud = new Cloud();
        torpedo = ms.t;
        scoreBoard = new ScoreBoard();
        // Sound
        if (!RocketGame.muted)
            loadSounds();

        // Config
        control.setRenderState(1);
        ship.setDemo(false);
        ship.setSpeed(new Speed());
        torpedo.setDemo(false);

        // Add
        objects = new ArrayList<GameObject>();
        objects.add(star);
        objects.add(earth);
        objects.add(cg);
        objects.add(ship);
        objects.add(paint);
        objects.add(bird);
        objects.add(cloud);
        objects.add(torpedo);
        objects.add(scoreBoard);

    }

    private void pullOut()
    {
        for (GameObject go : objects)
            switch (go.type)
            {
                case 1: // ship
                    break;
                case 2: // bg
                    break;
                case 3: // cg
                    break;
                case 4: // paint
                    break;
                case 5: // torpedo

                    break;
            }
    }

    public static int getDirection()
    {
        return direction;
    }

    private void loadSounds() // TODO load in menu
    {
        soundsLoaded = true;
        sndCoin = Gdx.audio.newSound(Gdx.files.internal("wav/sndCoin.wav"));
        sndPaint = Gdx.audio.newSound(Gdx.files.internal("wav/sndPaint.wav"));
        sndDeath = Gdx.audio.newSound(Gdx.files.internal("wav/sndDeath.wav"));
        sndBtn = Gdx.audio.newSound(Gdx.files.internal("wav/sndBtn.wav"));
    }

    @Override
    public void updateInput()
    {
        control.updateAnimation();
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
            touchY[0] = 240 - Gdx.input.getY(0) / resolutionFactor;
            touchX[1] = Gdx.input.getX(1) / resolutionFactor;
            touchY[1] = 240 - Gdx.input.getY(1) / resolutionFactor;

            direction = control.check(touchX[0], touchY[0], touchX[1], touchY[1]);
        } else
            control.unpress();

        // Restart
        if (Ship.isDead() && direction == 5)
        {
            ship.reset();
            score.reset();
            star.reset();
            earth.reset();
            scoreBoard.reset();
            dm.reset();
            cloud.reset();
            paint.reset();
            torpedo.reset();
            control.setRenderState(1);
            b.switchColor(true);
            cg.reset();
            bird.reset();
            if (!RocketGame.muted && soundsLoaded)
                sndBtn.play();
        }

        // Switch mute
        if (direction == 6 && lastDirection != 6)
        {
            RocketGame.muted = !RocketGame.muted;
            if(!soundsLoaded)
                loadSounds();
        }
        // Switch boost
        if (direction == 7 && lastDirection != 7)
            RocketGame.keepBoost = !RocketGame.keepBoost;
        if(direction == 3 && lastDirection != 3 && RocketGame.keepBoost)
            ship.switchBoost();

    }

    private void updateCollision() // And draw plus points
    {
        if (ship.checkCoinCollision(cg.getCoins()))
        {
            score.add();
            objects.add(new Mushroom(1, ship.getX() + 15, ship.getY() + 30));
            if (!RocketGame.muted && soundsLoaded)
                sndCoin.play();
        }
        if (ship.checkPaintCollision(paint))
        {
            objects.add(new Mushroom(3, ship.getX() + 15, ship.getY() + 30));
            score.add(3);
            b.switchColor(true);
            if (!RocketGame.muted && soundsLoaded)
                sndPaint.play();
        }

        // Die
        if (ship.checkTorpedoCollision(torpedo) && !score.isDead())
        {
            score.die();
            scoreBoard.activate(score.getValue());
            ship.die();
            control.setRenderState(2);
            if (!RocketGame.muted && soundsLoaded)
                sndDeath.play();
        }

    }

    @Override
    public void update(float dt)
    {
        updateInput();
        b.update(dt);
        for (int i = 0; i < objects.size(); i++)
            if (!objects.get(i).update(dt))
            {
                objects.get(i).dispose();
                objects.remove(i);
                i--;
            }


        updateCollision();
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

        if (soundsLoaded)
        {
            sndCoin.dispose();
            sndBtn.dispose();
            sndDeath.dispose();
            sndPaint.dispose();
        }
        System.out.println("PLAY STATE DISPOSED");
    }

    @Override
    public void disposeForSwitch() { }
}

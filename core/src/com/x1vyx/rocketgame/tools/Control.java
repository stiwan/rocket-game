package com.x1vyx.rocketgame.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.objects.Ship;
import com.x1vyx.rocketgame.states.State;

public class Control extends GameObject
{
    /* DEV */
    private Field[] fields;
    private Sprite btnL, btnR, btnAction, btnStart, btnRestart, btnMute, btnKeepboost;

    /* Animated Buttons */
    private static int renderState;
    // Restart btn
    private QFunction vRestart;

    private boolean disposed;

    // Render State:
    // 0 -> menu (L, R, Start)
    // 1 -> game (L, R, Action)
    // 2 -> died (Restart)
    public static final int RS_MENU = 0, RS_GAME = 1, RS_DIED = 2;

    // Max 3 buttons on screen

    // standard buttons:
    // w = 30
    // h = 20

    // reset button:
    // w = 74
    // h = 25

    //* TEST *//
    public static boolean left, right, action, start, restart, mute, kb, touched[]; // key status
    public int x1, y1, x2, y2; // touch points
    private boolean touchRelocated;

    public Control()
    {
        btnL = new Sprite("btn-l.png", 2,10, 10);
        btnR = new Sprite("btn-r.png", 2,80, 10);
        btnAction = new Sprite("btn-action.png", 2,45, 10);
        btnStart = new Sprite("btn-start.png", 2,45, 10);
        btnRestart = new Sprite("btn-restart.png", 1, (RocketGame.getWidth() / 2f - 74 / 2f), 0f);
        btnMute = new Sprite("btn-sound.png", 2,10, RocketGame.getHeight() - 30);
        btnKeepboost = new Sprite("btn-keepboost.png", 2,80, RocketGame.getHeight() - 30);
        vRestart = new QFunction(RocketGame.isWide ? 6f : 5.5f); // 7.5f S// Technically the y-position of btn-restart

        /* DEV */
        fields = new Field[6];
        // Left btm
        fields[0] = new Field(0, 0, RocketGame.getWidth() / 3, RocketGame.getHeight() / 2);
        // Middle small btm / Play / Boost
        fields[1] = new Field(RocketGame.getWidth() / 3, 0, RocketGame.getWidth() / 3, RocketGame.getHeight() / 2);
        // Right btm
        fields[2] = new Field((int) (RocketGame.getWidth() / 1.5), 0, RocketGame.getWidth() / 3, RocketGame.getHeight() / 2);
        // Middle big btm / Restart
        fields[3] = new Field((int) btnRestart.x, 0, btnRestart.w, RocketGame.getHeight() / 2);
        // Left upper
        fields[4] = new Field(0, RocketGame.getHeight() / 2, RocketGame.getWidth() / 2, RocketGame.getHeight() / 2);
        // Right upper
        fields[5] = new Field(RocketGame.getWidth() / 2, RocketGame.getHeight() / 2, RocketGame.getWidth() / 2, RocketGame.getHeight() / 2);

        touched = new boolean[2];
    }

    public void setRS(int rs)
    {
        if (rs == RS_DIED)
        {
            vRestart.reset();
            btnRestart.y = -RocketGame.getHeight(); // Delay
        } else
        {
            restart = false;
        }
        renderState = rs;
    }

    @Override
    public boolean update(float dt) // TODO: optimize even more
    {
        x1 = -1;
        y1 = -1;
        x2 = -1;
        y2 = -1;
        // get touch points
        touched[0] = Gdx.input.isTouched() || RocketGame.DEV_MODE;
        touched[1] = Gdx.input.isTouched(1) || RocketGame.DEV_MODE;

        if (touched[0])
        {
            touchRelocated = true;
            x1 = Gdx.input.getX(0) / State.resolutionFactor;
            y1 = RocketGame.getHeight() - Gdx.input.getY(0) / State.resolutionFactor;
            if (touched[1])
            {
                x2 = Gdx.input.getX(1) / State.resolutionFactor;
                y2 = RocketGame.getHeight() - Gdx.input.getY(1) / State.resolutionFactor;
            }
        } else
        {
            if(!touchRelocated)
                return true;
            touchRelocated = false;
        }

        // TODO: optimize
        boolean lastWasMute = mute;
        boolean lastWasKB = kb;
        boolean lastWasAction = action;
        boolean lastWasRight = right;
        boolean lastWasLeft = left;

        // update button press status
        if (renderState == RS_GAME)
        {
            left = btnL.pressed = btnL.containsTouch(x1, y1) || Gdx.input.isKeyPressed(Input.Keys.A);
            if(left && btnR.containsTouch(x2, y2))
            {
                left = btnL.pressed = false;
                right = btnR.pressed = true;
            } else
                right = btnR.pressed = btnR.containsTouch(x1, y1) || Gdx.input.isKeyPressed(Input.Keys.D);
            if(right && btnL.containsTouch(x2, y2))
            {
                right = btnR.pressed = false;
                left = btnL.pressed = true;
            }

            action = btnAction.pressed = btnAction.containsTouch(x1, y1) || btnAction.containsTouch(x2, y2) || Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.SPACE);
            if (action && !lastWasAction)
            {
                RocketGame.boosting = !RocketGame.boosting;
            }
        }

        if (renderState == RS_DIED)
        {
            mute = btnMute.pressed = btnMute.containsTouch(x1, y1) || btnMute.containsTouch(x2, y2);
            kb = btnKeepboost.pressed = btnKeepboost.containsTouch(x1, y1) || btnKeepboost.containsTouch(x2, y2);
            restart = btnRestart.containsTouch(x1, y1) || btnRestart.containsTouch(x2, y2) || Gdx.input.isKeyPressed(Input.Keys.SPACE);

            // Set mute and KB
            if (mute && !lastWasMute)
            {
                RocketGame.muted = !RocketGame.muted;
                SoundControl.play(SoundControl.BTN);
            }
            if (kb && !lastWasKB)
            {
                RocketGame.keepBoost = !RocketGame.keepBoost;
                SoundControl.play(SoundControl.BTN);
            }
        }

        if (renderState == RS_MENU)
        {
            mute = btnMute.pressed = btnMute.containsTouch(x1, y1) || btnMute.containsTouch(x2, y2);
            kb = btnKeepboost.pressed = btnKeepboost.containsTouch(x1, y1) || btnKeepboost.containsTouch(x2, y2);
            start = btnStart.pressed = btnStart.containsTouch(x1, y1) || btnStart.containsTouch(x2, y2) || Gdx.input.isKeyPressed(Input.Keys.SPACE);
            left = btnL.pressed = btnL.containsTouch(x1, y1) || btnL.containsTouch(x2, y2) || Gdx.input.isKeyPressed(Input.Keys.A);
            right = btnR.pressed = btnR.containsTouch(x1, y1) || btnR.containsTouch(x2, y2) || Gdx.input.isKeyPressed(Input.Keys.D);

            // Set mute and KB
            if (mute && !lastWasMute)
            {
                RocketGame.muted = !RocketGame.muted;
                SoundControl.play(SoundControl.BTN);
            }
            if (kb && !lastWasKB)
            {
                RocketGame.keepBoost = !RocketGame.keepBoost;
                SoundControl.play(SoundControl.BTN);
            }
            if(right && !lastWasRight)
            {
                SoundControl.play(SoundControl.BTN);
            }
            if(left && !lastWasLeft)
            {
                SoundControl.play(SoundControl.BTN);
            }
        }

        return true;
    }

    public void render(SpriteBatch sb)
    {
        switch (renderState)
        {
            case RS_MENU:
                btnL.renderAsButton(sb);
                btnStart.renderAsButton(sb);
                btnR.renderAsButton(sb);
                // Sound btn
                btnMute.renderAsButton(sb, RocketGame.muted);
                // Keep boost btn
                btnKeepboost.renderAsButton(sb, RocketGame.keepBoost);
                break;
            case RS_GAME:
                btnL.renderAsButton(sb);
                btnR.renderAsButton(sb);
                btnAction.renderAsButton(sb, RocketGame.boosting || Ship.getHoldBoost());
                break;
            case RS_DIED:
                if (vRestart.isSwitching)
                    btnRestart.y += vRestart.getNextV();
                btnRestart.render(sb);
                // Sound btn
                btnMute.renderAsButton(sb, RocketGame.muted);
                // Keep boost btn
                btnKeepboost.renderAsButton(sb, RocketGame.keepBoost);
        }
    }

    public void dispose()
    {
        if (disposed)
        {
            btnL.dispose();
            btnR.dispose();
            btnAction.dispose();
            btnStart.dispose();
            btnRestart.dispose();
            btnMute.dispose();
            btnKeepboost.dispose();
            System.out.println("CONTROL DISPOSED");
            disposed = true;
        }
    }

    public static int getRenderState()
    {
        return renderState;
    }
}

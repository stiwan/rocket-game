package com.x1vyx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.objects.Ship;
import com.x1vyx.game.states.PlayState;

public class Control
{
    /* DEV */
    private final boolean EXTENDED_BTNS = true; // 100% screen height
    public boolean once;
    private Field[] fields;
    private Sprite btnL, btnR, btnAction, btnStart, btnRestart, btnMute, btnKeepboost;

    /* Animated Buttons */
    private int renderState;
    // Restart btn
    private Vector2 positionBtnRestart;
    private QFunction vRestart;
    private boolean btnRestartAnimation;

    private boolean disposed;

    // Render State:
    // 0 -> menu (L, R, Start)
    // 1 -> game (L, R, Action)
    // 2 -> died (Restart)


    // Max 3 buttons on screen

    // standard buttons:
    // w = 30
    // h = 20

    // reset button:
    // w = 74
    // h = 25

    public Control()
    {
        btnL = new Sprite("btn-l.png", 2, new Vector2(10, 10));
        btnR = new Sprite("btn-r.png", 2, new Vector2(80, 10));
        btnAction = new Sprite("btn-action.png", 2, new Vector2(45, 10));
        btnStart = new Sprite("btn-start.png", 2, new Vector2(45, 10));
        btnRestart = new Sprite("btn-restart.png", 2, positionBtnRestart = new Vector2(RocketGame.WIDTH / 2 - 74 / 2, -25));
        btnMute = new Sprite("btn-sound.png", 2, new Vector2(10, 210));
        btnKeepboost = new Sprite("btn-keepboost.png", 2, new Vector2(80, 210));
        vRestart = new QFunction(7.5f); // Technically the y-position of btn-restart

        /* DEV */
        fields = new Field[6];
        // Left btm
        fields[0] = new Field(0, 0, RocketGame.WIDTH / 3, RocketGame.HEIGHT / 2);
        // Middle small btm / Play / Boost
        fields[1] = new Field(RocketGame.WIDTH / 3, 0, RocketGame.WIDTH / 3, RocketGame.HEIGHT / 2);
        // Right btm
        fields[2] = new Field((int) (RocketGame.WIDTH / 1.5), 0, RocketGame.WIDTH / 3, RocketGame.HEIGHT / 2);
        // Middle big btm / Restart
        fields[3] = new Field((int) btnRestart.getPosition().x, 0, btnRestart.w, RocketGame.HEIGHT / 2);
        // Left upper
        fields[4] = new Field(0, RocketGame.HEIGHT / 2, RocketGame.WIDTH / 2, RocketGame.HEIGHT / 2);
        // Right upper
        fields[5] = new Field(RocketGame.WIDTH / 2, RocketGame.HEIGHT / 2, RocketGame.WIDTH / 2, RocketGame.HEIGHT / 2);
    }

    // Animated Buttons
    public void updateAnimation()
    {
        if (btnRestartAnimation)
            positionBtnRestart.y += vRestart.getNextV();
    }

    // 0 -> nothing
    // 1 -> left
    // 2 -> right
    // 3 -> boost
    // 4 -> start
    // 5 -> restart
    // 6 -> mute
    // 7 -> switch boost
    public int check(int x1, int y1, int x2, int y2) // Touch display
    {
        once = false;
        if (!Gdx.input.isTouched(1))
        { // first touch
            if (!EXTENDED_BTNS)
            {
                if (btnAction.containsTouch(x1, y1))
                    return 3;
                if (btnL.containsTouch(x1, y1))
                    return 1;
                if (btnR.containsTouch(x1, y1))
                    return 2;
                if (btnRestart.containsTouch(x1, y1) && !vRestart.isSwitching)
                    return 5;
            } else // bigger touch field
            {
                if (fields[1].contains(x1, y1) && renderState != 2) // Middle small
                {
                    btnAction.setFrame(1);
                    return 3;
                }
                if (fields[0].contains(x1, y1) && renderState != 2) // Left
                {
                    btnL.setFrame(1);
                    return 1;
                }
                if (fields[2].contains(x1, y1) && renderState != 2) // Right
                {
                    btnR.setFrame(1);
                    return 2;
                }
                if (fields[3].contains(x1, y1) && !vRestart.isSwitching) // Middle big
                {
                    btnRestart.setFrame(1);
                    return 5;
                }
                if (fields[4].contains(x1, y1) && renderState != 1) // Mute
                    return 6;
                if (fields[5].contains(x1, y1) && renderState != 1) // Switch boost
                    return 7;
            }

        } else if (Gdx.input.isTouched(1))
        { // second touch
            if (!EXTENDED_BTNS)
            {
                if (btnAction.containsTouch(x2, y2))
                    return 3;
                if (btnL.containsTouch(x2, y2))
                    return 1;
                if (btnR.containsTouch(x2, y2))
                    return 2;
                if (btnRestart.containsTouch(x2, y2))
                    return 5;
            } else
            {
                if (fields[1].contains(x2, y2) && renderState != 2) // Middle small
                {
                    btnAction.setFrame(1);
                    return 3;
                }
                if (fields[0].contains(x2, y2) && renderState != 2) // Left
                {
                    btnL.setFrame(1);
                    return 1;
                }
                if (fields[2].contains(x2, y2) && renderState != 2) // Right
                {
                    btnR.setFrame(1);
                    return 2;
                }
                if (fields[3].contains(x2, y2) && !vRestart.isSwitching) // Middle big
                {
                    btnRestart.setFrame(1);
                    return 5;
                }
            }
        }
        return 0;
    }

    public void setRenderState(int i)
    {
        if (i == 2)
        {
            vRestart.startAnimation();
            btnRestart.getPosition().y = -RocketGame.HEIGHT; // Delay
            btnRestartAnimation = true;
        } else
        {
            btnRestartAnimation = false;
        }


        renderState = i;
    }

    public void unpress()
    {
        if (!once)
        {
            once = true;
            btnL.setFrame(0);
            btnR.setFrame(0);
            btnAction.setFrame(0);
            btnStart.setFrame(0);
            btnRestart.setFrame(0);


        }

    }

    public void render(SpriteBatch sb)
    {
        switch (renderState)
        {
            case 0: // Menu
                btnL.render(sb);
                btnStart.render(sb);
                btnR.render(sb);
                // Sound btn
                if (!RocketGame.muted)
                    btnMute.render(sb, 0);
                else
                    btnMute.render(sb, 1);
                // Keep boost btn
                if (!RocketGame.keepBoost)
                    btnKeepboost.render(sb, 0);
                else
                    btnKeepboost.render(sb, 1);
                break;
            case 1: // Game
                btnL.render(sb);
                if ((RocketGame.keepBoost && Ship.getHoldBoost()) || (!RocketGame.keepBoost && PlayState.getDirection() == 3))
                    btnAction.render(sb, 1);
                else
                    btnAction.render(sb, 0);
                btnR.render(sb);
                break;
            case 2: // Died
                btnRestart.render(sb);
                if (!RocketGame.muted)
                    btnMute.render(sb, 0);
                else
                    btnMute.render(sb, 1);
                // Keep boost btn
                if (!RocketGame.keepBoost)
                    btnKeepboost.render(sb, 0);
                else
                    btnKeepboost.render(sb, 1);
        }
    }

    public void dispose()
    {
        if(disposed)
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

}

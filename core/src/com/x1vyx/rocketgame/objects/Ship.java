package com.x1vyx.rocketgame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.objects.items.Coin;
import com.x1vyx.rocketgame.objects.items.Paint;
import com.x1vyx.rocketgame.objects.items.Torpedo;
import com.x1vyx.rocketgame.states.PlayState;
import com.x1vyx.rocketgame.tools.Control;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;
import com.x1vyx.rocketgame.ui.Speed;

public class Ship extends GameObject
{
    private static float v, aX, aSlide, vX, maxVX, orix, oriy;
    private static boolean demo, dead;
    private static boolean holdBoost;
    private int frame = 3;
    private boolean moveStarted;
    private float aV = 0.004f, oria = aV, maxV = 4f, originalMaxV = maxV, breakV = 0.015f;
    private Fire fire;
    private Speed speed;
    public static float top;

    public Ship()
    {
        type = 1;
        orix = RocketGame.getWidth() / 2 - 7;
        oriy = 38;
        sprite = new Sprite("rocket.png", 7, orix, oriy);
        top = oriy + sprite.h;
        fire = new Fire((int) orix, (int) oriy - 7);
        v = 0.0f;
        vX = 0.0f;
        aX = 0.25f;
        aSlide = 0.15f;
        maxVX = 2.5f;
    }

    public static float getV()
    {
        return v;
    }

    public static boolean isDead()
    {
        return dead;
    }

    // Demo
    public void setDemo(boolean demo)
    {
        Ship.demo = demo;
    }

    public Ship setSpeed(Speed speed)
    {
        this.speed = speed;
        return this;
    }

    private void turnLeft()
    {
        if (PlayState.timer % 7 == 0)
            frame--;
        if (frame < 0)
            frame = 0;
    }

    private void turnRight()
    {
        if (PlayState.timer % 7 == 0)
            frame++;
        if (frame > 6)
            frame = 6;
    }

    public void reset()
    {
        sprite.x = (int) orix;
//        sprite.y = (int) oriy;
        v = oria;
        dead = false;
    }

    @Override
    public boolean update(float dt)
    {
        if (Control.getRenderState() == Control.RS_GAME)
        {
            fire.boosting = false;
            /* X-Position */
            maxV = originalMaxV;
            aV = oria;

            if (Control.left) // Left
            {
                if (vX >= -maxVX)
                    vX -= aX;
                if (!moveStarted)
                    moveStarted = true;
            } else if (Control.right) // Right
            {
                if (vX <= maxVX)
                    vX += aX;
                if (!moveStarted)
                    moveStarted = true;

            } else // Straight
            {
                if (vX < 0.05f && vX > -0.05f) // full xv stop
                    vX = 0;
                else // slide effect
                {
                    if (vX < 0)
                        vX += aSlide;
                    else
                        vX -= aSlide;
                }
            }
            if ((Control.action && !RocketGame.keepBoost) || (RocketGame.boosting && RocketGame.keepBoost)) // Boost
            {
                fire.boosting = true;
                maxV = 80f;
                aV = aV * 4;
            }

            sprite.x += vX;

            /* Fire animation */
            fire.setX(sprite.x);

            /* Infinity X-Direction */
            if (sprite.x < -sprite.w)
                sprite.x = RocketGame.getWidth();
            if (sprite.x > RocketGame.getWidth())
                sprite.x = -sprite.w;

            /* Speed */
            if (v <= maxV && !dead)
            {
                v += aV;
                speed.update((int) (Ship.getV() * 10));
            } else if (v > maxV + 0.1f) // Break
            {
                v -= breakV;
                speed.update((int) (Ship.getV() * 10));
            }

            /* Ship animation */
            if (PlayState.timer % 15 == 0 || moveStarted)
            {
                if (Control.left)
                {
                    turnLeft();
                } else if (Control.right)
                {
                    turnRight();
                } else
                {
                    frame = 3;
                }
                moveStarted = false;
            }

        } else if (dead)
        {
            if (v > -maxV * 100)
                v -= breakV;
        }

        // Demo & Play
        fire.update(dt);
        return true;
    }

    public boolean checkCoinCollision(Coin coin)
    {
        if (sprite.isColliding(coin.getSprite().x, coin.getSprite().y, coin.getSprite().w, coin.getSprite().h))
        {
            coin.reset();
            Gdx.input.vibrate(15);
            return true;
        }
        return false;
    }

    public boolean checkPaintCollision(Paint paint)
    {
        if (sprite.isColliding(paint.getSprite()))
        {
            paint.ok();
            Gdx.input.vibrate(30);
            return true;
        }
        return false;
    }

    public boolean checkTorpedoCollision(Torpedo torpedo)
    {
        if (sprite.isColliding(torpedo.getSprite()))
        {
            //torpedo.blow();

            Gdx.input.vibrate(40);
            return true;
        }
        return false;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        // fire height = 7;
        if (!dead)
        {
            sprite.render(sb, frame);
            fire.render(sb);
        }
        if (!demo)
            speed.render(sb);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        fire.dispose();
        if (!demo)
            speed.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("SHIP DISPOSED");
    }

    public void die()
    {
        dead = true;
        sprite.x = (int) orix;
    }

    public float getX()
    {
        return sprite.x;
    }

    public float getY()
    {
        return sprite.y;
    }

    public void switchBoost()
    {
        holdBoost = !holdBoost;
    }

    public static boolean getHoldBoost()
    {
        return holdBoost;
    }

}

package com.x1vyx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.objects.items.Coin;
import com.x1vyx.game.objects.items.Paint;
import com.x1vyx.game.objects.items.Torpedo;
import com.x1vyx.game.states.PlayState;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.Randomer;
import com.x1vyx.game.tools.RocketGame;
import com.x1vyx.game.tools.Sprite;
import com.x1vyx.game.ui.Speed;

public class Ship extends GameObject
{
    private static float v, aX, vX, maxVX, orix, oriy;
    private static boolean demo, dead;
    private static boolean holdBoost;
    private int frame = 3;
    private boolean moveStarted;
    private float aV = 0.004f, oria = aV, maxV = 4f, originalMaxV = maxV, breakV = 0.05f;
    private Fire fire;
    private Speed speed;

    public Ship()
    {
        type = 1;
        orix = RocketGame.WIDTH / 2 - 7;
        oriy = 38;
        sprite = new Sprite("rocket.png", 7, position = new Vector2(orix, oriy));
        fire = new Fire(orix, oriy - 7);
        v = 0.0f;
        vX = 0.0f;
        aX = 0.3f;
        maxVX = 2f;
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
        position.x = orix;
        position.y = oriy;
        v = oria;
        dead = false;
    }

    @Override
    public boolean update(float dt)
    {
        if (!demo && !dead)
        {
            fire.boosting = false;
            /* X-Position */
            // 0 -> stay, 1 -> left, 2 -> right
            int direction = PlayState.getDirection();
            maxV = originalMaxV;
            aV = oria;

            if (direction == 1) // Left
            {
                if (vX >= -maxVX)
                    vX -= aX;
                if (!moveStarted)
                    moveStarted = true;
            } else if (direction == 2) // Right
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
                        vX += aX;
                    else
                        vX -= aX;
                }
            }
            if ((direction == 3 && !RocketGame.keepBoost) || (holdBoost && RocketGame.keepBoost)) // Boost
            {
                fire.boosting = true;
                maxV = 80f;
                aV = aV * 4;
            }

            position.x += vX;

            /* Fire animation */
            fire.setX(position.x);

            /* Infinity X-Direction */
            if (position.x < -sprite.w)
                position.x = RocketGame.WIDTH;
            if (position.x > RocketGame.WIDTH)
                position.x = -sprite.w;

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
                if (direction == 0)
                {
                    frame = 3;
                } else if (direction == 1)
                {
                    turnLeft();
                } else if (direction == 2)
                {
                    turnRight();
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

    public boolean checkCoinCollision(Coin[] coins)
    {
        for (Coin coin : coins)
            if (sprite.isColliding(coin.x, coin.y, coin.w, coin.h))
            {
                coin.genNewPos();
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
        if(!demo)
            speed.dispose();
        System.out.println("SHIP DISPOSED");
    }

    public void die() { dead = true; }

    public float getX() { return position.x; }

    public float getY() { return position.y; }

    public void switchBoost() { holdBoost = !holdBoost; }

    public static boolean getHoldBoost() { return holdBoost; }

}

package com.x1vyx.rocketgame.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.QFunction;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;

public class Score extends GameObject
{
    private final int MAX_DIG = 3;
    private int value;
    private int[] frames;
    private int[] x;
    private boolean animate;
    private QFunction v; // TODO: Optimize for 16by9

    public Score()
    {
        sprite = new Sprite("score.png", 10, 0, 0); // Default: RocketGame.getHeight() * 0.85f
        sprite.y = -sprite.h;
        frames = new int[MAX_DIG];
        x = new int[MAX_DIG];
        x[0] = RocketGame.getWidth() / 2 - sprite.w / 2;
        v = new QFunction(RocketGame.isWide ? 5.45f : 4.9f);
        v.reset();
        animate = true;
    }

    public void add()
    {
        value++;

        // Shift digits
        if (value > 99)
        {
            frames[2] = value / 100 % 10;
            x[0] = RocketGame.getWidth() / 2 + sprite.w / 2;
            x[1] = RocketGame.getWidth() / 2 - sprite.w / 2;
            x[2] = RocketGame.getWidth() / 2 - sprite.w - sprite.w / 2;
        } else if (value > 9)
        {
            frames[1] = value / 10 % 10;
            x[0] = RocketGame.getWidth() / 2;
            x[1] = RocketGame.getWidth() / 2 - sprite.w;
        }
        frames[0] = value % 10;
    }

    public void add(int i)
    {
        value += i;
        if (value > 99)
        {
            frames[2] = value / 100 % 10;
            if (x[0] != RocketGame.getWidth() / 2 + sprite.w / 2) // update only once
            {
                x[0] = RocketGame.getWidth() / 2 + sprite.w / 2;
                x[1] = RocketGame.getWidth() / 2 - sprite.w / 2;
                x[2] = RocketGame.getWidth() / 2 - sprite.w - sprite.w / 2;
            }
        }
        if (value > 9)
        {
            frames[1] = value / 10 % 10;
            if (x[0] != RocketGame.getWidth() / 2 && value < 99) // update only once
            {
                x[0] = RocketGame.getWidth() / 2;
                x[1] = RocketGame.getWidth() / 2 - sprite.w;
            }
        }
        frames[0] = value % 10;
    }

    public void die()
    {
        animate = true;
    }

    public void reset()
    {
        value = 0;
        frames[0] = 0;
        sprite.y = -sprite.h;
        x[0] = RocketGame.getWidth() / 2 - sprite.w / 2;
        v.reset();
        animate = true;
    }

    @Override
    public boolean update(float dt) // TODO: optimize
    {
        if (animate)
        {
            sprite.y += v.getNextV();
            animate = v.isSwitching;
        }

        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        if (value > 99)
            sprite.render(sb, frames[2], x[2], sprite.y);
        if (value > 9)
            sprite.render(sb, frames[1], x[1], sprite.y);
        sprite.render(sb, frames[0], x[0], sprite.y);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("SCORE DIGITS DISPOSED");
    }


    public int getValue()
    {
        return value;
    }

}

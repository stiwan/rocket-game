package com.x1vyx.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.QFunction;
import com.x1vyx.game.tools.RocketGame;
import com.x1vyx.game.tools.Sprite;

public class Score extends GameObject
{
    private final int MAX_DIG = 3;
    private int value;
    private int[] frames;
    private int[] x;
    private boolean die;
    private QFunction v;

    public Score()
    {
        sprite = new Sprite("score.png", 10, position = new Vector2(0, 0));
        frames = new int[MAX_DIG];
        x = new int[MAX_DIG];
        x[0] = RocketGame.WIDTH / 2 - sprite.w / 2;
        v = new QFunction();
        position.y = RocketGame.HEIGHT - 30;
    }

    public void add()
    {
        value++;
    }

    public void add(int i)
    {
        value += i;
    }

    public void die()
    {
        die = true;
        v.startAnimation();
    }

    public void reset()
    {
        value = 0;
        x[0] = RocketGame.WIDTH / 2 - sprite.w / 2;
        position.y = -30;
        v.startAnimation();
        die = false;
    }

    @Override
    public boolean update(float dt)
    {
        if (value > 99)
        {
            frames[2] = value / 100 % 10;
            if (x[0] != RocketGame.WIDTH / 2 + sprite.w / 2) // update only once
            {
                x[0] = RocketGame.WIDTH / 2 + sprite.w / 2;
                x[1] = RocketGame.WIDTH / 2 - sprite.w / 2;
                x[2] = RocketGame.WIDTH / 2 - sprite.w - sprite.w / 2;
            }
        }
        if (value > 9)
        {
            frames[1] = value / 10 % 10;
            if (x[0] != RocketGame.WIDTH / 2 && value < 99) // update only once
            {
                x[0] = RocketGame.WIDTH / 2;
                x[1] = RocketGame.WIDTH / 2 - sprite.w;
            }
        }
        frames[0] = value % 10;

        if (die)
            position.y += v.getNextV();
        if(!die && v.isSwitching) // Return to original
            position.y += v.getNextV();

        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        if (value > 99)
            sprite.render(sb, frames[2], x[2], position.y);
        if (value > 9)
            sprite.render(sb, frames[1], x[1], position.y);
        sprite.render(sb, frames[0], x[0], position.y);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        System.out.println("SCORE DIGITS DISPOSED");
    }


    public int getValue()
    {
        return value;
    }

    public boolean isDead()
    {
        return die;
    }
}

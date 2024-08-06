package com.x1vyx.rocketgame.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.QFunction;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;

public class ScoreBoard extends GameObject
{
    private int score;
    private boolean active;
    private QFunction v;


    public ScoreBoard()
    {
        sprite = new Sprite("score-board.png", 1, 0, 0);
        sprite.x = RocketGame.getWidth() / 2f - sprite.w / 2f;
        sprite.y = -sprite.h;
        v = new QFunction(RocketGame.isWide ? 5f : 4f);
    }

    public void activate(int score)
    {
        this.score = score;
        active = true;
        sprite.y = -sprite.h;
        v.reset();
    }

    public void reset()
    {
        score = 0;
        v.reset();
    }


    @Override
    public boolean update(float dt)
    {
        if (active && v.isSwitching)
        {
            sprite.y += v.getNextV();
            if (sprite.y > RocketGame.getHeight())
                active = false;
        }
        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        if (active)
        {
            sprite.render(sb);
            Sprite.renderNumbers(sb, score, sprite.x + sprite.w - 17, sprite.y + 5, 5);
        }
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("SCORE BOARD DISPOSED");
    }
}

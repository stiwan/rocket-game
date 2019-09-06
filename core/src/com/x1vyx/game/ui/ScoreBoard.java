package com.x1vyx.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.QFunction;
import com.x1vyx.game.tools.RocketGame;
import com.x1vyx.game.tools.Sprite;

public class ScoreBoard extends GameObject
{
    private int score;
    private boolean active;
    private QFunction v;


    public ScoreBoard()
    {
        sprite = new Sprite("score-board.png", 1, position = new Vector2(0, 0));
        position.x = RocketGame.WIDTH / 2f - sprite.w / 2f;
        position.y = -sprite.h;
        v = new QFunction(6.3f);
    }

    public void activate(int score)
    {
        this.score = score;
        active = true;
        position.y = -sprite.h;
        v.startAnimation(); // Standard velocity @ y = 0
    }

    public void reset()
    {
        score = 0;
        v.startAnimation();
    }


    @Override
    public boolean update(float dt)
    {
        if (active)
        {
            position.y += v.getNextV();
            if (position.y > RocketGame.HEIGHT)
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
            Sprite.renderNumbers(sb, score, position.x + sprite.w - 17, position.y + 5, 5);
        }
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        System.out.println("SCORE BOARD DISPOSED");
    }
}

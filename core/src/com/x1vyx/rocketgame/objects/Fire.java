package com.x1vyx.rocketgame.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;

public class Fire extends GameObject
{
    public boolean boosting;
    private Sprite boost;

    public Fire(float x, float y)
    {
        sprite = new Sprite("fire.png", 10, x, y).setCycleTime(0.5f);
        boost = new Sprite("boost.png", 4, x, y - 8).setCycleTime(0.5f);
    }

    @Override
    public boolean update(float dt)
    {
        if (!boosting)
            sprite.update(dt);
        else
            boost.update(dt);
        return true;
    }

    public void setX(float x)
    {
        sprite.x = x;
        boost.x = x;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        if (!boosting)
            sprite.render(sb);
        else
            boost.render(sb);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        boost.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("FIRE DISPOSED");
    }
}

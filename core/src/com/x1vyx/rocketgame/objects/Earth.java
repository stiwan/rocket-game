package com.x1vyx.rocketgame.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;

public class Earth extends GameObject
{
    private boolean dead;

    public Earth()
    {
        sprite = new Sprite("back.png", 1, 0, 0);
    }

    public void reset()
    {
        sprite.y = 0;
        dead = false;
    }

    @Override
    public boolean update(float dt)
    {
        if (sprite.y > -RocketGame.getHeight())
            sprite.y -= Ship.getV();
        else
            dead = true;

        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        if (!dead && sprite.y < 0)
            sprite.render(sb);
        else if (!dead)
            sprite.render(sb, 0, 0);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("EARTH DISPOSED");
    }

}

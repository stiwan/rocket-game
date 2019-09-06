package com.x1vyx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.RocketGame;
import com.x1vyx.game.tools.Sprite;

public class Earth extends GameObject
{
    private Sprite sprite;
    private boolean dead;

    public Earth()
    {
        sprite = new Sprite("back.png", 1, position = new Vector2(0, 0));
    }

    public void reset()
    {
        position.y = 0;
        dead = false;
    }

    @Override
    public boolean update(float dt)
    {
        if (position.y > -RocketGame.HEIGHT)
            position.y -= Ship.getV();
        else
            dead = true;

        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        if (!dead)
            sprite.render(sb);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        System.out.println("EARTH DISPOSED");
    }

}

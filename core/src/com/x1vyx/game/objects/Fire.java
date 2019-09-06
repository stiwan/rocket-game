package com.x1vyx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.Sprite;

public class Fire extends GameObject
{
    public boolean boosting;
    private Sprite boost;

    public Fire(float x, float y)
    {
        sprite = new Sprite("fire.png", 10, position = new Vector2(x, y)).setCycleTime(0.5f);
        boost = new Sprite("boost.png", 4, position).setCycleTime(0.5f);
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
        position.x = x;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        if (!boosting)
            sprite.render(sb);
        else
            boost.render(sb, position.x, position.y - 8);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        boost.dispose();
        System.out.println("FIRE DISPOSED");
    }
}

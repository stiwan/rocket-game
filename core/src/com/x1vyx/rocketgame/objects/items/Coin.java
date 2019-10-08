package com.x1vyx.rocketgame.objects.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.objects.Ship;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.Randomer;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;

public class Coin extends GameObject
{
    public Coin()
    {
        sprite = new Sprite("coin.png", 6,92, 99).setCycleTime(0.5f);
    }

    public void reset()
    {
        sprite.x = Randomer.getX();
        sprite.y = Randomer.getY(1);
    }

    public boolean update(float dt)
    {
        sprite.y -= Ship.getV();
        if (sprite.y <= 0)
            reset();

        sprite.update(dt);

        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        if(sprite.y < RocketGame.getHeight())
            sprite.render(sb);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("COINS DISPOSED");
    }
}

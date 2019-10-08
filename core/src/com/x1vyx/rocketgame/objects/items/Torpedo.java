package com.x1vyx.rocketgame.objects.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.objects.Ship;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.Randomer;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;

public class Torpedo extends GameObject
{
    private Sprite warning;
    private static int warningAppearFactor = 5;
    private boolean demo;

    public Torpedo()
    {
        type = 5;
        sprite = new Sprite("torpedo.png", 7,0, 0).setCycleTime(0.2f);
        warning = new Sprite("warning.png", 2,0,0).setCycleTime(0.1f);
    }

    public void reset()
    {
        sprite.x = Randomer.getX();
        sprite.y = Randomer.getY(10);
    }

    @Override
    public boolean update(float dt)
    {
        sprite.update(dt);
        warning.update(dt);
        if(!demo)
            if (sprite.y <= 0)
                reset();
            else
                sprite.y -= (Ship.getV() + 3);
        return true;
    }

    public void setDemo(boolean d)
    {
        this.demo = d;
        sprite.y = 117;
        sprite.x = 80;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        // Render Warning sign @ top of the screen
        if(sprite.y <= RocketGame.getHeight() * warningAppearFactor && sprite.y >= RocketGame.getHeight())
            warning.render(sb, sprite.x + 1, RocketGame.getHeight() - warning.h - 1);
        sprite.render(sb);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("TORPEDO DISPOSED");
    }
}

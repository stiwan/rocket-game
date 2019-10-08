package com.x1vyx.rocketgame.objects.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.objects.Ship;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.Randomer;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;

public class Paint extends GameObject
{

    public Paint()
    {
        type = 4;
        sprite = new Sprite("paint.png", 10,0, 0).setCycleTime(0.5f);
        genNewPos();
    }

    @Override
    public boolean update(float dt)
    {
        sprite.update(dt);
        sprite.y -= Ship.getV();
        if (sprite.y <= 0)
            genNewPos();

        return true;
    }

    public void reset()
    {
        genNewPos();
    }

    private void genNewPos()
    {
        sprite.x = Randomer.getX();
        sprite.y = Randomer.getY(6);
    }

    public void ok()
    {
        genNewPos();
    }

    public void setDemo()
    {
        sprite.x = 38;
        sprite.y = 78;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sprite.render(sb, 0, true);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("PAINT DISPOSED");
    }
}

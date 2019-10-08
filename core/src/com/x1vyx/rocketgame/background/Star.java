package com.x1vyx.rocketgame.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.objects.Ship;
import com.x1vyx.rocketgame.tools.Field;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.Randomer;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;
import com.x1vyx.rocketgame.ui.Speed;

public class Star extends GameObject
{
    private final int LENGTH = 7;
    private Field[] boxes;
    private int frameState;

    public Star()
    {
        sprite = new Sprite("star.png", 3, 0, 0);
        boxes = new Field[LENGTH];
        for (int i = 0; i < LENGTH; i++)
        {
            boxes[i] = new Field(0, 0);
            genNewPos(i);
        }
    }

    public void reset()
    {
        for (int i = 0; i < LENGTH; i++)
            genNewPos(i);

        frameState = 0;
    }

    private void genNewPos(int i)
    {
        boxes[i].x = Randomer.getX();
        if (!Ship.isDead())
            boxes[i].y = Randomer.getY(1);
        else
            boxes[i].y = Randomer.getY(0);
    }

    @Override
    public boolean update(float dt)
    {
        for (int i = 0; i < LENGTH; i++)
        {
            if ((boxes[i].y < 0 && !Ship.isDead()) || (boxes[i].y > RocketGame.getHeight() && Ship.isDead()))
                genNewPos(i);
            else
                boxes[i].y -= Ship.getV() / 20;
        }

        if (Speed.getValue() > 100 || Speed.getValue() < -100)
            frameState = 2;
        else if (Speed.getValue() > 45 || Speed.getValue() < -45)
            frameState = 1;
        else
            frameState = 0;

        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        for (int i = 0; i < LENGTH; i++)
        {
            sprite.render(sb, frameState, boxes[i].x, boxes[i].y);
        }

    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("STARS DISPOSED");
    }
}

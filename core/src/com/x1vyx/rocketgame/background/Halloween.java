package com.x1vyx.rocketgame.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.objects.Ship;
import com.x1vyx.rocketgame.tools.Field;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.Randomer;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;

public class Halloween extends GameObject
{
    private final int LENGTH = 3;
    private Field[] boxes;
    private int[] frames;
    private final float SPEED = 0.25f;

    public Halloween()
    {
        sprite = new Sprite("halloween.png", LENGTH, 0, 0);
        boxes = new Field[LENGTH];
        frames = new int[LENGTH];
        for (int i = 0; i < LENGTH; i++)
        {
            boxes[i] = new Field(0, 0);
            genNewPos(i);
        }
        for (int i = 0; i < LENGTH; i++)
            frames[i] = Randomer.getInt(LENGTH);
    }

    public void reset()
    {
        for (int i = 0; i < LENGTH; i++)
            genNewPos(i);
    }
    private void genNewPos(int i)
    {
        boxes[i].x = Randomer.getX() - 20;
        if (Ship.getV() > -0.1f)
            boxes[i].y = Randomer.getY(2);
    }

    @Override
    public boolean update(float dt)
    {
        for (int i = 0; i < LENGTH; i++)
        {
            if (boxes[i].y < (-20 - Randomer.getInt(RocketGame.getHeight() / 2)) && Ship.getV() > 0)
                genNewPos(i);
            else
                boxes[i].y -= Ship.getV();

            boxes[i].x += SPEED;
        }

        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        for (int i = 0; i < LENGTH; i++)
            sprite.render(sb, frames[i], boxes[i].x, boxes[i].y);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("HALLOWEEN DISPOSED");
    }
}

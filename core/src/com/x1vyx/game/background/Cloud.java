package com.x1vyx.game.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.objects.Ship;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.Randomer;
import com.x1vyx.game.tools.Sprite;

public class Cloud extends GameObject
{
    private final int LENGTH = 4;
    private Vector2[] positions;
    private int[] frames;
    private final float SPEED = -0.25f;

    public Cloud()
    {
        sprite = new Sprite("clouds.png", LENGTH, new Vector2(0, 0));
        positions = new Vector2[LENGTH];
        frames = new int[LENGTH];
        for (int i = 0; i < LENGTH; i++)
        {
            positions[i] = new Vector2(0, 0);
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
        positions[i].x = Randomer.getX() + 20;
        if (Ship.getV() > -0.1f)
            positions[i].y = Randomer.getY(2);
    }

    @Override
    public boolean update(float dt)
    {
        for (int i = 0; i < LENGTH; i++)
        {
            if (positions[i].y < -20 && Ship.getV() > 0)
                genNewPos(i);
            else
                positions[i].y -= Ship.getV();

            positions[i].x += SPEED;
        }

        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        for (int i = 0; i < LENGTH; i++)
            sprite.render(sb, frames[i], positions[i].x, positions[i].y);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        System.out.println("CLOUDS DISPOSED");
    }
}

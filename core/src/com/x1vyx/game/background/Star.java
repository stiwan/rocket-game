package com.x1vyx.game.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.objects.Ship;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.Randomer;
import com.x1vyx.game.tools.RocketGame;
import com.x1vyx.game.tools.Sprite;
import com.x1vyx.game.ui.Speed;

public class Star extends GameObject
{
    private final int LENGTH = 7;
    private Vector2[] positions;
    private int frameState;

    public Star()
    {
        sprite = new Sprite("star.png", 3, new Vector2(0, 0));
        positions = new Vector2[LENGTH];
        for (int i = 0; i < LENGTH; i++)
        {
            positions[i] = new Vector2(0, 0);
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
        positions[i].x = Randomer.getX();
        if(!Ship.isDead())
            positions[i].y = Randomer.getY(1);
        else
            positions[i].y = Randomer.getY(0);
    }

    @Override
    public boolean update(float dt)
    {
        for (int i = 0; i < LENGTH; i++)
        {
            if ((positions[i].y < 0 && !Ship.isDead()) || (positions[i].y > RocketGame.HEIGHT && Ship.isDead()))
                genNewPos(i);
            else
                positions[i].y -= Ship.getV() / 20;
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
            sprite.render(sb, frameState, positions[i].x, positions[i].y);
        }

    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        System.out.println("STARS DISPOSED");
    }
}

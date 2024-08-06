package com.x1vyx.rocketgame.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.objects.Ship;
import com.x1vyx.rocketgame.states.PlayState;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.Randomer;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;

public class Bird extends GameObject
{
    private final int FRAME_LENGTH = 4;
    private float velX, velY;
    private int frame;

    public Bird()
    {
        sprite = new Sprite("bird.png", FRAME_LENGTH,0, 0);
        genNewPos();
    }

    private void genNewPos()
    {
        sprite.x = Randomer.getX() - 3;
        sprite.y = Randomer.getY(2);
        velX = 1;
    }

    public void reset()
    {
        genNewPos();
    }

    @Override
    public boolean update(float dt)
    {
        if (PlayState.timer % 5 == 0)
        {
            if (sprite.x >= RocketGame.getWidth() || sprite.y < 0)
                genNewPos();
            velY = Randomer.getFloat() - 0.5f;
            if (frame == FRAME_LENGTH - 1)
                frame = 0;
            else if (velY > 0)
                frame++;

            sprite.x += velX;
        }
        sprite.y += (velY - Ship.getV());
        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sprite.render(sb, frame);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("BIRDS DISPOSED");
    }
}

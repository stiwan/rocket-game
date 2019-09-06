package com.x1vyx.game.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.objects.Ship;
import com.x1vyx.game.states.PlayState;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.Randomer;
import com.x1vyx.game.tools.RocketGame;
import com.x1vyx.game.tools.Sprite;

public class Bird extends GameObject
{
    private final int FRAME_LENGTH = 4;
    private float velX, velY;
    private int frame;

    public Bird()
    {
        sprite = new Sprite("bird.png", FRAME_LENGTH, position = new Vector2(0, 0));
        genNewPos();
    }

    private void genNewPos()
    {
        position.x = Randomer.getX() - 3;
        position.y = Randomer.getY(2);
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
            if (position.x >= RocketGame.WIDTH || position.y < 0)
                genNewPos();
            velY = Randomer.getFloat() - 0.5f;
            if (frame == FRAME_LENGTH - 1)
                frame = 0;
            else if (velY > 0)
                frame++;

            position.x += velX;
        }
        position.y += (velY - Ship.getV());
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
        System.out.println("BIRDS DISPOSED");
    }
}

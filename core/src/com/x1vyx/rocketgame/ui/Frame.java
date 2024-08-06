package com.x1vyx.rocketgame.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;

public class Frame extends GameObject
{
    private Sprite[] sprites;
    private final int FRAME_WIDTH = 8;

    public Frame()
    {
        sprites = new Sprite[4];
        sprites[0] = new Sprite("frame-left.png", 1, 0,FRAME_WIDTH);
        sprites[1] = new Sprite("frame-right.png", 1, RocketGame.getWidth() - FRAME_WIDTH, FRAME_WIDTH);
        sprites[2] = new Sprite("frame-up.png", 1, 0,RocketGame.getHeight() - FRAME_WIDTH);
        sprites[3] = new Sprite("frame-low.png", 1, 0,0);

    }

    @Override
    public boolean update(float dt)
    {
        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        for (int i = 0; i < sprites.length; i++)
        {
            sprites[i].render(sb);
        }
    }

    @Override
    public void dispose()
    {
        for (int i = 0; i < sprites.length; i++)
        {
            sprites[i].dispose();
        }
    }
}

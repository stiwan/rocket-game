package com.x1vyx.rocketgame.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.states.PlayState;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.Sprite;

public class Mushroom extends GameObject
{
    private float x, y, maxWay;
    private int plusPoints;

    public Mushroom(int plusPoints, float x, float y)
    {
        this.plusPoints = plusPoints;
        this.x = x;
        this.y = y;
        maxWay = 30 + y;

    }

    @Override
    public boolean update(float dt)
    {
        if (PlayState.timer % 2 == 0)
            y++;

        return (y < maxWay);
    }

    @Override
    public void render(SpriteBatch sb)
    {
        Sprite.renderNumbers(sb, plusPoints, x, y, true);
    }

    @Override
    public void dispose() { }
}

package com.x1vyx.rocketgame.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.objects.Ship;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.Sprite;

public class DistanceManager
{
    private static float value;
    private final int MARGIN = 3;

    public void update(float dt)
    {
        // - - - -
        if (value > -333)
            value += Ship.getV() / 10;
        else
            value = -333;
    }

    public void reset()
    {
        value = 0;
    }

    public void render(SpriteBatch sb)
    {
        Sprite.renderNumbers(sb, (int) value, RocketGame.getWidth() - Sprite.letterW - MARGIN, RocketGame.getHeight() - Sprite.letterW - MARGIN, false);
    }
}

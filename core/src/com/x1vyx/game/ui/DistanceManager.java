package com.x1vyx.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.game.objects.Ship;
import com.x1vyx.game.tools.RocketGame;
import com.x1vyx.game.tools.Sprite;

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
        Sprite.renderNumbers(sb, (int) value, RocketGame.WIDTH - Sprite.letterW - MARGIN, RocketGame.HEIGHT - Sprite.letterW - MARGIN, false);
    }
}

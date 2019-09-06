package com.x1vyx.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.game.tools.RocketGame;
import com.x1vyx.game.tools.Sprite;

public class Speed
{
    private static int value, length = 1, margin = 3;

    public void update(int vu)
    {
        value = vu;

        if (value > 9)
            if (value > 99)
                if (value > 999)
                    if (value > 9999)
                        length = 4;
                    else
                        length = 3;
                else
                    length = 2;
            else
                length = 1;
        else
            length = 0;
    }


    public void render(SpriteBatch sb)
    {
        Sprite.renderNumbers(sb, value, length * 5 + margin, RocketGame.HEIGHT - Sprite.letterW - margin, false);
    }

    public void dispose() { }
    public static int getValue()
    {
        return value;
    }

}

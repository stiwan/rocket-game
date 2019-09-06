package com.x1vyx.game.objects.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.objects.Ship;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.Randomer;
import com.x1vyx.game.tools.RocketGame;
import com.x1vyx.game.tools.Sprite;

public class Torpedo extends GameObject
{
    private Sprite warning;
    private static int warningAppearFactor = 6;
    private boolean demo;

    public Torpedo()
    {
        type = 5;
        sprite = new Sprite("torpedo.png", 7, position = new Vector2(0, 0)).setCycleTime(0.2f);
        warning = new Sprite("warning.png", 2, new Vector2(0,0)).setCycleTime(0.1f);
    }

    public void reset()
    {
        generatePosition();
    }

    @Override
    public boolean update(float dt)
    {
        sprite.update(dt);
        warning.update(dt);
        if(!demo)
            if (position.y <= 0)
                generatePosition();
            else
                position.y -= (Ship.getV() + 3);
        return true;
    }

    private void generatePosition()
    {
        position.x = Randomer.getX();
        position.y = Randomer.getY(10);
    }

    public static void setWarningAppearFactor(int factor)
    {
        warningAppearFactor = factor;
    }

    public void setDemo(boolean d)
    {
        this.demo = d;
        position.y = 127;
        position.x = 80;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        // Render Warning sign @ top of the screen
        if(position.y <= RocketGame.HEIGHT * warningAppearFactor && position.y >= RocketGame.HEIGHT)
            warning.render(sb, position.x + 1, RocketGame.HEIGHT - warning.h - 1);
        sprite.render(sb);
    }

    @Override
    public void dispose()
    {
        sprite.dispose();
        System.out.println("TORPEDO DISPOSED");
    }
}

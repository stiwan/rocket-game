package com.x1vyx.game.background;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.QFunction;
import com.x1vyx.game.tools.RocketGame;
import com.x1vyx.game.tools.Sprite;

public class Background extends GameObject
{
    private static final int BACK_SHADE = 3;
    private static Texture texture0, texture1;
    private static Pixmap pixmap;
    private static boolean active0 = true;
    private static float y;

    private boolean disposed;

    private QFunction v;

    public Background()
    {
        type = 1;
        int w = RocketGame.WIDTH;
        int h = RocketGame.HEIGHT;
        texture0 = new Texture(w, h, Pixmap.Format.RGBA8888);
        pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        pixmap.setColor(Sprite.MONO[Sprite.activeColor][BACK_SHADE]);
        pixmap.fill();
        texture0.draw(pixmap, 0, 0);
        texture1 = new Texture(w, h, Pixmap.Format.RGBA8888);

        v = new QFunction();
    }

    public void switchColor(boolean next)
    {
        if (!v.isSwitching)
        {
            // Switch Theme
            if (next)
                Sprite.nextActiveColor();
            else
                Sprite.prevActiveColor();

            pixmap.setColor(Sprite.MONO[Sprite.activeColor][BACK_SHADE]);
            pixmap.fill();

            if (active0)
                texture1.draw(pixmap, 0, 0);
            else
                texture0.draw(pixmap, 0, 0);

            y = 0;
            active0 = !active0;

            v.startAnimation();
        }

    }


    @Override
    public boolean update(float dt)
    {
        if (v.isSwitching)
            y += v.getNextV();


        return true;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        if (active0)
        {
            sb.draw(texture0, 0, 0);
            if (v.isSwitching)
                sb.draw(texture1, 0, y);
        } else
        {
            sb.draw(texture1, 0, 0);
            if (v.isSwitching)
                sb.draw(texture0, 0, y);
        }
    }

    @Override
    public void dispose()
    {
        if(!disposed)
        {
            texture0.dispose();
            texture1.dispose();
            pixmap.dispose();
            System.out.println("BACKGROUND DISPOSED");
            disposed = true;
        }
    }
}
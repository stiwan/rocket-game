package com.x1vyx.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x1vyx.game.tools.Field;
import com.x1vyx.game.tools.GameObject;
import com.x1vyx.game.tools.RocketGame;
import com.x1vyx.game.tools.Sprite;

import java.util.ArrayList;

public class Popup extends GameObject
{
    public boolean isDisposed;
    private int w = 90, h = 140, x, y;
    private String title, content;
    private Sprite background, btn;
    private ArrayList<String> lines;
    private String cntBtn0, cntBtn1;
    // btns
    private int x0, y0, x1, y1, wBtn = 30, hBtn = 16;
    private Field[] fields;
    private int btn0Frame, btn1Frame;
    private boolean btn0, btn1;


    public Popup(String title, String content, String cntBtn0, String cntBtn1)
    { // title max = 16; content max = 16*x
        this.title = title;
        this.content = content;
        this.cntBtn0 = cntBtn0;
        this.cntBtn1 = cntBtn1;
        // center
        x = RocketGame.WIDTH / 2 - this.w / 2;
        y = RocketGame.HEIGHT / 2 - this.h / 2;
        background = new Sprite("popup.png", 1, new Vector2(x, y));

        lines = new ArrayList<String>();
        int numLines = (int) Math.ceil(content.length() / 16.0);
        for (int i = 0; i < numLines; i++)
        {
            int i0 = i * 15;
            int i1 = i0 + 15;
            if (i1 > content.length())
                i1 = content.length();
            lines.add(new String(content.substring(i0, i1)));
        }

        // btns
        btn = new Sprite("btn-blank.png", 2, new Vector2(0, 0));
        if (cntBtn1 != null)
        {
            x0 = x + 4;
            y0 = y + 6;
            x1 = x + 48;
            y1 = y + 6;
            fields = new Field[2];
            fields[0] = new Field(x0, y0 - 25, wBtn, hBtn + 50);
            fields[1] = new Field(x1, y1 - 25, wBtn, hBtn + 50);
        } else
        {
            x0 = x + 29;
            y0 = y + 6;
            fields = new Field[1];
            fields[0] = new Field(x0, y0 - 25, wBtn, hBtn + 50);
        }
    }

    @Override
    public boolean update(float dt)
    {
        return true;
    }


    public void update(int x, int y)
    {
        if (cntBtn1 != null)
        {
            btn0Frame = 0;
            btn1Frame = 0;
            if (fields[0].contains(x, y))
            {
                btn0Frame = 1;
                btn0 = true;
            }
            if (fields[1].contains(x, y))
            {
                btn1Frame = 1;
                btn1 = true;
            }
        } else
        {
            btn0Frame = 0;
            if (fields[0].contains(x, y))
            {
                btn0Frame = 1;
                btn0 = true;
            }
        }
    }

    // 0 -> nothing
    // 1 -> first btn
    // 2 -> second btn
    public int check()
    {
        btn0Frame = 0;
        btn1Frame = 0;
        if (btn0)
            return 1;
        if (btn1)
            return 2;
        return 0;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        background.render(sb);
        int xTitle = 8 * Sprite.letterW - title.length() * Sprite.letterW / 2;
        Sprite.renderText(sb, title, x + 6 + xTitle, y + h - 11);
        Sprite.renderText(sb, "**************", x + 10, y + h - 20);
        int yLine = y + h - 27;
        for (String line : lines)
        {
            Sprite.renderText(sb, line, x + 10, yLine);
            yLine -= 7;
        }
        if (cntBtn1 != null)
        {
            btn.render(sb, btn0Frame, x0, y0);
            Sprite.renderText(sb, cntBtn0, x0 + 5, y0 + 5);
            btn.render(sb, btn1Frame, x1, y1);
            Sprite.renderText(sb, cntBtn1, x1 + 5, y1 + 5);
        } else
        {
            btn.render(sb, btn0Frame, x0, y0);
            Sprite.renderText(sb, cntBtn0, x0 + 5, y0 + 5);
        }
    }

    @Override
    public void dispose()
    {
        background.dispose();
        btn.dispose();
        isDisposed = true;
        System.out.println("POPUP DISPOSED");
    }
}

package com.x1vyx.rocketgame.states;

// TODO: colors abtn update

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x1vyx.rocketgame.background.Background;
import com.x1vyx.rocketgame.objects.Ship;
import com.x1vyx.rocketgame.objects.items.Coin;
import com.x1vyx.rocketgame.objects.items.Paint;
import com.x1vyx.rocketgame.objects.items.Torpedo;
import com.x1vyx.rocketgame.tools.Control;
import com.x1vyx.rocketgame.tools.GameObject;
import com.x1vyx.rocketgame.tools.RocketGame;
import com.x1vyx.rocketgame.tools.SoundControl;
import com.x1vyx.rocketgame.tools.Sprite;
import com.x1vyx.rocketgame.ui.Frame;

import java.util.ArrayList;

/*
NOTES
    - createdList will be given over to the play state while switching

 */

public class MenuState extends State
{
    public Control control;
    private Sprite logo;
    private Frame frame;
    public Background b;
    public Ship ship;
    // TEST
    public Coin coin;
    public Paint paint;
    public Torpedo t;

    // Config
    private boolean showTips = true;


    public MenuState(GameStateManager gsm)
    {
        super(gsm);

        // Init
        control = new Control();
        control.setRS(0);
        logo = new Sprite("logo.png", 1, 18, RocketGame.getHeight() * 0.67f);
        frame = new Frame();
        b = new Background();
        ship = new Ship();
        ship.setDemo(true);
        coin = new Coin();
        paint = new Paint();
        t = new Torpedo();

        // Sound
        SoundControl.init();

        // Config
        if (showTips)
        {
            paint.setDemo();
            t.setDemo(true);
        }
        // Add
        objects = new ArrayList<GameObject>();
        objects.add(b);
        objects.add(coin);
        objects.add(paint);
        objects.add(ship);
        objects.add(t);
    }

    @Override
    public void update(float dt)
    {
        control.update(dt);

        for (GameObject elem : objects)
            elem.update(dt);

        if (Control.start)
            gsm.set(new PlayState(gsm, this));

        // timer
        if (timer > 960)
            timer = 0;
        else
            timer++;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(cam.combined);
        for (GameObject elem : objects)
            elem.render(sb);
        control.render(sb);
        Sprite.renderText(sb, "avoid them ->", 10, 120);
        Sprite.renderText(sb, "collect this ->", 10, 100);
        Sprite.renderText(sb, "<- and that", 50, 80);
        if (RocketGame.DEV_MODE)
            ;//Sprite.renderText(sb, "IS WIDE: " + RocketGame.isWide, 10, 50);
        frame.render(sb);
        logo.render(sb);
    }

    @Override
    public void dispose() // dispose all media (exit from menu)
    {
        logo.dispose();
        frame.dispose();
        SoundControl.dispose();
        control.dispose();
        for (GameObject elem : objects)
            elem.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("MENU DISPOSED");
    }

    public void disposeForSwitch() // for switching to play state
    {
        logo.dispose();
        frame.dispose();
        if (RocketGame.DEV_MODE)
            System.out.println("MENU DISPOSED FOR SWITCH (logo & frame)");
    }
}

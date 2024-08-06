package com.x1vyx.rocketgame.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundControl
{
    private static final int COIN_SOUND_LIMIT = 6;
    private static final int PAINT_SOUND_LIMIT = 3;
    private static Sound sndBtn, sndDeath;
    private static Sound[] sndCoin = new Sound[COIN_SOUND_LIMIT], sndPaint = new Sound[PAINT_SOUND_LIMIT];
    public static final int BTN = 0, COIN = 1, PAINT = 2, DEATH = 3;

    private static boolean loaded = false;

    public static void init()
    {
        sndBtn = Gdx.audio.newSound(Gdx.files.internal("wav/button.wav"));

        for (int i = 0; i < COIN_SOUND_LIMIT; i++)
        {
            sndCoin[i] = Gdx.audio.newSound(Gdx.files.internal("wav/coin-" + i + ".wav"));
        }
        for (int i = 0; i < PAINT_SOUND_LIMIT; i++)
        {
            sndPaint[i] = Gdx.audio.newSound(Gdx.files.internal("wav/paint-" + i + ".wav"));
        }

        sndDeath = Gdx.audio.newSound(Gdx.files.internal("wav/death.wav"));

        loaded = true;
    }

    public static void play(int id)
    {
        if(!RocketGame.muted && loaded)
            switch (id)
            {
                case (BTN):
                    sndBtn.play();
                    break;
                case (COIN):
                    sndCoin[Randomer.getInt(COIN_SOUND_LIMIT)].play();
                    break;
                case (PAINT):
                    sndPaint[Randomer.getInt(PAINT_SOUND_LIMIT)].play();
                    break;
                case (DEATH):
                    sndDeath.play();
                    break;
                default:
                    break;
            }

    }

    public static void dispose()
    {
        if (loaded)
        {
            sndBtn.dispose();
            sndDeath.dispose();

            for (int i = 0; i < COIN_SOUND_LIMIT; i++)
            {
                sndCoin[i].dispose();
            }

            for (int i = 0; i < PAINT_SOUND_LIMIT; i++)
            {
                sndPaint[i].dispose();
            }
        }
    }

}

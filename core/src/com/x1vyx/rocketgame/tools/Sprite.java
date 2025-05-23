package com.x1vyx.rocketgame.tools;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite
{
    // Colors
    public static final int[][] MONO = {
            { // GREY, DEFAULT
                    0x000000ff, // <- conture
                    0x202020ff, // <- rocket dark
                    0x404040ff, // <- rocket light
                    0xc0c0c0ff, // <- background
                    0xffffffff, // <- text
            },
            { // CHERRY, DONE
                    0x000000ff,
                    0x5e161aff,
                    0xa5262eff,
                    0x2f0b0dff,
                    0xf4878dff,
            },
            { // FRESH, DONE
                    0x05386bff,
                    0x379683ff,
                    0x8ee4afff,
                    0x5cdb95ff,
                    0xedf5e1ff,

            },
            { // BLUE, DONE
                    0x212e53ff,
                    0xce6a6bff,
                    0xebaca2ff,
                    0x4a919eff,
                    0xbed3c3ff,

            },
            { // NICE, DONE
                    0x146481ff,
                    0xFFAEAEff,
                    0x56BAECff,
                    0xB4D8E7ff,
                    0xFFF0AAff,
            },
            { // GUCCI LMAO, DONE
                    0xbd9302ff,
                    0x0a6a56ff,
                    0xa82b11ff,
                    0x204030ff,
                    0x000000ff,
            }, // https://www.colorcombos.com/combotester.html?
            // color0=443266&color1=C3C3E5&color2=8C489F&color3=F1F0FF&color4=633385&show_text=
            { // MORGENSHTERN, DONE
                    0x172540ff,
                    0x4177a1ff,
                    0x97b7d8ff,
                    0xfecd7dff,
                    0xb97637ff,
            }, // https://www.colorcombos.com/combotester.html?
            // color0=4B6580&color1=B7D0D4&color2=5A9CA1&color3=7C7F85&color4=F7AE4B&show_text=
            { // PASTA, DONE
                    0x1b1b1eff,
                    0xa8bcd1ff,
                    0xd8dbe2ff,
                    0x55a4b1ff,
                    0x373f52ff,
            }, // https://visme.co/blog/website-color-schemes/
            { // DREAM, DONE
                    0x3f6385ff,
                    0xe7a59aff,
                    0x87738cff,
                    0xffbc95ff,
                    0x20364aff,
            },
            { // WINTER, DONE
                    0x020b36ff,
                    0x230745ff,
                    0xe3e3ffff,
                    0xd6fffbff,
                    0xb3e3f4ff,
            },
            { // HALLOWEEN GHOST, SPECIAL, DONE
                    0x151515ff,
                    0xa6a6a6ff,
                    0xf6fafdff,
                    0xc0c3c6ff,
                    0xea5f21ff,
            }
    };

    /* DEV */
    public float x, y;
    public int w, h;

    public static final int SUPPORTED_COLORS = MONO.length;
    private static final int SUPPORTED_SHADES = MONO[0].length;
    // Active fullTextures: red, green or blue fullTextures that have been initialized by class color
    // Variables
    public static int activeColor = 3;
    private static Sprite unitext = new Sprite("monospace.png", 59, 0, 0);
    public static int letterW = unitext.w;
    // Collision
    // Texture and Color
    private int frame;
    private Texture[] fullTextures; // gry, red, grn, blu, bbl etc...; will be disposed after setting animation
    private Pixmap defaultGreyPixmap;
    private TextureRegion[][] colorFramesAnimation;
    private float frameTime, curFrameTime;
    private int pieces; // frameH = h
    public boolean pressed; // meaningful for buttons

    public Sprite(String path, int pieces, float x, float y)
    {
        /* Position */
        this.x = x;
        this.y = y;

        fullTextures = new Texture[SUPPORTED_COLORS];
        fullTextures[0] = new Texture("png/" + path);
        // init grey pixmap
        Texture textureToConsume = new Texture("png/" + path);
        textureToConsume.getTextureData().prepare();
        defaultGreyPixmap = textureToConsume.getTextureData().consumePixmap();
        textureToConsume.dispose();

        // Init dimensions
        // whole fullW & h
        int fullW = defaultGreyPixmap.getWidth();
        h = defaultGreyPixmap.getHeight();

        if (pieces != 0)
            w = fullW / pieces;
        else
            System.out.println("SPRITE ERR: / BY 0");

        /* Init active textures */
        // gry is already initialized as a texture
        Pixmap[] tmpColorMaps = new Pixmap[SUPPORTED_COLORS];
        for (int i = 0; i < tmpColorMaps.length; i++)
            tmpColorMaps[i] = new Pixmap(fullW, h, Pixmap.Format.RGBA8888);

        for (int row = 0; row < h; row++)
            for (int col = 0; col < fullW; col++)
                for (int c = 0; c < SUPPORTED_COLORS - 1; c++)
                    for (int s = 0; s < SUPPORTED_SHADES; s++)
                        if (defaultGreyPixmap.getPixel(col, row) == MONO[0][s])
                            tmpColorMaps[c].drawPixel(col, row, MONO[c + 1][s]);

        /* Create color Textures */
        for (int c = 0; c < fullTextures.length - 1; c++)
        {
            fullTextures[c + 1] = new Texture(tmpColorMaps[c]);
            tmpColorMaps[c].dispose();
        }

        /* Set Animation */
        if (pieces > 0)
        {
            this.pieces = pieces;
            colorFramesAnimation = new TextureRegion[SUPPORTED_COLORS][pieces];
            for (int i = 0; i < SUPPORTED_COLORS; i++)
            {
                colorFramesAnimation[i] = new TextureRegion[pieces];
                for (int j = 0; j < pieces; j++)
                    colorFramesAnimation[i][j] = new TextureRegion(fullTextures[i], j * w, 0, w, h); // active gry
            }
        }
    }


    public Sprite(int w, int h, float x, float y)  // manual Sprite, used e.g. by DistanceManager
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        defaultGreyPixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        fullTextures = new Texture[SUPPORTED_COLORS];
        fullTextures[0] = new Texture(defaultGreyPixmap);

        for (int c = 1; c < fullTextures.length; c++)
            for (int s = 0; s < SUPPORTED_SHADES; s++)
            {
                Pixmap tmp = new Pixmap(w, h, Pixmap.Format.RGBA8888);
                tmp.setColor(MONO[c][s]);
                tmp.fill();
                fullTextures[c] = new Texture(tmp);
            }

        // Set Animation
        colorFramesAnimation = new TextureRegion[SUPPORTED_COLORS][1];
        for (int i = 0; i < SUPPORTED_COLORS; i++)
        {
            colorFramesAnimation[i] = new TextureRegion[1];
            colorFramesAnimation[i][0] = new TextureRegion(fullTextures[i], 0, 0, w, h);
        }
    }

    public Sprite(TextureRegion[][] textureRegions)
    {
        colorFramesAnimation = textureRegions;
        w = colorFramesAnimation[0][0].getRegionWidth();
        h = colorFramesAnimation[0][0].getRegionHeight();
    }

    public Sprite(TextureRegion[][] textureRegions, float x, float y)
    {
        colorFramesAnimation = textureRegions;
        w = colorFramesAnimation[0][0].getRegionWidth();
        h = colorFramesAnimation[0][0].getRegionHeight();
    }

    public static void nextActiveColor()
    {
        if (activeColor < SUPPORTED_COLORS - 1)
            activeColor++;
        else
            activeColor = 0;
    }

    public static void prevActiveColor()
    {
        if (activeColor >= 1)
            activeColor--;
        else
            activeColor = SUPPORTED_COLORS - 1;
    }

    // Render Numbers from right to left
    public static void renderNumbers(SpriteBatch sb, int num, float x, float y, boolean plus)
    {
        if (plus)
            unitext.render(sb, 11, x - letterW, y);
        if (num == 0)
        {
            unitext.render(sb, 16, x, y);
            return;
        }

        int i = 0;
        while (num != 0)
        {
            unitext.render(sb, num % 10 + 16, x - i * unitext.w, y);
            num /= 10;
            i++;
        }
    }

    // Render Numbers (and z zeros in front) from right to left
    public static void renderNumbers(SpriteBatch sb, int num, float x, float y, int z)
    {
        for (int i = 0; i < z; i++)
        {
            if(num % 10 != 0)
                unitext.render(sb, num % 10 + 16, x - i * unitext.w, y); // render next digit
            else
                unitext.render(sb, 16, x - i * unitext.w, y); // render 0
            num /= 10;
        }

    }

    public static void renderText(SpriteBatch sb, String text, float x, float y)
    {
        for (int i = 0; i < text.length(); i++)
            unitext.render(sb, text.toUpperCase().codePointAt(i) - 32, x + i * unitext.w, y);

    }


    static void initUnitext()
    {
        unitext = new Sprite("monospace.png", 59, 0, 0);
    }

    static void disposeFont()
    {
        unitext.dispose();
    }

    public boolean isColliding(Sprite other)
    {
        float oX1 = other.x;
        float oY1 = other.y;
        float oX2 = other.x + other.w;
        float oY2 = other.y + other.h;
        return (contains(oX1, oY1) || contains(oX2, oY1) || contains(oX1, oY2) || contains(oX2, oY2));
    }

    public boolean isColliding(float oX, float oY, float oW, float oH)
    {
        float oX2 = oX + oW;
        float oY2 = oY + oH;
        return (contains(oX, oY) || contains(oX2, oY) || contains(oX, oY2) || contains(oX2, oY2));
    }

    boolean containsTouch(int pX, int pY)
    {
        if (pX > x && pX < (x + w) && pY > y && pY < (y + h))
        {
            if(pieces > 1)
                frame = 1;
            return true;
        }
        frame = 0;
        return false;
    }

    private boolean contains(float pX, float pY)
    {
        return (pX > x && pX < (x + w) && pY > y && pY < (y + h));
    }

    public boolean update(float dt) // sprite update is not necessary
    {
        curFrameTime += dt;
        if (curFrameTime > frameTime)
        {
            frame++;
            curFrameTime = 0;
        }
        if (frame >= pieces)
        {
            frame = 0;
        }
        return true;
    }

    public void render(SpriteBatch spriteBatch)
    {
        spriteBatch.draw(colorFramesAnimation[activeColor][frame], x, y);
    }

    public void render(SpriteBatch spriteBatch, int frame, float x, float y)
    {
        spriteBatch.draw(colorFramesAnimation[activeColor][frame], x, y);
    }

    public void render(SpriteBatch spriteBatch, float x, float y)
    {
        spriteBatch.draw(colorFramesAnimation[activeColor][frame], x, y);
    }

    public void render(SpriteBatch spriteBatch, int frame)
    {
        spriteBatch.draw(colorFramesAnimation[activeColor][frame], x, y);
    }

    public void render(SpriteBatch spriteBatch, int color, boolean sure)
    {
        if (sure)
            spriteBatch.draw(colorFramesAnimation[color][frame], x, y);
        else
            System.out.print("Sprite: Not sure??");
    }

    public void renderAsButton(SpriteBatch spriteBatch)
    {
        spriteBatch.draw(colorFramesAnimation[activeColor][pressed ? 1 : 0], x, y);
    }

    public void renderAsButton(SpriteBatch spriteBatch, boolean active)
    {
        spriteBatch.draw(colorFramesAnimation[activeColor][active ? 1 : 0], x, y);
    }

    public void dispose()
    {
        defaultGreyPixmap.dispose();
        for (Texture texture : fullTextures) texture.dispose();
    }

    public Sprite setCycleTime(float cycleTime)
    {
        frameTime = cycleTime / this.pieces;
        return this;
    }

    void setFrame(int frame)
    {
        this.frame = frame;
    }

}

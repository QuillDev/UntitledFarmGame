package tech.quilldev;

import com.badlogic.gdx.Gdx;

public class MathConstants {
    public static final int WORLD_UNIT = 16;

    //World Logic
    public static float ACCELERATION_RATE = 1f;
    public static final float TICKS_PER_SECOND = 120;
    public static float TICK_RATE = 1f / TICKS_PER_SECOND / ACCELERATION_RATE;

    public static float ACCUMULATOR = 0;
}

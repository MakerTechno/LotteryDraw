package io.github.makertechno.randoms;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class SimpleRandoms {
    public static int simpleRandom(@NotNull Random random, int from, int to){
        assert to > from;
        return random.nextInt(to - from) + from;
    }

    public static int getUnSameX6(int elementLength, int[] source, int stopPoint){
        int tmp = SimpleRandoms.simpleRandom(new Random(), 0, elementLength);
        boolean isRecorded = false;
        for (int i = 0; i < stopPoint; i++){
            if (source[i] == tmp){
                isRecorded = true;
                break;
            }
        }
        if (!isRecorded) return tmp;
        else return getUnSameX6(elementLength, source, stopPoint);
    }
}

package com.faire.makers.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Util class to generate random numbers and ranges.
 */
public class RandomGen {

    private static final int SEED = 23032019;
    private static Random random = new Random(SEED);

    /**
     * Gets a random integer in the interval min to max inclusive.
     *
     * @param min Minimal inclusive value.
     * @param max Maximum inclusive value.
     * @return A random integer in the range.
     */
    public static int getRandomInt(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }

    /**
     * Generate a list with random integers from min to max inclusive,
     * with no repetitions.
     * The total numbers in the interval [min, max] must be greater or equal to size.
     *
     * @param size Size of the list.
     * @param max The maximum value.
     * @param min The minimal value.
     * @return A list of random numbers.
     */
    public static int[] getRandomIntsNoRep(int size, int min, int max) {

        int count = max - min + 1;

        if(count < size){
            return null;
        }

        Integer[] possibleValues = new Integer[count];
        for(int i = 0; i < possibleValues.length; i++){
            possibleValues[i] = i + min;
        }

        List<Integer> list = Arrays.asList(possibleValues);
        Collections.shuffle(list);
        int[] result = new int[size];
        for(int i = 0; i < size; i++){
            result[i] = list.get(i);
        }

        return result;
    }
}

package Sorts;

import java.util.Random;

public class ArrayHandler {
    // Used to provide the same array to all sorting algorithms and to create randomized arrays with a specific length

    public static int LENGTH = 250;
    public static Integer[] current;

    // Creates a new shuffled array, utilizing the Fisher-Yates-Shuffle
    public static void getNew() {
        Integer[] array = new Integer[LENGTH];

        // Fills a new array with ascending elements ([1,2,3,4,...])
        for (int i = 1; i < LENGTH+1; i++) {
            array[i-1] = i;
        }

        // Shuffles every element to a random position
        for (int i = LENGTH-1; i > 0; i--) {
            int j = new Random().nextInt(i+1);

            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        current = array;
    }
}

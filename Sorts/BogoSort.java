package Sorts;

import java.util.Random;

public class BogoSort  extends Sort{

    public BogoSort() {
        super("Bogo");
    }

    @Override
    protected void sort() {
        boolean sorted;

        do {
            // Shuffles array randomly with the Fisher-Yates-Shuffle
            for (int i = getArray().length-1; i > 0; i--) {
                int j = new Random().nextInt(i+1);

                int temp = numbers[i];
                numbers[i] = numbers[j];
                numbers[j] = temp;
            }

            if (SortStateManager.stopSortingRequested) return;
            updateDiagram(numbers);

            // Checks, whether array is sorted
            sorted = true;

            for (int i = 1; i < getArray().length; i++) {
                if (numbers[i - 1] != i) {
                    sorted = false;
                    break;
                }

            }

        } while (!sorted && !SortStateManager.stopSortingRequested);
    }
}

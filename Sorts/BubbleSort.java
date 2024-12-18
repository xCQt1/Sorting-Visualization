package Sorts;

public class BubbleSort extends Sort {

    /*
        Der Bubblesort Algorithmus geht das zu sortierende Array durch und vertauscht nebeneinanderliegende Elemente, wenn das vordere größer ist als das hintere. Findet in einer Iteration kein tausch statt, ist das Array sortiert.

        Laufzeit: O(n^2)
     */

    public BubbleSort() {
        super("Bubble");
    }

    @Override
    protected void sort() {
        boolean switched;

        do {
            if (SortStateManager.stopSortingRequested) return;

            switched = false;
            for (int i = 0; i < numbers.length-1; i++) {
                if (numbers[i+1] < numbers[i]) {
                    int temp = numbers[i];
                    numbers[i] = numbers[i+1];
                    numbers[i+1] = temp;

                    switched = true;
                }
                if (SortStateManager.stopSortingRequested) return;
                updateDiagram(numbers);
            }
        } while (switched && !SortStateManager.stopSortingRequested);
    }
}

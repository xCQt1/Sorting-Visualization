package Sorts;

public class StalinSort extends Sort{

    public StalinSort() {
        super("Stalin");
    }

    @Override
    protected void sort() {
        int max = numbers[0];

        // Iterates over each element in numbers
        for (int i = 1; i < numbers.length; i++) {
            // If the current element is smaller than the largest element encountered before, it will be set to 0
            if (numbers[i] < max) {
                numbers[i] = 0;
            } else {
                max = numbers[i];
            }

            // Call to updateDiagram and check if sorting is to be stopped
            updateDiagram(numbers);
            if (SortStateManager.stopSortingRequested) return;
        }
    }
}

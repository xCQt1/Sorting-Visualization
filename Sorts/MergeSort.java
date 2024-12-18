package Sorts;

public class MergeSort extends Sort{

    public MergeSort() {
        super("Merge");
    }

    @Override
    protected void sort() {
        mergeSort(numbers, 0, numbers.length - 1);
    }

    private void mergeSort(Integer[] array, int left, int right) {
        if (SortStateManager.stopSortingRequested) return;
        if (left < right) {

            // Calculates the middle of the array
            int mid = (left + right) / 2;

            // Sorts left half
            mergeSort(array, left, mid);

            // Sorts right half
            mergeSort(array, mid + 1, right);

            // Merges both halves
            merge(array, left, mid, right);

            // Call to updateDiagram
            updateDiagram(array);
        }
    }

    private void merge(Integer[] array, int left, int mid, int right) {
        // Gets the size of both merging arrays
        int n1 = mid - left + 1;
        int n2 = right - mid;

        if (SortStateManager.stopSortingRequested) return;

        // Creates temporary arrays for the right and left array
        Integer[] leftArray = new Integer[n1];
        Integer[] rightArray = new Integer[n2];

        // Copies content of the arrays to the temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }


        int i = 0, j = 0;
        int k = left;

        // Merging both arrays in ascending order
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;

            // Call to updateDiagram
            updateDiagram(array);
            if (SortStateManager.stopSortingRequested) return;
        }

        // Copies leftover elements from leftArray to array
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;

            // Call to updateDiagram
            updateDiagram(array);
            if (SortStateManager.stopSortingRequested) return;
        }

        // Copies leftover elements from rightArray to array
        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;

            // Call to updateDiagram
            updateDiagram(array);
            if (SortStateManager.stopSortingRequested) return;
        }
    }
}

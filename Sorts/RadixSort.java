package Sorts;

public class RadixSort extends Sort{

    public RadixSort() {
        super("Radix");
    }

    @Override
    protected void sort() {
        // Gets the highest element in the array
        int max = ArrayHandler.LENGTH;

        for (int exp = 1; max / exp > 0; exp *= 10) {
            // Checks if sorting is to be stopped
            if (SortStateManager.stopSortingRequested) return;

            int n = numbers.length;
            Integer[] output = new Integer[n];  // Output array to store sorted numbers temporarily
            int[] count = new int[10];  // Counting array for the digits (0-9)

            // Count occurrences of each digit in the current place (units, tens, etc.)
            for (Integer number : numbers) {
                int digit = (number / exp) % 10;    // Extract the current digit
                count[digit]++;
            }

            // Update the count array to store positions of digits in the output array
            for (int i = 1; i < 10; i++) {
                count[i] += count[i - 1];
            }

            // Checks if sorting is to be stopped
            if (SortStateManager.stopSortingRequested) return;

            // Build the output array by placing numbers in their correct positions
            for (int i = n - 1; i >= 0; i--) {
                int digit = (numbers[i] / exp) % 10;    // Extract the current digit again
                output[count[digit] - 1] = numbers[i];  // Place the number in the output array
                count[digit]--;     // Decrease the count for this digit
            }

            // Copy the sorted numbers from the output array back to the original array
            for (int i = 0; i < n; i++) {
                numbers[i] = output[i];

                // Call to updateDiagram and check if sorting is to be stopped
                updateDiagram(numbers);
                if (SortStateManager.stopSortingRequested) return;
            }
        }

    }
}

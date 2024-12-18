package Sorts;

public class SortStateManager {
    // Used to check, if sorting algorithms are running and to stop them

    public static boolean isSorting;
    public static boolean stopSortingRequested;

    private static int sortingCounter = 0;  // Counter of currently sorting algorithms

    //
    public static void requestStop() {
        System.out.println("Stop requested");
        stopSortingRequested = true;
        updateStates();
    }

    // Increases sortingCounter
    public static void logOn() {
        sortingCounter++;
        updateStates();
    }

    // Decreases sortingCounter
    public static void logOff() {
        sortingCounter--;
        if (sortingCounter == 0) System.out.println("All sorts have ended");
        updateStates();
    }

    private static void updateStates() {
        isSorting = sortingCounter != 0;
        if (!isSorting) stopSortingRequested = false;

        if (sortingCounter < 0) throw new IllegalStateException("sortingCounter is negative");
    }

}

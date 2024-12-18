package Sorts;
import UI.*;

public abstract class Sort implements Runnable{
    public static String[] sorts = new String[] {"Merge Sort", "Radix Sort", "Bubble Sort", "Stalin Sort", "Bogo Sort"};

    // Returns an object according to the name
    public static Sort getSortFromName(String name) {
        return switch (name) {
            case "Merge Sort" -> new MergeSort();
            case "Radix Sort" -> new RadixSort();
            case "Bubble Sort" -> new BubbleSort();
            case "Stalin Sort" -> new StalinSort();
            case "Bogo Sort" -> new BogoSort();
            default -> null;
        };
    }

    public final String NAME;

    // This is the array to be sorted
    Integer[] numbers;

    public Graph parent;
    public boolean finished = false;

    // Contructor used when using the sort without a GUI
    public Sort(String name) {
        super();
        NAME = name;
        numbers = ArrayHandler.current.clone();
    }

    public Sort(String name, Graph graph) {
        this(name);
        setGraph(graph);
    }

    public void setGraph(Graph graph) {
        this.parent = graph;
    }

    // Method to be implemented by sorting classes inheriting from this one
    protected abstract void sort();

    // Creates a new thread to run the sorting and starts it
    public void startSorting() {
        new Thread(this){
            @Override
            public void run() {
                Sort.this.run();
            }
        }.start();
    }

    // Method that runs the sorting
    public void run() {

        delayCounterMicro = 0;  // variable for measuring the delays after updating the GUI

        // Quits, if the array is already sorted
        if (finished) {
            System.out.println("Quitted sorting, already sorted");
            return;
        }

        SortStateManager.logOn();
        long start = System.nanoTime();     // fetches the time, sorting was started at

        sort();

        long runtime = (System.nanoTime() - start); // calculates the runtime of the sorting
        SortStateManager.logOff();


        finished = true;
        long runtimeMicro =  runtime/1000;  // runtime from nano to micro secs

        System.out.println(NAME + " has finished, ran for " + (GUI.animationDelay? "an estimated ": "") +  (runtimeMicro - delayCounterMicro) + " µs");
        parent.setToolTipText((GUI.animationDelay? "Estimated ": "") +"Runtime: " + (runtimeMicro-delayCounterMicro) + " µs");  // Outputs the runtime via the graph's tooltip text
    }

    private long delayCounterMicro = 0; // used for counting time spent sleeping
    protected void updateDiagram(Integer[] array) {
        if (parent == null) return;     // returns if graph is not set

        long start = System.nanoTime();
        parent.drawDiagram(array);      // updates the diagram with the provided array

        try {
            if (GUI.animationDelay) {
                Thread.sleep(1);    // sleeps 1 milli sec for animation purposes if the delay is enabled (animationDelay == true)
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        delayCounterMicro += (System.nanoTime() - start) / 1000;    // increases delayCounter by time spent updating and sleeping (in order to provide at least a rough estimation of the runtime)
    }

    public Integer[] getArray() {
        return this.numbers.clone();
    }

    // Resets array to a randomized one
    public void resetArray() {
        numbers = ArrayHandler.current.clone();
        finished = false;
    }

}

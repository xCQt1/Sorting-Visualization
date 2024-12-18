package UI;

import Sorts.*;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Graph extends JPanel {
    Sort sort;  // The sorting algorithm used in this graph

    JButton closeButton;
    JComboBox<String> sortSelectBox;

    GraphContainer parent;

    // Constructor for a specified sort
    public Graph(GraphContainer gc, Sort sort) {    //
        this(gc);

        this.sort = sort;
        sort.setGraph(this);
    }

    // Constructor for default sort
    public Graph(GraphContainer gc) {
        super();

        parent = gc;

        // Sets up the close button
        closeButton = new JButton("X");
        closeButton.setPreferredSize(new Dimension(38,25));
        closeButton.addActionListener(e -> parent.removeGraph(Graph.this));
        closeButton.setVisible(true);

        // Sets up the dropdown menu to choose the used sort
        sortSelectBox = new JComboBox<>(Sort.sorts);
        sortSelectBox.setEditable(false);
        sortSelectBox.setPreferredSize(new Dimension(150, 24));
        sortSelectBox.setSelectedIndex(0);
        setSort(Sort.getSortFromName((String) sortSelectBox.getSelectedItem()));
        sortSelectBox.addActionListener(e -> setSort(Objects.requireNonNull(Sort.getSortFromName((String) sortSelectBox.getSelectedItem()))));

        // Adds both components
        add(sortSelectBox);
        add(closeButton);

        setMinimumSize(parent.MIN_GRAPH_SIZE);
        drawDiagram(sort.getArray().clone());
        setVisible(true);
        repaint();
    }

    //
    public void setSort(Sort sort) {
        if (SortStateManager.isSorting) {
            SortStateManager.requestStop();
        }

        parent.resetAll();

        this.sort = sort;
        resetGraph();
        sort.setGraph(this);
    }

    // Resets the graph to a new randomized array
    public void resetGraph() {
        sort.resetArray();
        arrayToPaint = ArrayHandler.current.clone();
        repaint();
    }

    // Starts sorting the array
    public void startSorting() {
        if (sort == null) throw new RuntimeException("Sort is not set");
        setToolTipText("");

        sort.startSorting();
    }

    // Method to be called when updating the diagram with an array of Integers
    private Integer[] arrayToPaint;
    public void drawDiagram(Integer[] array) {
        arrayToPaint = array.clone() ;
        repaint();
    }

    // Draws the previously specified array to this component
    static int PADDING = 10;
    @Override
    public void paintComponent(Graphics g) {
        int GRAPH_WIDTH, BAR_WIDTH;

        GRAPH_WIDTH = getWidth() - PADDING;
        BAR_WIDTH = (int) ((float) (GRAPH_WIDTH) / (float) arrayToPaint.length);

        // Determines the difference in pixels from drawn bars and available space
        int barGraphDiff = GRAPH_WIDTH - BAR_WIDTH*arrayToPaint.length;

        // Prepares background
        g.clearRect(0,0,getWidth(),getHeight());
        g.setColor(parent.getBackground());
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.DARK_GRAY);
        g.drawRoundRect(0,0,getWidth()-1,getHeight()-1,2*PADDING,2*PADDING);

        // Handles the case, that no arrayToPaint was specified before
        if (arrayToPaint == null) {
            g.drawRect(20,20,getWidth()-40, getHeight()-40);
            return;
        }

        // Multiplier to adjust the height of the bars to the height of this JPanel
        float heightMultipl = (float) (getHeight()-2*PADDING)/ (float) arrayToPaint.length;

        // draws the elements from arrayToPaint
        int i = 0;
        for (int x = PADDING; x < GRAPH_WIDTH; x += BAR_WIDTH) {    // Iterates over the coordinates of the

            if (i < barGraphDiff) { // if the current element's index is smaller than the pixel difference, it will be stretched by one pixel to fill the available space better
                if (arrayToPaint[i] != 0) g.fillRect(x, getHeight() - (int) (arrayToPaint[i]*heightMultipl + PADDING + 2), BAR_WIDTH+1, (int) (arrayToPaint[i]*heightMultipl + 2));
                x++;
            } else {
                if (arrayToPaint[i] != 0) g.fillRect(x, getHeight() - (int) (arrayToPaint[i]*heightMultipl + PADDING + 2), BAR_WIDTH, (int) (arrayToPaint[i]*heightMultipl + 2));
            }

            i++;
        }
    }
}

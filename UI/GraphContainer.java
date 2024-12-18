package UI;

import Sorts.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphContainer extends JPanel {

    final int MAX_GRAPH_COUNT;
    final Dimension MIN_GRAPH_SIZE = new Dimension(ArrayHandler.LENGTH + 2*Graph.PADDING,ArrayHandler.LENGTH + 2*Graph.PADDING);

    ArrayList<Graph> graphs = new ArrayList<>();    // stores the displayed graphs

    public GraphContainer() {
        super();

        setMinimumSize(new Dimension(200, 200));
        setLayout(new GridLayout(2,2,5,5));

        MAX_GRAPH_COUNT = 4;

        setVisible(true);
    }

    // Adds a new graph with a default sort
    public void addGraph() {
        if (SortStateManager.isSorting) return;

        if (graphs.size() + 1 > MAX_GRAPH_COUNT) {
            System.out.println("Refused new Graph (there can't be more than " + MAX_GRAPH_COUNT + ")");
            new JOptionPane().createDialog("Du kannst nicht mehr Algorithmen vergleichen.");
            return;
        }

        Graph g = new Graph(this);
        graphs.add(g);
        add(g);

        System.out.println("New generic graph added");

        if (graphs.getFirst().sort.finished) resetAll();

        revalidate();
        repaint();
    }

    // Adds a new graph with a specified sort
    public void addGraph(Sort sort) {

        addGraph();
        graphs.getLast().setSort(sort);

    }

    // Removes the graph g
    public void removeGraph(Graph g) {
        if (!graphs.isEmpty()) {
            remove(g);
            graphs.remove(g);

            System.out.println("Removed " + g.sort.NAME + " Sort");

            revalidate();
            repaint();
        }
    }

    // Starts sorting for all graphs
    public void startSorting() {
        graphs.forEach(Graph::startSorting);
    }

    // Resets all graphs to a new random array
    public void resetAll() {
        ArrayHandler.getNew();
        graphs.forEach(Graph::resetGraph);
    }
}

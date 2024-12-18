package UI;

import Sorts.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class GUI extends JFrame{

    public static boolean animationDelay;   // Whether to halt the sorting shortly after calling updateDiagram

    // Components of the JFrame
    JPanel main;
    private JPanel graph;
    private JButton startButton;
    private JButton resetButton;
    private JCheckBox delayToggle;
    private JButton addGraphButton;
    private GraphContainer graphContainer;

    public GUI() {

        super("Sorting Visualizer");


        // Adds a  graphController to the JPanel graph
        graphContainer = new GraphContainer();
        graph.setLayout(new GridLayout());
        graph.add(graphContainer);

        // Sets window options
        setMinimumSize(new Dimension(graphContainer.MIN_GRAPH_SIZE.width + 27, graphContainer.MIN_GRAPH_SIZE.height + 27));
        setPreferredSize(new Dimension(1000,700));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(main);

        setBackground(Color.DARK_GRAY);
        setResizable(true);
        setLocationRelativeTo(null);


        // Resets the array to sort
        ArrayHandler.getNew();

        // Enables the animation delay if the respective toggle is selected
        animationDelay = delayToggle.isSelected();

        // Updates the GUI with the new components
        pack();
        setVisible(true);
        repaint();

        // Assigns actionListeners to the addGraph, reset & start button and the delay toggle
        addGraphButton.addActionListener(l -> graphContainer.addGraph());

        resetButton.addActionListener(l -> {
            graphContainer.resetAll();
            SortStateManager.requestStop();
        });

        startButton.addActionListener(l -> {
            if (!SortStateManager.isSorting) graphContainer.startSorting();
        });

        delayToggle.addItemListener(e -> {
            if (SortStateManager.isSorting) {
                delayToggle.setSelected(animationDelay);
                return;
            }
            animationDelay = e.getStateChange() == ItemEvent.SELECTED;
            System.out.println("Toggled animation delay to " + animationDelay);
        });
    }
}

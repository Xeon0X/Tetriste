package controller;

import model.Grid;
import view.GridView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GridController extends JFrame implements Observer {
    static long lastTime = System.currentTimeMillis();
    private final Executor ex = Executors.newSingleThreadExecutor();
    private final JTextField textField = new JTextField("");
    private final JButton button = new JButton("do");
    private final Grid grid;
    private final Observer gridView;

    public GridController(Grid _grid) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        grid = _grid;

        setSize(350, 400);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textField, BorderLayout.NORTH);
        panel.add(button, BorderLayout.SOUTH);

        gridView = new GridView(grid);

        panel.add((JPanel) gridView, BorderLayout.CENTER);
        setContentPane(panel);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ex.execute(new Runnable() {
                    @Override
                    public void run() {
                        grid.action();
                        System.out.println("action");
                    }
                });
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        grid.action();
                }
            }
        });


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
                                       public void run() {
                                           Grid currentGrid = new Grid();
                                           GridController gridController = new GridController(currentGrid);
                                           gridController.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                           currentGrid.addObserver(gridController);
                                           gridController.setVisible(true);
                                       }
                                   }
        );
    }

    @Override
    public void update(Observable observable, Object arg) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gridView.update(observable, arg);
                textField.setText("Elapsed time : " + (System.currentTimeMillis() - lastTime) + "ms - x = " + grid.getActiveCell().getCoordinate().x + " y = " + grid.getActiveCell().getCoordinate().y);
                lastTime = System.currentTimeMillis();
            }
        });
    }
}

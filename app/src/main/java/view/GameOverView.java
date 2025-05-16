package view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameOverView extends JPanel {
    private final JLabel titleLabel;

    @Getter
    private final JButton homeButton;

    public GameOverView() {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        titleLabel = new JLabel("Game Over");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel homePanel = new JPanel(new GridLayout());
        homePanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        homeButton.setPreferredSize(new Dimension(100, 50));

        homePanel.add(homeButton);
        add(homePanel, BorderLayout.CENTER);
    }
}
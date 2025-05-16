package view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeView extends JPanel {

    private final JLabel titleLabel;
    @Getter
    private final JButton easyButton;
    @Getter
    private final JButton mediumButton;
    @Getter
    private final JButton hardButton;

    public HomeView() {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        titleLabel = new JLabel("Tetriste");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);


        JPanel difficultyPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        difficultyPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        easyButton = new JButton("Facile");
        easyButton.setFont(new Font("Arial", Font.PLAIN, 20));

        mediumButton = new JButton("Moyen");
        mediumButton.setFont(new Font("Arial", Font.PLAIN, 20));

        hardButton = new JButton("Difficile");
        hardButton.setFont(new Font("Arial", Font.PLAIN, 20));

        difficultyPanel.add(easyButton);
        difficultyPanel.add(mediumButton);
        difficultyPanel.add(hardButton);

        add(difficultyPanel, BorderLayout.CENTER);
    }

    public void setDifficultyActionListener(ActionListener listener) {
        easyButton.addActionListener(listener);
        mediumButton.addActionListener(listener);
        hardButton.addActionListener(listener);
    }

}
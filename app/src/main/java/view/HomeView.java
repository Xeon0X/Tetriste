package view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HomeView extends JPanel {
    private static final float BASE_TITLE_SIZE = 36f;
    private static final float BASE_BUTTON_SIZE = 20f;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

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
        titleLabel.setFont(new Font("Arial", Font.BOLD, (int) BASE_TITLE_SIZE));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);


        JPanel difficultyPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        difficultyPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        easyButton = new JButton("Facile");
        easyButton.setFont(new Font("Arial", Font.PLAIN, (int) BASE_BUTTON_SIZE));

        mediumButton = new JButton("Moyen");
        mediumButton.setFont(new Font("Arial", Font.PLAIN, (int) BASE_BUTTON_SIZE));

        hardButton = new JButton("Difficile");
        hardButton.setFont(new Font("Arial", Font.PLAIN, (int) BASE_BUTTON_SIZE));

        difficultyPanel.add(easyButton);
        difficultyPanel.add(mediumButton);
        difficultyPanel.add(hardButton);

        add(difficultyPanel, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateFonts();
            }
        });
    }

    private void updateFonts() {
        int width = getWidth();
        int height = getHeight();

        if (width <= 0 || height <= 0) return;

        float scaleFactor = Math.min((float) width / WIDTH, (float) height / HEIGHT);

        titleLabel.setFont(new Font("Arial", Font.BOLD,
                Math.max(12, Math.round(BASE_TITLE_SIZE * scaleFactor))));

        int buttonFontSize = Math.max(12, Math.round(BASE_BUTTON_SIZE * scaleFactor));

        easyButton.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        mediumButton.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        hardButton.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));

        revalidate();
    }

    public void setDifficultyActionListener(ActionListener listener) {
        easyButton.addActionListener(listener);
        mediumButton.addActionListener(listener);
        hardButton.addActionListener(listener);
    }
}
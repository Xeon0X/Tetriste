package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HomeView extends JFrame {
    private static final float BASE_TITLE_SIZE = 36f;
    private static final float BASE_BUTTON_SIZE = 20f;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JLabel titleLabel;

    public HomeView() {
        setTitle("Tetriste");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        setupUI();
    }

    private void setupUI() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        titleLabel = new JLabel("Tetriste");
        titleLabel.setFont(new Font("Arial", Font.BOLD, (int) BASE_TITLE_SIZE));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        easyButton = new JButton("Easy");
        mediumButton = new JButton("Medium");
        hardButton = new JButton("Hard");

        panel.add(easyButton);
        panel.add(mediumButton);
        panel.add(hardButton);

        add(panel, BorderLayout.CENTER);

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

    public void display() {
        setVisible(true);
    }

    public void close() {
        dispose();
    }
}
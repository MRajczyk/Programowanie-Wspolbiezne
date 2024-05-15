package pl.dmcs;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    JFrame frame;
    JPanel mainPanel;
    JPanel threadsPoolPanel;
    JPanel userTablePanel;
    JPanel buttonsPanel;
    JPanel buttonsGroup;
    JButton startButton;
    JButton stopButton;
    JButton addUsersButton;

    public void setupWindow() {
        frame = new JFrame("Programowanie Współbieżne");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new GridBagLayout());

        threadsPoolPanel = new JPanel(new GridBagLayout());
        userTablePanel = new JPanel(new GridLayout(0, 5, 10, 0));
        buttonsPanel = new JPanel(new GridBagLayout());

        // THREAD POOL
        threadsPoolPanel.setBorder(BorderFactory.createTitledBorder("Thread pool"));
        mainPanel.add(threadsPoolPanel, new GridBagConstraints(0, 0, 1, 1, 0.8, 0.3, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        // USER TABLE
        userTablePanel.setBorder(BorderFactory.createTitledBorder("User Table"));
        mainPanel.add(userTablePanel, new GridBagConstraints(0, 1, 1, 1, 0.8, 0.7, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        //BUTTONS
        buttonsGroup = new JPanel(new GridLayout(3, 0, 0, 10));

        Dimension buttonSize = new Dimension(100, 40);
        startButton = new JButton("Start");
        startButton.setPreferredSize(buttonSize);
        stopButton = new JButton("Stop");
        stopButton.setPreferredSize(buttonSize);
        addUsersButton = new JButton("Add Users");
        addUsersButton.setPreferredSize(buttonSize);

        buttonsGroup.add(startButton);
        buttonsGroup.add(stopButton);
        buttonsGroup.add(addUsersButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonsPanel.add(buttonsGroup, gbc);

        buttonsPanel.setBorder(BorderFactory.createTitledBorder("Buttons"));
        mainPanel.add(buttonsPanel, new GridBagConstraints(1, 0, 1, 2, 0.2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        frame.add(mainPanel);
        frame.setSize(new Dimension(960, 660));
        frame.setVisible(true);
    }
}

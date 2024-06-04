package pl.dmcs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MainWindow {

    public volatile boolean  programRunning = false;

    JFrame frame;
    JPanel mainPanel;
    JPanel threadsPoolPanel;
    JPanel threadsGroup;
    JTable queueTable;
    JPanel queueTablePanel;
    JPanel buttonsPanel;
    JPanel buttonsGroup;
    JButton startButton;
    JButton stopButton;
    JButton addUsersButton;

    JPanel thread1Container;
    JLabel thread1Id;
    JLabel thread1fileSize;
    JLabel thread1secondsInQueue;
    JLabel thread1Progress;
    JPanel thread2Container;
    JLabel thread2Id;
    JLabel thread2fileSize;
    JLabel thread2secondsInQueue;
    JLabel thread2Progress;
    JPanel thread3Container;
    JLabel thread3Id;
    JLabel thread3fileSize;
    JLabel thread3secondsInQueue;
    JLabel thread3Progress;
    JPanel thread4Container;
    JLabel thread4Id;
    JLabel thread4fileSize;
    JLabel thread4secondsInQueue;
    JLabel thread4Progress;
    JPanel thread5Container;
    JLabel thread5Id;
    JLabel thread5fileSize;
    JLabel thread5secondsInQueue;
    JLabel thread5Progress;

    JScrollPane tableScrollPane;
    DefaultTableModel tableModel;

    public void setupWindow() {
        frame = new JFrame("Programowanie Współbieżne");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new GridBagLayout());

        threadsPoolPanel = new JPanel(new GridBagLayout());
        queueTablePanel = new JPanel(new GridLayout(1, 1, 0, 10));
        buttonsPanel = new JPanel(new GridBagLayout());

            // THREAD POOL
        threadsGroup = new JPanel(new GridLayout(0, 5, 0, 10));

            //THREAD 1
        thread1Container = new JPanel(new GridLayout(4, 1, 0, 10));
        thread1Container.setBorder(BorderFactory.createTitledBorder("Thread 1"));
        thread1Id = new JLabel("User Id #");
        thread1fileSize = new JLabel("File Size #");
        thread1secondsInQueue = new JLabel("Seconds in queue: #");
        thread1Progress = new JLabel("Progress #%");
        thread1Container.add(thread1Id);
        thread1Container.add(thread1fileSize);
        thread1Container.add(thread1secondsInQueue);
        thread1Container.add(thread1Progress);
        threadsGroup.add(thread1Container);

            //THREAD 2
        thread2Container = new JPanel(new GridLayout(4, 1, 0, 10));
        thread2Container.setBorder(BorderFactory.createTitledBorder("Thread 2"));
        thread2Id = new JLabel("User Id #");
        thread2fileSize = new JLabel("File Size #");
        thread2secondsInQueue = new JLabel("Seconds in queue: #");
        thread2Progress = new JLabel("Progress #%");
        thread2Container.add(thread2Id);
        thread2Container.add(thread2fileSize);
        thread2Container.add(thread2secondsInQueue);
        thread2Container.add(thread2Progress);
        threadsGroup.add(thread2Container);

            //THREAD 3
        thread3Container = new JPanel(new GridLayout(4, 1, 0, 10));
        thread3Container.setBorder(BorderFactory.createTitledBorder("Thread 3"));
        thread3Id = new JLabel("User Id #");
        thread3fileSize = new JLabel("File Size #");
        thread3secondsInQueue = new JLabel("Seconds in queue: #");
        thread3Progress = new JLabel("Progress #%");
        thread3Container.add(thread3Id);
        thread3Container.add(thread3fileSize);
        thread3Container.add(thread3secondsInQueue);
        thread3Container.add(thread3Progress);
        threadsGroup.add(thread3Container);

            //THREAD 4
        thread4Container = new JPanel(new GridLayout(4, 1, 1, 10));
        thread4Container.setBorder(BorderFactory.createTitledBorder("Thread 4"));
        thread4Id = new JLabel("User Id #");
        thread4fileSize = new JLabel("File Size #");
        thread4secondsInQueue = new JLabel("Seconds in queue: #");
        thread4Progress = new JLabel("Progress #%");
        thread4Container.add(thread4Id);
        thread4Container.add(thread4fileSize);
        thread4Container.add(thread4secondsInQueue);
        thread4Container.add(thread4Progress);
        threadsGroup.add(thread4Container);

            //THREAD 5
        thread5Container = new JPanel(new GridLayout(4, 1, 0, 10));
        thread5Container.setBorder(BorderFactory.createTitledBorder("Thread 5"));
        thread5Id = new JLabel("User Id #");
        thread5fileSize = new JLabel("File Size #");
        thread5secondsInQueue = new JLabel("Seconds in queue: #");
        thread5Progress = new JLabel("Progress #%");
        thread5Container.add(thread5Id);
        thread5Container.add(thread5fileSize);
        thread5Container.add(thread5secondsInQueue);
        thread5Container.add(thread5Progress);
        threadsGroup.add(thread5Container);

        threadsPoolPanel.add(threadsGroup);
        threadsPoolPanel.setBorder(BorderFactory.createTitledBorder("Thread pool"));
        mainPanel.add(threadsPoolPanel, new GridBagConstraints(0, 0, 1, 1, 0.8, 0.3, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        // QUEUE TABLE
        Object[][] initialData = {{}};

        Object[] columns = {"Id", "Files", "Queue enter time"};
        tableModel = new DefaultTableModel(initialData, columns);
        queueTable = new JTable(tableModel);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        queueTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        queueTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        queueTable.getColumnModel().getColumn(0).setMaxWidth(40);
        queueTable.getColumnModel().getColumn(1).setMinWidth(330);
        tableScrollPane = new JScrollPane(queueTable);
        queueTablePanel.add(tableScrollPane);

        queueTablePanel.setBorder(BorderFactory.createTitledBorder("Queue"));
        mainPanel.add(queueTablePanel, new GridBagConstraints(0, 1, 1, 1, 0.8, 0.7, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        //BUTTONS
        buttonsGroup = new JPanel(new GridLayout(3, 0, 0, 10));

        Dimension buttonSize = new Dimension(100, 40);
        startButton = new JButton("Start");
        startButton.setPreferredSize(buttonSize);
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
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

        // EVENT HANDLERS
        startButton.addActionListener(e -> {
            this.programRunning = true;
            this.startButton.setEnabled(false);
            this.stopButton.setEnabled(true);
        });

        stopButton.addActionListener(e -> {
            this.programRunning = false;
            this.stopButton.setEnabled(false);
            this.startButton.setEnabled(true);
        });
    }
}

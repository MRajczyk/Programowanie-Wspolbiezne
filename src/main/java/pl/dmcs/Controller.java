package pl.dmcs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.Vector;
import java.time.Duration;

public class Controller {

    private static final Object mutex = new Object();
    MainWindow mainWindow;
    ArrayList<User> users;
    SecureRandom secureRandom;
    int lastId;

    public static String formatWithDots(long number) {
        String reversed = new StringBuilder(Long.toString(number)).reverse().toString();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            if (i > 0 && i % 3 == 0) {
                sb.append('.');
            }
            sb.append(reversed.charAt(i));
        }

        return sb.reverse().toString();
    }

    public Controller(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.secureRandom = new SecureRandom();
        this.users = new java.util.ArrayList<User>();
        this.lastId = 1;

        this.mainWindow.addUsersButton.addActionListener(e -> {
//            for(int i = 0; i < Main.NUMBER_OF_THREADS; ++i) {
                addNewUsersToQueue();
//            }

            redrawQueueTable();
        });
    }

    private void addNewUsersToQueue() {
        Vector<UserFile> generatedUserFiles = new Vector<UserFile>();
        int filesToBeGenerated = this.secureRandom.nextInt(Main.MAX_NUMBER_OF_FILES_GENERATED) + 1;
        for(int i = 0; i < filesToBeGenerated; ++i) {
            generatedUserFiles.add(new UserFile(getRandomFileSize()));
        }
        generatedUserFiles.sort((o1, o2) -> {
            if (o1.getFileSize() == o2.getFileSize()) {
                return 0;
            }

            return o1.getFileSize() < o2.getFileSize() ? -1 : 1;
        });
        synchronized (mutex) {
            users.add(new User(this.lastId, generatedUserFiles, LocalDateTime.now()));
            this.lastId++;
        }
    }

    private long getRandomFileSize() {
        return this.secureRandom.nextLong(Main.MAX_FILE_SIZE) + 1;
    }

    private void redrawQueueTable() {
        while (mainWindow.tableModel.getRowCount() > 0) {
            mainWindow.tableModel.removeRow(0);
        }

        for (User user : users) {
            StringBuilder stringBuilder = new StringBuilder();

            boolean first = true;
            for(UserFile file: user.getUserFiles()) {
                if(first) {
                    first = false;
                    stringBuilder.append(formatWithDots(file.getFileSize()));
                }
                else {
                    stringBuilder.append(", ").append(formatWithDots(file.getFileSize()));
                }
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            Object[] tempUserFields = {user.getId(), stringBuilder.toString(), user.getTimeOfJoin().format(formatter)};
            mainWindow.tableModel.addRow(tempUserFields);
        }
    }

    public synchronized void setThreadLabels(String userId, String fileSize, String threadId, String progress, String secondsInQueue) {
        switch (threadId) {
            case "1" -> {
                mainWindow.thread1Id.setText("User Id " + userId);
                mainWindow.thread1fileSize.setText("File size " + fileSize);
                mainWindow.thread1secondsInQueue.setText("Seconds in queue: " + secondsInQueue);
                mainWindow.thread1Progress.setText("Progress: " + progress);
            }
            case "2" -> {
                mainWindow.thread2Id.setText("User Id " + userId);
                mainWindow.thread2fileSize.setText("File size " + fileSize);
                mainWindow.thread2secondsInQueue.setText("Seconds in queue: " + secondsInQueue);
                mainWindow.thread2Progress.setText("Progress: " + progress);
            }
            case "3" -> {
                mainWindow.thread3Id.setText("User Id " + userId);
                mainWindow.thread3fileSize.setText("File size " + fileSize);
                mainWindow.thread3secondsInQueue.setText("Seconds in queue: " + secondsInQueue);
                mainWindow.thread3Progress.setText("Progress: " + progress);
            }
            case "4" -> {
                mainWindow.thread4Id.setText("User Id " + userId);
                mainWindow.thread4fileSize.setText("File size " + fileSize);
                mainWindow.thread4secondsInQueue.setText("Seconds in queue: " + secondsInQueue);
                mainWindow.thread4Progress.setText("Progress: " + progress);
            }
            case "5" -> {
                mainWindow.thread5Id.setText("User Id " + userId);
                mainWindow.thread5fileSize.setText("File size " + fileSize);
                mainWindow.thread5secondsInQueue.setText("Seconds in queue: " + secondsInQueue);
                mainWindow.thread5Progress.setText("Progress: " + progress);
            }
        }
    }

    public synchronized void setThreadProgress(String threadId, String progress) {
        switch (threadId) {
            case "1" -> mainWindow.thread1Progress.setText("Progress: " + progress);
            case "2" -> mainWindow.thread2Progress.setText("Progress: " + progress);
            case "3" -> mainWindow.thread3Progress.setText("Progress: " + progress);
            case "4" -> mainWindow.thread4Progress.setText("Progress: " + progress);
            case "5" -> mainWindow.thread5Progress.setText("Progress: " + progress);
        }
    }

    //function formula y = t^(1/x) + x/s
    //t = time, x = number of users in queue, s = file size
    public synchronized HighestScoreUserAndFile getHighestScoreUserAndFile() {
        LocalDateTime timeNow = LocalDateTime.now();
        double maxQueueFunctionValue = -1D;
        User nextInQueueUser = null;

        synchronized (mutex) {
            for (User user : users) {
                long filesize = user.getNextUserFileSizeInQueue();
                int usersInQueue = users.size();
                long secondsSinceJoin = Duration.between(user.getTimeOfJoin(), timeNow).toSeconds();

                double functionResult = Math.pow(secondsSinceJoin, 1D / usersInQueue) + (double)usersInQueue / filesize;
                if(functionResult > maxQueueFunctionValue) {
                    maxQueueFunctionValue = functionResult;
                    nextInQueueUser = user;
                }
            }
        }

        if(nextInQueueUser != null) {
            HighestScoreUserAndFile returnValue = new HighestScoreUserAndFile(nextInQueueUser.getId(), nextInQueueUser.popNextUserFile(), Duration.between(nextInQueueUser.getTimeOfJoin(), timeNow).toSeconds());
            if(nextInQueueUser.getUserFiles().isEmpty()) {
                users.remove(nextInQueueUser);
            }
            synchronized (mutex) {
                redrawQueueTable();
            }
            return returnValue;
        }

        return null;
    }
}

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

    public static String formatWithUnderscores(long number) {
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
            for(int i = 0; i < Main.NUMBER_OF_THREADS; ++i) {
                addNewUsersToQueue();
            }

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
                    stringBuilder.append(formatWithUnderscores(file.getFileSize()));
                }
                else {
                    stringBuilder.append(", ").append(formatWithUnderscores(file.getFileSize()));
                }
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            Object[] tempUserFields = {user.getId(), stringBuilder.toString(), user.getTimeOfJoin().format(formatter)};
            mainWindow.tableModel.addRow(tempUserFields);
        }
    }

    //function formula y = t^(1/x) + x/s
    //t = time, x = number of users in queue, s = file size
    public synchronized HighestScoreUserAndFile getHighestScoreUser() {
        double maxQueueFunctionValue = -1D;
        User nextInQueueUser = null;

        synchronized (mutex) {
            for (User user : users) {
                long filesize = user.getNextUserFileSizeInQueue();
                int usersInQueue = users.size();
                long secondsSinceJoin = Duration.between(LocalDateTime.now(), user.getTimeOfJoin()).toSeconds();

                double functionResult = Math.pow(secondsSinceJoin, 1D / usersInQueue) + (double)usersInQueue / filesize;
                if(functionResult > maxQueueFunctionValue) {
                    maxQueueFunctionValue = functionResult;
                    nextInQueueUser = user;
                }
            }
        }

        if(nextInQueueUser != null) {
            return new HighestScoreUserAndFile(nextInQueueUser.getId(), nextInQueueUser.popNextUserFile());
        }

        return null;
    }
}

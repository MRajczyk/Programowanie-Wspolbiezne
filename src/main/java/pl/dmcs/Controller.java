package pl.dmcs;

import java.util.ArrayList;
import java.security.SecureRandom;

public class Controller {
    MainWindow mainWindow;
    ArrayList<User> users;
    SecureRandom secureRandom;
    Long lastId;

    public Controller(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.secureRandom = new SecureRandom();
        this.users = new java.util.ArrayList<User>();
        this.lastId = 0L;

        this.mainWindow.addUsersButton.addActionListener(e -> {
            for(int i = 0; i < Main.NUMBER_OF_THREADS; ++i) {
                addUserToQueue();
            }

            redrawQueueTable();
        });
    }

    private void addUserToQueue() {
        ArrayList<UserFile> generatedUserFiles = new ArrayList<UserFile>();

        for(int i = 0; i < Main.NUMBER_OF_FILES_GENERATED; ++i) {
            generatedUserFiles.add(new UserFile(getRandomFileSize()));
        }
        users.add(new User(this.lastId.toString(), generatedUserFiles));
        this.lastId++;
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
                    stringBuilder.append(file.getFileSize());
                }
                else {
                    stringBuilder.append(", ").append(file.getFileSize());
                }
            }

            Object[] tempUserFields = {user.getId(), stringBuilder.toString(), "time of join"};
            mainWindow.tableModel.addRow(tempUserFields);
        }
    }
}

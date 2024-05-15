package pl.dmcs;

import java.util.ArrayList;

public class User {
    private final String id;
    private final ArrayList<UserFile> userFiles;

    public User(String id, ArrayList<UserFile> userFiles) {
        this.id = id;
        this.userFiles = userFiles;
    }

    public String getId() {
        return id;
    }

    public ArrayList<UserFile> getUserFiles() {
        return userFiles;
    }

    public UserFile getNextUserFileInQueue() {
        if(userFiles.isEmpty()) {
            return null;
        }

        return userFiles.removeLast();
    }
}

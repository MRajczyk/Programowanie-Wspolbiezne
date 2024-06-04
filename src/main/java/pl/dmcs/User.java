package pl.dmcs;

import java.time.LocalDateTime;
import java.util.Vector;

public class User {
    private final int id;
    private final Vector<UserFile> userFiles;

    private final LocalDateTime timeOfJoin;

    public User(int id, Vector<UserFile> userFiles, LocalDateTime timeOfJoin) {
        this.id = id;
        this.userFiles = userFiles;
        this.timeOfJoin = timeOfJoin;
    }

    public int getId() {
        return id;
    }

    public Vector<UserFile> getUserFiles() {
        return userFiles;
    }

    public LocalDateTime getTimeOfJoin() {
        return timeOfJoin;
    }

    public long getNextUserFileSizeInQueue() {
        if(userFiles.isEmpty()) {
            return -1;
        }

        return userFiles.firstElement().getFileSize();
    }

    public UserFile popNextUserFile() {
        if(userFiles.isEmpty()) {
            return null;
        }

        return userFiles.removeFirst();
    }
}

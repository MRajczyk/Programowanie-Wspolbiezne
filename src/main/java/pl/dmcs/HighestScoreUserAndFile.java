package pl.dmcs;

public class HighestScoreUserAndFile {
    private int userId;
    private UserFile userFile;
    private long secondsInQueue;

    public HighestScoreUserAndFile(int userId, UserFile userFile, long secondsInQueue) {
        this.userId = userId;
        this.userFile = userFile;
        this.secondsInQueue = secondsInQueue;
    }

    public int getUserId() {
        return userId;
    }

    public UserFile getUserFile() {
        return userFile;
    }

    public long getSecondsInQueue() {
        return this.secondsInQueue;
    }
}

package pl.dmcs;

public class HighestScoreUserAndFile {
    private int userId;
    private UserFile userFile;

    public HighestScoreUserAndFile(int userId, UserFile userFile) {
        this.userId = userId;
        this.userFile = userFile;
    }

    public int getUser() {
        return userId;
    }

    public UserFile getUserFile() {
        return userFile;
    }
}

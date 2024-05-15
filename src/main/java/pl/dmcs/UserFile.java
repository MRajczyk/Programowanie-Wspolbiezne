package pl.dmcs;

public class UserFile {
    private final long fileSize;

    public UserFile(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getFileSize() {
        return fileSize;
    }
}

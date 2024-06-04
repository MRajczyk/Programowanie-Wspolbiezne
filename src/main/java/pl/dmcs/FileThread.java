package pl.dmcs;

public class FileThread implements Runnable {

    private final Controller controller;
    private final int threadId;

    public FileThread(Controller controller, int threadId) {
        this.controller = controller;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadId + " started");
        while(true) {
            if(controller.mainWindow.programRunning) {
                HighestScoreUserAndFile highestScoreUserAndFile = controller.getHighestScoreUserAndFile();
                if(highestScoreUserAndFile == null) {
                    continue;
                }
                controller.setThreadLabels(Integer.toString(highestScoreUserAndFile.getUserId()), Controller.formatWithDots(highestScoreUserAndFile.getUserFile().getFileSize()), Integer.toString(threadId), "0%", Long.toString(highestScoreUserAndFile.getSecondsInQueue()));
                //map all file sizes to max 20 seconds per file
                // formula - filesize / maxfilesize * 20 seconds * 1000 for miliseconds
                try {
                    double sleepMilisDuration = ((double)highestScoreUserAndFile.getUserFile().getFileSize() / Main.MAX_FILE_SIZE) * 20 * 1000;
                    //update progress every second
                    int ctr = 1;
                    while(ctr * 1000 < sleepMilisDuration) {
                        if(!controller.mainWindow.programRunning) {
                            continue;
                        }
                        Thread.sleep(1000);
                        controller.setThreadProgress(Integer.toString(threadId), String.format("%.2f", ctr * 1000D / sleepMilisDuration * 100) + "%");
                        ++ctr;
                    }
                    Thread.sleep((int) sleepMilisDuration - (ctr - 1) * 1000L);
                    controller.setThreadProgress(Integer.toString(threadId), "100%");

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                controller.setThreadLabels("#", "#", Integer.toString(threadId), "-%", "-");
            }
        }
    }
}

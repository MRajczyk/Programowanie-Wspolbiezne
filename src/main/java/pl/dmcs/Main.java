package pl.dmcs;

public class Main {

    public static int NUMBER_OF_THREADS = 5;
    public static int MAX_NUMBER_OF_FILES_GENERATED = 5;
    public static long MAX_FILE_SIZE = 100;

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                //
            }
        });
        window.setupWindow();

        Controller controller = new Controller(window);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Thread thread = new Thread(new FileThread(controller, i + 1));
            thread.start();
        }
    }
}
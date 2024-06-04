package pl.dmcs;

public class Main {

    public static int NUMBER_OF_THREADS = 5;
    public static int MAX_NUMBER_OF_FILES_GENERATED = 1;
    public static long MAX_FILE_SIZE = 100; //100GB

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setupWindow();

        Controller controller = new Controller(window);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            new Thread(new FileThread(controller, i + 1)).start();
        }
    }
}
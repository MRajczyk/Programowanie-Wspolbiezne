package pl.dmcs;

public class Main {

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setupWindow();

        MainThread thread = new MainThread(window);
        thread.run();
    }
}
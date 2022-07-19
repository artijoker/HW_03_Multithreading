
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadFillsFile extends Thread {
    private final String _path;
    private final String _fileName;
    private final int _numbersAmount;

    public String GetFile() {
        return _path + _fileName;
    }

    public ThreadFillsFile(String path, String fileName, int numbersAmount) {

        _path = path;
        _fileName = fileName;
        _numbersAmount = numbersAmount;
    }

    public ThreadFillsFile(int numbersAmount) {

        _path = ".\\";
        _fileName = "data.txt";
        _numbersAmount = numbersAmount;
    }

    @Override
    public void run() {
        var generator = new Random();
        try (var writer = new FileWriter(_path + _fileName, false)) {
            for (int i = 0; i < _numbersAmount; i++) {
                if (isInterrupted()) {
                    return;
                }
                if (App.token) {
                    Thread.currentThread().interrupt();
                    return;
                }
                writer.write(generator.nextInt(10, 100) + " ");
            }
            writer.flush();
        } catch (IOException ex) {
            App.token = true;
        }
        var file = new File(_path, _fileName);
        System.out.println("Data file successfully created");
        System.out.println("Path to data file: " + file.getAbsolutePath());
    }
}

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ThreadFindsPrimeNumbersInFile extends Thread {
    private final String _dataFile;
    private final String _resultFile;

    public ThreadFindsPrimeNumbersInFile(String dataFile, String resultFile) {
        _dataFile = dataFile;
        _resultFile = resultFile;
    }


    @Override
    public void run() {
        Path pathToFile = Paths.get(_dataFile);
        Scanner scanner;
        try {
            scanner = new Scanner(pathToFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder builder = new StringBuilder();
        scanner.useDelimiter(System.getProperty("line.separator"));
        while(scanner.hasNext()){
            builder.append(scanner.next());
        }
        scanner.close();
        var numbers = Arrays.stream(builder.toString()
                .split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        //System.out.println(Arrays.toString(numbers));
        List<Integer> result = new ArrayList<>();

        boolean isPrimeNumber;
        for (int number :numbers) {
            isPrimeNumber = true;

            for (int i = 2; i <= number / 2; i++) {
                if ((number % i) == 0) {
                    isPrimeNumber = false;
                    break;
                }
            }

            if (isPrimeNumber)
                result.add(number);
        }

        synchronized (App.locker){
            try(var writer = new FileWriter(_resultFile, App.isAppend))
            {
                if (isInterrupted()){
                    return;
                }
                if (App.token) {
                    Thread.currentThread().interrupt();
                    return;
                }

                writer.write("Простые числа: " + result + "\n");
                writer.flush();

                App.isAppend = true;
            }
            catch(IOException ex){
                App.token = true;
            }
        }
    }
}


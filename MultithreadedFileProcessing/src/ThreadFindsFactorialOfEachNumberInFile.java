import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ThreadFindsFactorialOfEachNumberInFile extends Thread {
    private final String _dataFile;
    private final String _resultFile;

    public ThreadFindsFactorialOfEachNumberInFile(String dataFile, String resultFile) {
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
        while (scanner.hasNext()) {
            builder.append(scanner.next());
        }
        scanner.close();
        var numbers = Arrays.stream(builder.toString()
                        .split(" "))
                .mapToLong(Long::parseLong)
                .toArray();

        //System.out.println(Arrays.toString(numbers));
        List<BigInteger> result = new ArrayList<>();
        BigInteger factorial;
        for (long number : numbers) {

            factorial = BigInteger.valueOf(1);
            for (int i = 1; i <= number; i++) {
                factorial = factorial.multiply(BigInteger.valueOf(i));
            }
            result.add(factorial);
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

                writer.write("Факториал каждого числа: " + result +"\n");
                writer.flush();

                App.isAppend = true;
            }
            catch(IOException ex){
                App.token = true;
            }
        }
    }
}



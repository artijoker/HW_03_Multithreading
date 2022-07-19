import java.io.*;

public class App {

    public static final Object locker = new Object();
    public volatile static boolean token = false;
    public static boolean isAppend = false;

    public static void main(String[] args) {
        try {

            ThreadFillsFile fillsFile = new ThreadFillsFile(15);

            fillsFile.start();
            fillsFile.join();

            String dataFile = fillsFile.GetFile();
            String fileName = "result.txt";
            String pathToSave = ".\\";
            var findsPrimeNumbersInFile = new ThreadFindsPrimeNumbersInFile(
                    dataFile,
                    pathToSave + fileName
            );
            var findsFactorialOfEachNumberInFile = new ThreadFindsFactorialOfEachNumberInFile(
                    dataFile,
                    pathToSave + fileName
            );

            findsPrimeNumbersInFile.start();
            findsFactorialOfEachNumberInFile.start();

            findsPrimeNumbersInFile.join();
            findsFactorialOfEachNumberInFile.join();

            var file = new File(pathToSave, fileName);

            System.out.println("Data file processed successfully");
            System.out.println("Path to result file: " + file.getAbsolutePath());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            token = true;
            System.out.println(e.getMessage());
        }

    }

}

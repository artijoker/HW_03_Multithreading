
public class App {

    public volatile static boolean token = false;

    public static void main(String[] args){
        try {
            var createArray = new ThreadCreatingArray(15);
            createArray.start();
            createArray.join();
            var numbers = createArray.GetNumbers();

            var findsSum = new ThreadFindsSum(numbers);
            var findsPrimeNumbers = new ThreadFindsPrimeNumbers(numbers);

            findsSum.start();
            findsPrimeNumbers.start();

            findsSum.join();
            findsPrimeNumbers.join();

            System.out.printf("Array: %s\n\n", numbers);

            System.out.printf("Sum: %d\n\n", findsSum.GetSum());

            System.out.printf("PrimeNumbers: %s\n\n", findsPrimeNumbers.GetPrimeNumbers());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            token = true;
            System.out.println(e.getMessage());
        }
    }
}

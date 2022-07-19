import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadFindsPrimeNumbers extends Thread {

    private final List<Integer> _numbers;
    private final List<Integer> _primeNumbers = new ArrayList<>();

    public List<Integer> GetPrimeNumbers() {
        return _primeNumbers;
    }


    public ThreadFindsPrimeNumbers(List<Integer> numbers) {
        _numbers = numbers;
    }


    @Override
    public void run() {
        boolean isPrimeNumber;
        for (int number : _numbers) {
            if (isInterrupted()){
                return;
            }
            if (App.token) {
                Thread.currentThread().interrupt();
                return;
            }
            isPrimeNumber = true;
            for (int i = 2; i <= number / 2; i++) {
                if (App.token) {
                    Thread.currentThread().interrupt();
                    return;
                }
                if ((number % i) == 0) {
                    isPrimeNumber = false;
                    break;
                }
            }

            if(isPrimeNumber){
                _primeNumbers.add(number);
            }
        }
    }
}


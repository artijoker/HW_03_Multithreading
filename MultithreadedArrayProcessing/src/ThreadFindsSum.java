import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadFindsSum extends Thread {
    private final List<Integer> _numbers;
    private int _sum = 0;

    public int GetSum() {
        return _sum;
    }

    public ThreadFindsSum(List<Integer> numbers) {
        _numbers = numbers;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int number : _numbers) {
            if (isInterrupted()){
                return;
            }
            if (App.token) {
                Thread.currentThread().interrupt();
                return;
            }
            sum += number;
        }
        _sum = sum;
    }
}
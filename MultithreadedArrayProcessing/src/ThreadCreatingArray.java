import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadCreatingArray extends Thread {

    private final int _size;
    private final List<Integer> _numbers = new ArrayList<>();

    public List<Integer> GetNumbers(){
        return _numbers;
    }

    public ThreadCreatingArray(int size){
        _size = size;
    }

    @Override
    public void run() {
        var generator = new Random();
        for (int i = 0; i < _size; i++) {
            if (isInterrupted()){
                return;
            }
            if (App.token) {
                Thread.currentThread().interrupt();
                return;
            }
            _numbers.add(generator.nextInt(100));
        }
    }
}

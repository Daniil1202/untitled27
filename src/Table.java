import java.util.concurrent.CountDownLatch;

public class Table extends Thread {
    private final int  PHILOSOPH_COUNT = 5;
    private Fork [] forks;
    private Philosoph [] philosophs;
    private CountDownLatch cdl;

    public Table (){
        forks = new Fork[PHILOSOPH_COUNT];
        philosophs = new Philosoph[PHILOSOPH_COUNT];
        cdl = new CountDownLatch(PHILOSOPH_COUNT);
        init();
    }

    @Override
    public void run() {
        System.out.println("Приступим к обеду товарищи!");
        try {
            think();
            cdl.await();

        }catch (InterruptedException exception){
            throw new RuntimeException(exception);
        }
        System.out.println("Все пообедали");

    }
    public synchronized boolean getForks(int leftFork,int rightFork) {
        if (!forks[leftFork].isHelper() && !forks[rightFork].isHelper()) {
            forks[leftFork].setHelper(true);
            forks[rightFork].setHelper(true);
            return true;
        }
        return false;
    }
    public  void putForks(int leftFork, int rightFork){
        forks[leftFork].setHelper(false);
        forks[rightFork].setHelper(false);
    }

    private void init(){
        for (int i = 0; i <PHILOSOPH_COUNT ; i++) {
            forks[i] = new Fork();
        }
        for (int i = 0; i < PHILOSOPH_COUNT; i++) {
            philosophs[i] = new Philosoph("Philosoph №" + i, this, i,
                    (i +1) % PHILOSOPH_COUNT, cdl);

        }
    }
    private void think(){
        for (Philosoph philosoph: philosophs){
            philosoph.start();
        }
    }
}

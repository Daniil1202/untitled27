

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosoph extends Thread{
    private String name;
    private int leftFork;
    private int rightFork;
    private int countEat;
    private Random random;
    private CountDownLatch cdl;
    private Table table;

    public Philosoph (String name,Table table, int leftFork,int rightFork,CountDownLatch cdl){
        this.name=name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.cdl=cdl;
        this.table=table;
        countEat = 0;
        random = new Random();
    }

    @Override
    public void run() {
        while (countEat<3){
            try {
                thinking();
                eating();

            }catch (InterruptedException interruptedException){
                interruptedException.fillInStackTrace();
            }
        }
        System.out.println(name + " Пообедал");
        cdl.countDown();
    }

    private  void eating() throws InterruptedException {
        if (table.getForks(leftFork,rightFork)){
            System.out.println(name + " приступает к обеду, используя вилку : " +leftFork + "и"
                    + rightFork);
            sleep(random.nextLong(4000, 6000));
            table.putForks(leftFork,rightFork);
            System.out.println(name + " Покушал,можно и помыслить, положив вилки : "
                    + rightFork + " и " + leftFork);
            countEat++;
        }
    }

    private void thinking()throws InterruptedException{
        sleep(random.nextLong(100,3000));

    }
}

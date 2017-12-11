import Simulation.Sim;


public class Main {

    public static void main(String args[]) throws InterruptedException {

        int size = 500;
        while(size >= 500) {
            algPerc(1.0, size);
            algPerc(0.83, size);
            algPerc(0.66, size);
            algPerc(0.50, size);
            algPerc(0.33, size);
            algPerc(0.16, size);
            algPerc(0.0, size);
            size /= 4;
        }
    }
    private static void algPerc(double percent, int size) throws InterruptedException {
        System.out.println(percent);
        System.out.println("-----------------------------");
        System.out.println("-----------------------------");
        int shared = (int) (size * percent);
        int sep = size - shared;
        Sim sim = new Sim(shared, sep);
        sim.runSim(30000, 0.15, 6);
    }

    private static void sizePerc(double percent) throws InterruptedException {
        System.out.println(percent);
        System.out.println("-----------------------------");
        System.out.println("-----------------------------");
        for(int i = 500; i <= 32000; i *= 2) {
            int shared = (int) (i * percent);
            int sep = i - shared;
            System.out.println(i);
            Sim sim = new Sim(shared, sep);
            sim.runSim(30000, 0.15, 6);
        }
    }

}

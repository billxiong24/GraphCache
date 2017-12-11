import Simulation.Sim;


public class Main {

    public static void main(String args[]) throws InterruptedException {

        int shared = 30000;
        int separate = 0;


        Sim sim = new Sim(20000, 10000);
        sim.runSim(30000, 0.15, 6);

        System.out.println("------------------------------");
    }

}

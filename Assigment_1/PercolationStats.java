import java.time.DayOfWeek;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation simulation;
    private double[] estimates;
    private double[] confidence;
    private double x;
    private double s;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Both N & T must be greater than 0");
        estimates = new double[trials];
        confidence = new double[2];

        for (int i = 0; i < trials; i++){
            simulation = new Percolation(n);  //Initialize all sites to be blocked
            boolean percolates = simulation.percolates();
            int[] site = new int[2];

            while (!percolates) {
                //pick a blocked site at random
                while (true) {
                    site[0] = StdRandom.uniform(n) + 1; 
                    site[1] = StdRandom.uniform(n) + 1; 
                    if ( !simulation.isOpen(site[0], site[1]) ) break;
                }
                simulation.open(site[0], site[1]); //open site
                percolates = simulation.percolates();
            }
            estimates[i] = simulation.numberOfOpenSites() / (double) (n*n);  //percolation threshold
        }

        x = StdStats.mean(estimates);;
        s = StdStats.stddev(estimates);
        double factor = 1.96*s / Math.sqrt(trials);
        confidence[0] = x - factor;
        confidence[1] = x + factor;
    }

    // sample mean of percolation threshold
    public double mean(){ return x; }

    // sample standard deviation of percolation threshold
    public double stddev(){ return s; }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){ return confidence[0]; }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){ return confidence[1]; }

   // simulation client (see below)
   public static void main(String[] args){
        int n = 200;
        int t = 100;
        if (args.length == 2){
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        }
        PercolationStats monteCarloSimulation = new PercolationStats(n, t);
        StdOut.println("mean                    = " + monteCarloSimulation.mean());
        StdOut.println("stddev                  = " + monteCarloSimulation.stddev());
        StdOut.println("95% confidence interval = " + "[" + monteCarloSimulation.confidenceLo() + ", " + monteCarloSimulation.confidenceHi() + "]");   
   }

}
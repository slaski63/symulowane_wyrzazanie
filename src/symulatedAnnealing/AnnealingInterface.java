package symulatedAnnealing;

public interface AnnealingInterface {
	
	public double function(Particle particle);
	public double[][] function(double[] x, double[] y);
	
}

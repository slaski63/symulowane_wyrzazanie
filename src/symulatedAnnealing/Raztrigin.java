package symulatedAnnealing;

public class Raztrigin extends Annealing {

	public Raztrigin(main main) {
		super(main);

	}

	public double[][] function(double[] x, double[] y) {
		double[][] z = new double[y.length][x.length];
		for (int i = 0; i < x.length; i++) {

			for (int j = 0; j < y.length; j++) {
				z[j][i] = function(new Particle(x[i], y[j]));

			}
		}
		return z;
	}

	public double function(Particle particle) {

		double[] xs = new double[2];
		xs[0] = particle.x;
		xs[1] = particle.y;

		int m = 10;
		double sum = 0.0;
		for (int i = 0; i < xs.length; i++) {
			double xi = xs[i];
			sum += (Math.pow(xi, 2) - 10 * Math.cos(2 * Math.PI * xi));
		}
		return -(10 * sum);

	}

}

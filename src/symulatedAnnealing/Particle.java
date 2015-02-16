package symulatedAnnealing;

import java.util.Random;

public class Particle implements Cloneable {
	double x;
	double y;

	public Particle(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Particle() {

	}

	public Particle RandomParticle(double maxX, double minX, double maxY,
			double minY) {
		this.x = randomInRange(minX, maxX);
		this.y = randomInRange(minY, maxY);
		return this;
	}

	public Particle RandomY(double maxY, double minY) {

		this.y = randomInRange(minY, maxY);
		return this;
	}

	public Particle RandomX(double maxX, double minX) {

		this.x = randomInRange(minX, maxX);
		return this;
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public double randomInRange(double min, double max) {
		Random rand = new Random();
		Random random = new Random();
		double range = max - min;
		double scaled = random.nextDouble() * range;
		double shifted = scaled + min;
		return (double) Math.round(shifted * 1000) / 1000;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

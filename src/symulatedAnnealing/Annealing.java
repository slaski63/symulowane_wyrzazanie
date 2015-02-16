package symulatedAnnealing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;

public abstract class Annealing implements AnnealingInterface {
	double maxX;
	double minX;
	double maxY;
	double minY;
	double temp = 100;
	double coolingRate = 0.000001;
	private static final double INITIAL_TEMPERATURE = 100.0;
	private static final double FINAL_TEMPERATURE = 1;
	private static final double ALPHA = 0.999995;
	private static final int ITERATIONS_AT_TEMPERATURE = 3;
	Particle best;
	JProgressBar progressBar;
	Plot3DPanel wykresPanel;
	main parent;
	ArrayList<Particle> steps = new ArrayList<Particle>();

	public Annealing(main main) {
		this.parent = main;
	}

	public double getTemp() {
		return this.temp;
	}

	public double getMaxXVal() {
		return this.maxX;
	}

	public double getMinXVal() {
		return this.minX;
	}

	public void setMaxXVal(double max) {
		this.maxX = max;
	}

	public void setMinXVal(double min) {
		this.minX = min;
	}

	public double getMaxYVal() {
		return this.maxY;
	}

	public double getMinYVal() {
		return this.minY;
	}

	public void setMaxYVal(double max) {
		this.maxY = max;
	}

	public void setMinYVal(double min) {
		this.minY = min;
	}

	private boolean acceptanceProbability(double energy, double newEnergy,
			double temperature) {

		if (energy < newEnergy) {
			return true;
		} else {
			return Math.random() < Math.exp((-(energy - newEnergy))
					/ temperature);
		}

	}

	public void createPlot(Plot3DPanel wykresPanel) {
		for (int i = 0; i < wykresPanel.getPlots().size(); i++) {
			wykresPanel.removePlot(i);
		}

		this.wykresPanel = wykresPanel;
		double[] x = increment(this.maxX, this.maxX / 30); // x = 0.0:0.1:1.0
		double[] y = increment(this.maxY, this.maxY / 30);
		double[][] z = this.function(x, y);
		wykresPanel.addGridPlot("", Color.blue, x, y, z);

	}

	public static double[] increment(double max, double consta) {
		ArrayList<Double> wal = new ArrayList<Double>();
		double value = 0;
		while (value < max) {
			wal.add(value);
			value = value + consta;

		}
		double[] x = new double[wal.size()];
		for (int i = 0; i < wal.size(); i++) {
			x[i] = wal.get(i);

		}

		return x;
	}

	public void addProgressBad(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public void startAnnealing() {
		parent.pokazWykresButton.setEnabled(false);

		Thread thread = new Thread(new Runnable() {

			public void run() {
				if (progressBar != null) {
					progressBar.setMaximum((int) INITIAL_TEMPERATURE);
				}

				boolean solution = false;
				boolean useNew = false;
				int accepted = 0;

				double temperature = INITIAL_TEMPERATURE;
				double alpha = ALPHA;

				Particle currentParticle = new Particle().RandomParticle(maxX,
						minX, maxY, minY);
				Particle bestParticle = null;
				Particle tmpParticle;
				try {
					bestParticle = (Particle) currentParticle.clone();
					tmpParticle = (Particle) currentParticle.clone();
				} catch (CloneNotSupportedException e) {
					
					e.printStackTrace();
				}

				while (temperature > FINAL_TEMPERATURE) {
					accepted = 0;

					for (int i = 0; i < ITERATIONS_AT_TEMPERATURE; i++) {
						useNew = false;

						tmpParticle = new Particle().RandomParticle(maxX, minX,
								maxY, minY);

						if (round(function(tmpParticle)) < round(function(currentParticle))) {
							useNew = true;

						} else {
							double test = new Random().nextDouble(); // Get
																		// random
																		// value
																		// between
																		// 0.0
																		// and
																		// 1.0
							double delta = function(tmpParticle)
									- function(currentParticle);
							double calc = Math.exp(-delta / temperature);
							if (round(calc) > round(test)) {
								accepted++;
								useNew = true;

							}
						}

						if (useNew) {
							useNew = false;

							currentParticle = new Particle(tmpParticle.x,
									tmpParticle.y);
							if (round(function(currentParticle)) < round(function(bestParticle))) {
								bestParticle = new Particle(currentParticle.x,
										currentParticle.y);
								steps.add(bestParticle);
								solution = true;
							}
						}
					}
					if (progressBar != null) {
						progressBar.setValue((int) temperature);
					}
					temperature *= alpha;

				}
				if (wykresPanel != null) {
					double[] wynX = new double[1];
					double[] wynY = new double[1];
					double[] wynZ = new double[1];
					wynX[0] = bestParticle.x;
					wynY[0] = bestParticle.y;
					wynZ[0] = function(bestParticle);
					wykresPanel.addScatterPlot("Wynik: " + round(wynZ[0]) + "",
							Color.red, wynX, wynY, wynZ);

				}
				parent.pokazWykresButton.setEnabled(true);
				if (parent.pokazWykresButton.getActionListeners().length > 0) {
					parent.pokazWykresButton
							.removeActionListener(parent.pokazWykresButton
									.getActionListeners()[0]);
				}
				parent.pokazWykresButton.addActionListener(listner);
			}
		});
		thread.start();

	}

	ActionListener listner = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			JFrame frame = new JFrame("Podsumowanie");
			frame.setSize(800, 600);
			frame.setVisible(true);
			Plot2DPanel plot = new Plot2DPanel("SOUTH");
			double[] x = new double[steps.size()];
			double[] y = new double[steps.size()];
			for (int i = 0; i < steps.size(); i++) {
				x[i] = i;
				y[i] = function(steps.get(i));
			}
			frame.setContentPane(plot);
			plot.addLinePlot("sin(x)", x, y);
		}
	};

	public double round(double val) {
		return (double) Math.round(val * 1000) / 1000;
	}

	public static double randomInRange(double min, double max) {
		int maxs = (int) (max * 1000);
		Random generator = new Random();
		int shifted = generator.nextInt(maxs) + (int) min;
		return shifted / 1000;
	}
}

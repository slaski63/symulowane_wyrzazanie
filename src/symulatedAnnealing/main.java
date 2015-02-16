package symulatedAnnealing;

import java.awt.EventQueue;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.LineBorder;

import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;

import java.awt.Color;
import java.util.ArrayList;

public class main {

	private JFrame frame;
	private JTextField minXText;
	private JTextField maxXText;
	JRadioButton michalewiczRadio;
	JRadioButton raztriginRadio;
	Plot3DPanel wykresPanel;
	JProgressBar progressBar;
	private JTextField minYText;
	private JTextField maxYText;
	JButton pokazWykresButton;
	private JLabel lblTemp;
	JTextField temperatureText;
	private JLabel lblWartoscOchladania;
	JTextField coolingRateText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main window = new main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setEnabled(false);
		frame.setBounds(100, 100, 645, 414);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(21, 11, 207, 314);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JButton startAnnealingButton = new JButton("Rozpocznij");
		startAnnealingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Annealing annealing = null;
				if (michalewiczRadio.isSelected()) {
					annealing = new Michalewicz(main.this);
				} else if (raztriginRadio.isSelected()) {
					annealing = new Raztrigin(main.this);
				}

				for (int i = 0; i < wykresPanel.getPlots().size(); i++) {
					wykresPanel.removePlot(i);
				}

				try {
					annealing.setMaxXVal(Double.parseDouble(maxXText.getText()));
					annealing.setMaxYVal(Double.parseDouble(maxYText.getText()));
					annealing.setMinXVal(Double.parseDouble(minXText.getText()));
					annealing.setMinYVal(Double.parseDouble(minYText.getText()));
					annealing.createPlot(wykresPanel);
					try {
						annealing.addProgressBad(progressBar);
						annealing.startAnnealing();
					} catch (Exception e1) {
						
						e1.printStackTrace();
					}
				} catch (Exception a) {
					JOptionPane.showMessageDialog(frame,
							"Nie prawid³owe wartoœci");
				}

			}
		});
		startAnnealingButton.setBounds(6, 280, 191, 23);
		panel.add(startAnnealingButton);

		michalewiczRadio = new JRadioButton("Funkcja Michalewicza");
		michalewiczRadio.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (michalewiczRadio.isSelected()) {
					raztriginRadio.setSelected(false);
				} else {
					michalewiczRadio.setSelected(true);
				}

			}
		});
		michalewiczRadio.setSelected(true);
		michalewiczRadio.setBounds(6, 165, 155, 23);
		panel.add(michalewiczRadio);

		raztriginRadio = new JRadioButton("Funkcja Raztrigina");

		raztriginRadio.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (raztriginRadio.isSelected()) {
					michalewiczRadio.setSelected(false);
				} else {
					raztriginRadio.setSelected(true);
				}

			}
		});
		raztriginRadio.setBounds(6, 191, 141, 23);
		panel.add(raztriginRadio);

		minXText = new JTextField();
		minXText.setText("0");
		minXText.setBounds(119, 11, 42, 20);
		panel.add(minXText);
		minXText.setColumns(10);

		maxXText = new JTextField();
		maxXText.setText("0");
		maxXText.setBounds(39, 11, 37, 20);
		panel.add(maxXText);
		maxXText.setColumns(10);

		progressBar = new JProgressBar();
		progressBar.setBounds(6, 221, 191, 14);
		panel.add(progressBar);

		JLabel lblNewLabel = new JLabel("MinX:");
		lblNewLabel.setBounds(86, 14, 61, 14);
		panel.add(lblNewLabel);

		JLabel lblMax = new JLabel("MaxX:");
		lblMax.setBounds(6, 14, 46, 14);
		panel.add(lblMax);

		JLabel lblMiny = new JLabel("MinY");
		lblMiny.setBounds(86, 39, 46, 14);
		panel.add(lblMiny);

		JLabel lblMaxy = new JLabel("MaxY");
		lblMaxy.setBounds(6, 39, 46, 14);
		panel.add(lblMaxy);

		minYText = new JTextField();
		minYText.setText("0");
		minYText.setBounds(119, 36, 42, 20);
		panel.add(minYText);
		minYText.setColumns(10);

		maxYText = new JTextField();
		maxYText.setText("0");
		maxYText.setBounds(39, 36, 37, 20);
		panel.add(maxYText);
		maxYText.setColumns(10);

		pokazWykresButton = new JButton("Podsumowanie");

		pokazWykresButton.setEnabled(false);
		pokazWykresButton.setBounds(6, 246, 191, 23);
		panel.add(pokazWykresButton);

		lblTemp = new JLabel("Temperatura");
		lblTemp.setBounds(6, 84, 78, 14);
		panel.add(lblTemp);

		temperatureText = new JTextField();
		temperatureText.setText("100");
		temperatureText.setBounds(105, 81, 42, 20);
		panel.add(temperatureText);
		temperatureText.setColumns(10);

		lblWartoscOchladania = new JLabel("Wartosc ochladania");
		lblWartoscOchladania.setHorizontalAlignment(SwingConstants.LEFT);
		lblWartoscOchladania.setBounds(6, 109, 107, 23);
		panel.add(lblWartoscOchladania);

		coolingRateText = new JTextField();
		coolingRateText.setText("0.999");
		coolingRateText.setBounds(105, 112, 46, 20);
		panel.add(coolingRateText);
		coolingRateText.setColumns(10);

		lblWartoscOchladania.setVisible(false);
		coolingRateText.setVisible(false);
		lblTemp.setVisible(false);
		temperatureText.setVisible(false);

		wykresPanel = new Plot3DPanel("SOUTH");
		wykresPanel.plotToolBar.setFloatable(true);
		wykresPanel.plotToolBar.setRollover(true);
		wykresPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		wykresPanel.setBounds(238, 11, 371, 338);
		frame.getContentPane().add(wykresPanel);
	}
}

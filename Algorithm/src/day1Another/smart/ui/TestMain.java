package day1Another.smart.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import day1Another.smart.bono.BonoPlayer;
import day1Another.smart.bono.SmartTom;
import day1Another.smart.bono.SmartTomUtils;

public class TestMain extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;

	DrawPanel drawPanel;
	final Point ptJERRY = new Point(25, 85);
	final Point ptDoor = new Point (70, 70);

	public void init(final BonoPlayer player) {
		JPanel contentPanel = new JPanel();
		JPanel btnPanel = new JPanel();

		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(drawPanel = new DrawPanel(player), BorderLayout.CENTER);
		contentPanel.add(btnPanel, BorderLayout.PAGE_END);

		btnPanel.setPreferredSize(new Dimension(10, 32));

		btnPanel.setLayout(new FlowLayout());
		JButton startButton = new JButton();
		startButton.setText("START");
		startButton.setPreferredSize(new Dimension(80, 22));
		final JButton pauseButton = new JButton();
		pauseButton.setText("PAUSE");
		pauseButton.setPreferredSize(new Dimension(80, 22));
		JButton traceButton = new JButton();
		traceButton.setText("TRACE");
		traceButton.setPreferredSize(new Dimension(80, 22));
		btnPanel.add(startButton);
		btnPanel.add(pauseButton);
		btnPanel.add(traceButton);

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Runnable playRunnable = new Runnable() {

					@Override
					public void run() {
						try {
							player.play(ptDoor, ptJERRY,
									new SmartTom(), TestMain.this);
						} catch (Exception e) {
						}
					}
				};
				(new Thread(playRunnable)).start();
			}
		});
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (pauseButton.getText().equals("PAUSE")) {
					pauseButton.setText("RESUME");
					player.pause();
				} else {
					drawPanel.setJerryWay(null);
					pauseButton.setText("PAUSE");
					player.resume();
				}
			}
		});
		traceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawPanel.setJerryWay(SmartTomUtils.findFastestWay(player.getBoard()));
				drawPanel.repaint();
			}
		});

		this.setContentPane(contentPanel);

	}

	@Override
	public void update(Observable arg0, Object arg1) {
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		this.repaint();
	}

	public static void main(String[] args) {
		TestMain mainFrame = new TestMain();

		mainFrame.init(new BonoPlayer());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800, 800);
		mainFrame.setVisible(true);
	}
}

package graphicalElements;


import gameCommons.IFrog;
import util.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;


public class FroggerGraphic extends JPanel implements IFroggerGraphics, KeyListener {
	private Clip clip;
	private BufferedImage car;
	private BufferedImage ground;
	private BufferedImage ice;
	private BufferedImage star;
	private BufferedImage trap1;
	private BufferedImage trap2;
	private BufferedImage wall;
	private BufferedImage water;
	private BufferedImage wood;
	private BufferedImage frogger;
	private ArrayList<Element> elementsToDisplay;
	private int pixelByCase = 30;
	private int height;
	private IFrog frog;
	private JFrame frame;
	private boolean move;
	private volatile boolean start;

	private Timer timer;
	private int hour = 0, minute = 0, second = 0;
	private String time = "0" + hour + ":0" + minute + ":0" + second;
	private Font font = new Font("TimesRoman", Font.ITALIC, 30);


	public FroggerGraphic(int width, int height) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		this.height = height;
		this.move = false;
		this.start = true;
		elementsToDisplay = new ArrayList<>();
		Initiat();
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));

		clip = AudioSystem.getClip();
		AudioInputStream ais = AudioSystem.getAudioInputStream(new File("materials/BGM.wav"));
		clip.open(ais);
		clip.loop(Clip.LOOP_CONTINUOUSLY);

		ImageIcon icon = new ImageIcon("materials/start.gif");
		icon = new ImageIcon(icon.getImage().getScaledInstance(width * pixelByCase, height * pixelByCase, Image.SCALE_DEFAULT));
		JFrame frame = new JFrame("Frogger");
		JLabel label = new JLabel(icon);
		this.frame = frame;
		frame.getContentPane().add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.addKeyListener(this);
		while (start) {
			// Wait till enter
			start = true;
		}
		frame.getContentPane().remove(label);
		frame.getContentPane().add(this);
		frame.pack();
		timer = new Timer(1000, arg0 -> {
			second++;
			if (second > 59) {
				second = 0;
				minute++;
			}
			if (minute > 59) {
				minute = 0;
				hour++;
			}
			if (second < 10 && hour >= 10 && minute >= 10)
				time = hour + ":" + minute + ":0" + second;
			else if (second >= 10 && hour < 10 && minute >= 10)
				time = "0" + hour + ":" + minute + ":" + second;
			else if (second >= 10 && hour >= 10 && minute < 10)
				time = +hour + ":0" + minute + ":" + second;
			else if (second < 10 && hour < 10 && minute < 10)
				time = "0" + hour + ":0" + minute + ":0" + second;
			else if (second < 10 && hour < 10)
				time = "0" + hour + ":" + minute + ":0" + second;
			else if (second >= 10 && hour < 10)
				time = "0" + hour + ":0" + minute + ":" + second;
			else if (second < 10)
				time = hour + ":0" + minute + ":0" + second;
			else
				time = hour + ":" + minute + ":" + second;
			repaint();
		});
		timer.start();
	}

	private void Initiat() throws IOException {
		car = ImageIO.read(new File("materials/car1.png"));
		ground = ImageIO.read(new File("materials/ground.png"));
		ice = ImageIO.read(new File("materials/ice.png"));
		star = ImageIO.read(new File("materials/star.png"));
		trap1 = ImageIO.read(new File("materials/trap1.png"));
		trap2 = ImageIO.read(new File("materials/trap2.png"));
		wall = ImageIO.read(new File("materials/wall.png"));
		water = ImageIO.read(new File("materials/water.png"));
		wood = ImageIO.read(new File("materials/wood.png"));
		frogger = ImageIO.read(new File("materials/frog.png"));
	}

	private BufferedImage select_image(Element e){
		switch (e.logo){
			case car:
				return this.car;
			case ice:
				return this.ice;
			case star:
				return this.star;
			case wall:
				return this.wall;
			case wood:
				return this.wood;
			case trap1:
				return this.trap1;
			case trap2:
				return this.trap2;
			case water:
				return this.water;
			case ground:
				return this.ground;
			case frog:
				return this.frogger;
		}
		System.out.println("WARNING");
		return null;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		try{
			for (Element element : elementsToDisplay) {
				g.drawImage(select_image(element),
						pixelByCase * element.x,
						pixelByCase * (height - 1 - element.y),
						this);
			}
			g.setFont(font);
			Color c = (this.frog.on_water())?Color.RED:Color.ORANGE;
			g.setColor(c);
		} catch (NullPointerException e){
			e.fillInStackTrace();
		}

		int stringWidth = g.getFontMetrics().stringWidth(time);
		int xCoordinate = getWidth() - stringWidth;
		int yCoordinate = getHeight();
		g.drawString(time, xCoordinate, yCoordinate);
		String score = "Score: " + this.frog.get_score();
		g.drawString(score, 0, yCoordinate);
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		try {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					frog.move(Direction.up);
					break;
				case KeyEvent.VK_DOWN:
					frog.move(Direction.down);
					break;
				case KeyEvent.VK_LEFT:
					frog.move(Direction.left);
					break;
				case KeyEvent.VK_RIGHT:
					frog.move(Direction.right);
					break;
				default:
					start = false;
					return;
			}
			this.move = true;
			frog.slide();
		} catch (NullPointerException ignored){
		}
	}

	public boolean get_move(){
		boolean res = this.move;
		this.move = false;
		return res;
	}

	public void clear() {
		this.elementsToDisplay.clear();
	}

	public void add(Element e) {
		this.elementsToDisplay.add(e);
	}

	public void setFrog(IFrog frog) {
		this.frog = frog;
	}

	public void endGameScreen(String s) {
		timer.stop();
		String x = "<html><body><p align=\"center\">" + s + "<br/>"+ time + "</p></body></html>";
		frame.remove(this);
		JLabel label = new JLabel(x);
		label.setFont(new Font("Verdana", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setSize(this.getSize());
		frame.getContentPane().add(label);
		frame.repaint();
		clip.stop();
		try {
			Clip clip_dead = AudioSystem.getClip();
			AudioInputStream ais_dead = AudioSystem.getAudioInputStream(new File("materials/dead.wav"));
			clip_dead.open(ais_dead);
			clip_dead.loop(0);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}

}

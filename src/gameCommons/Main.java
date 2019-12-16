package gameCommons;

import Inf.EnvInf;
import Inf.FrogInf;
import graphicalElements.FroggerGraphic;
import graphicalElements.IFroggerGraphics;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		int width = 26;
		int height = 28;
		int tempo = 100; // 60 FPS (No influence on experience)
		int minSpeed = 3;
		double defaultDensity = 0.1D;

		IFroggerGraphics graphic = new FroggerGraphic(width, height);
		Game game = new Game(graphic, width, height, minSpeed, defaultDensity);
		IFrog frog = new FrogInf(game);
		game.setFrog(frog);
		graphic.setFrog(frog);
		IEnvironment env = new EnvInf(game);
		game.setEnvironment(env);

		Timer timer = new Timer(tempo, e -> {
			game.update();
			graphic.repaint();
		});

		timer.setInitialDelay(0);
		timer.start();
	}

}

package lab14;

import lab14lib.*;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		/** Your code here. */
//		Generator generator = new SineWaveGenerator(200);
//		GeneratorAudioAnimator gp = new GeneratorAudioAnimator(generator);
//		gp.drawAndPlay(4096, 10000000);
//		Generator g1 = new SineWaveGenerator(60);
//		Generator g2 = new SineWaveGenerator(61);
//		ArrayList<Generator> generators = new ArrayList<>();
//		generators.add(g1);
//		generators.add(g2);
//		MultiGenerator mg = new MultiGenerator(generators);
//		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(mg);
//		gav.drawAndPlay(500000, 1000000);
//		Generator generator = new AcceleratingSawToothGenerator(200, 1.1);
//		GeneratorAudioVisualizer gp = new GeneratorAudioVisualizer(generator);
//		gp.drawAndPlay(4096, 10000000);
		Generator generator = new StrangeBitwiseGenerator(512);
		GeneratorAudioVisualizer gp = new GeneratorAudioVisualizer(generator);
		gp.drawAndPlay(4096, 10000000);
	}
} 
package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

public class Program {
	//How many iterations till we conclude if the point is in the fractal set
	static int maxStepCount; //TODO check how much should it be
	//How much is the distance threshold which when passed we conclude that the point isn't in the fractal set
	//It is calculated automatically to fit the whole real rectangle to be rendered on the output image
	static double threshHoldRadius; 
	//create granularity constant and use it to load balance
	static int granularity;
	
	ArgumentParser args;

	BufferedImage image;

	Program(ArgumentParser args){
		this.args = args;
		granularity = args.getGranularity();
		maxStepCount = args.getMaxIterationCount();
		//We make the thresholdRadius big enough to fit the whole real rectangle the user entered to be rendered
		double real = Math.abs((args.getRealRectangle().getSmallestX())-(args.getRealRectangle().getBiggestX()));
		double imaginary = Math.abs((args.getRealRectangle().getSmallestY())-(args.getRealRectangle().getBiggestY()));
		threshHoldRadius = real+imaginary;
		//Setting a quiet mode which will disable all output to the standard output
		if(args.isQuiet()) {
			IOManager.disableStandardOutput();
		}
		startProgram();
		//restoring standard output so main can still output the total time of the program
		if(args.isQuiet()) {
			IOManager.enableStandardOutput();
		}
	}

	void startProgram() {
		System.out.println("Program started.");

		image = new BufferedImage(args.getWidthInPixels(), args.getHeightInPixels(), BufferedImage.TYPE_INT_RGB);

		//TODO separate number matrix generation, thread starting and color writing function to separate classes
		Color[][] pixelsAsColors = createColorMatrix(); //this will be where the threads will write/their output

		startThreads(pixelsAsColors);

		fillImage(image,pixelsAsColors);
		//writing the BufferedImage to a file
		File f = new File(args.getOutputFileName());
		try {
			ImageIO.write(image, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startThreads(Color[][] pixelsAsColors) {
		long start = Calendar.getInstance().getTimeInMillis();
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < args.getThreadCount(); i++) {
			Thread t = new Thread(new FractalRectThread(i, pixelsAsColors, args));
			t.start();
			threads.add(t);
		}
		for(Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long end = Calendar.getInstance().getTimeInMillis();
		IOManager.enableStandardOutput();
		System.out.println("Parallel section time: "+(end-start));
		IOManager.restorePrevious();
	}

	private void fillImage(BufferedImage image2, Color[][] pixelsAsColors) {
		for (int x = 0; x < pixelsAsColors.length; x++) {
			for (int y = 0; y < pixelsAsColors[x].length; y++) {
				//image.setRGB(x, y, pixelsAsColors[x][y].getRGB());
				image.setRGB(x, y, pixelsAsColors[x][y].getRGB());
			}
		}
	}

	//The results will be written in this matrix, it shouldn't contain any data
	private Color[][] createColorMatrix() {
		System.out.println("Generating color pixel matrix for the threads.");
		Color[][] result = new Color[args.getWidthInPixels()][args.getHeightInPixels()];
		return result;
	}


}

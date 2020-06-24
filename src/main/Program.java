package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.math3.complex.Complex;

public class Program {
	//How many iterations till we conclude if the point is in the fractal set
	static final int maxStepCount=20; //TODO check how much should it be
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
		//We make the thresholdRadius big enough to fit the whole real rectangle the user entered to be rendered
		double real = Math.max(Math.abs(args.getRealRectangle().getSmallestX()),Math.abs(args.getRealRectangle().getBiggestX()));
		double imaginary = Math.max(Math.abs(args.getRealRectangle().getSmallestY()),Math.abs(args.getRealRectangle().getBiggestY()));
		threshHoldRadius = (new Complex(real,imaginary)).abs();
		startProgram();
	}

	void startProgram() {
		System.out.println("Program started.");

		image = new BufferedImage(args.getWidthInPixels(), args.getHeightInPixels(), BufferedImage.TYPE_INT_RGB);

		//TODO separate number matrix generation, thread starting and color writing function to separate classes
		Complex[][] pixelsAsNumbers = generateNumberMatrix(); //this will be the input for the threads
		Color[][] pixelsAsColors = generateColorMatrix(); //this will be where the threads will write/their output

		startThreads(pixelsAsNumbers,pixelsAsColors);

		fillImage(image,pixelsAsColors);
		//writing the BufferedImage to a file
		File f = new File(args.outputFileName);
		try {
			ImageIO.write(image, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void startThreads(Complex[][] pixelsAsNumbers, Color[][] pixelsAsColors) {
		ExecutorService pool = Executors.newFixedThreadPool(args.threadCount);
		int rowCount = pixelsAsNumbers.length;
		int columnCount = pixelsAsNumbers[0].length;
		for(int x=0;x<=rowCount-1;x+=granularity) {
			for(int y=0;y<=columnCount-1;y+=granularity) {
				Rect<Integer> currentRect = new Rect<Integer>(x,Math.min(x+granularity,rowCount-1),
						y,Math.min(y+granularity,columnCount-1));
				FractalRectThread runable = new FractalRectThread(currentRect, pixelsAsNumbers, pixelsAsColors);
				pool.execute(runable);
			}
		}
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			System.out.println("There is a thread in the thread pool which hasnt completed yet");
			e.printStackTrace();
		}
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
	private Color[][] generateColorMatrix() {
		System.out.println("Generating color pixel matrix for the threads.");
		Color[][] result = new Color[args.widthInPixels][args.heightInPixels];
		return result;
	}

	//TODO Parallel number matrix generation
	private Complex[][] generateNumberMatrix() {
		System.out.println("Generating complex number matrix.");
		Complex[][] result = new Complex[args.widthInPixels][args.heightInPixels];

		double xDelta = (args.realRectangle.getBiggestX() - args.realRectangle.getSmallestX())/args.widthInPixels;
		double yDelta = (args.realRectangle.getBiggestY() - args.realRectangle.getSmallestY())/args.heightInPixels;
		for (int x = 0; x < result.length; x++) {
			for (int y = 0; y < result[x].length; y++) {
				result[x][y]=new Complex(args.realRectangle.getSmallestX()+(xDelta*x),
						args.realRectangle.getSmallestY()+(yDelta*y));
			}
		}
		return result;
	}
	

}

package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import org.apache.commons.math3.complex.Complex;

public class Program {
	//How many iterations till we conclude if the point is in the fractal set
	static final int maxStepCount=50; //TODO check how much should it be
	//How much is the distance threshold which when passed we conclude that the point isn't in the fractal set
	static final double threshHoldRadius = 4; //TODO maybe it should use the real rect from the ArgumentParser
	//create granularity constant and use it to load balance
	static final int granularity = 5;
	
	ArgumentParser args;

	BufferedImage image;

	Program(ArgumentParser args){
		this.args = args;
		startProgram();
	}

	void startProgram() {
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
		Rect<Integer> currentRect = new Rect<Integer>(0,(Program.));
		FractalRectThread runable1 = 
				new FractalRectThread(
						new Rect<Integer>(0,pixelsAsNumbers.length-1,0,pixelsAsNumbers[0].length-1), pixelsAsNumbers, pixelsAsColors);
		pool.execute(runable1);
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
		Color[][] result = new Color[args.widthInPixels][args.heightInPixels];
		return result;
	}

	//TODO Parallel number matrix generation
	private Complex[][] generateNumberMatrix() {
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

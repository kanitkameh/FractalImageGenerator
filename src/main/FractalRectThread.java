package main;

import java.awt.Color;
import java.util.Calendar;

import org.apache.commons.math3.complex.Complex;

public class FractalRectThread implements Runnable {
	public FractalRectThread(Rect<Integer> indexes, Complex[][] pixelAsComplexNumbers, Color[][] output, ArgumentParser args) {
		super();
		this.indexes = indexes;
		this.pixelAsComplexNumbers = pixelAsComplexNumbers;
		this.output = output;
		this.args = args;
	}
	//Indexes of the submatrix/subrect of the complex matrix that this thread will process
	Rect<Integer> indexes;
	//Which complex number corresponds to the specific pixel
	Complex[][] pixelAsComplexNumbers;
	//The output to be written to the image
	Color[][] output; 
	//the arguments passed to the program
	ArgumentParser args;
	@Override
	public void run() {
	System.out.println(Thread.currentThread().getName()+" has started");
	long start = Calendar.getInstance().getTimeInMillis();
	fillNumberSubmatrix();
	//TODO 
	//assert that the dimensions of output match with pixelAsComplexNumbers
		for (int x = indexes.getSmallestX(); x <= indexes.getBiggestX(); x++) {
			for (int y = indexes.getSmallestY(); y <= indexes.getBiggestY(); y++) {
				applyFunctionUptoNTimes(Program.maxStepCount,pixelAsComplexNumbers[x][y],x,y);
			}
		}
	long end = Calendar.getInstance().getTimeInMillis();
	System.out.println(Thread.currentThread().getName()+" has ended. Execution time: "+(end-start));
	}
	//This is function composition f(f(f(n)))
	void applyFunctionUptoNTimes(int times,Complex number,int xPosition, int yPosition) {
		Complex result=number;
		for (int i = 0; i < times; i++) {
			result = number.multiply(result.cos());
			if(result.abs()>Program.threshHoldRadius) {
				//number isn't in the fractal set as it grows to infinity
				//we color it depending on how fast it moves to infinity
				output[xPosition][yPosition]=new Color(0,0,(int)(((double)i/times)*255));
				//point is colored stop further checks
				return;
			}
		}
		//number is in the fractal set or very close to it 
		//we color it black
		output[xPosition][yPosition]=Color.white;
	}
	
	//The thread fills the numbers in its submatrix itself
	private void fillNumberSubmatrix() {
		System.out.println("Generating complex number matrix.");

		double xDelta = (args.getRealRectangle().getBiggestX() - args.getRealRectangle().getSmallestX())/args.getWidthInPixels();
		double yDelta = (args.getRealRectangle().getBiggestY() - args.getRealRectangle().getSmallestY())/args.getHeightInPixels();
		for (int x = indexes.getSmallestX(); x <= indexes.getBiggestX(); x++) {
			for (int y = indexes.getSmallestY(); y <= indexes.getBiggestY(); y++) {
				pixelAsComplexNumbers[x][y]=new Complex(args.getRealRectangle().getSmallestX()+(xDelta*x),
						args.getRealRectangle().getSmallestY()+(yDelta*y));
			}
		}
	}

}

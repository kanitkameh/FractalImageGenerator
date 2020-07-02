package main;

import java.awt.Color;
import java.util.Calendar;

import org.apache.commons.math3.complex.Complex;

public class FractalRectThread implements Runnable {
	public FractalRectThread(int threadNumber, Color[][] output, ArgumentParser args) {
		super();
		this.threadNumber = threadNumber;
		this.output = output;
		this.args = args;
	}
	//this is the thread number
	int threadNumber;
	//The output to be written to the image
	Color[][] output; 
	//the arguments passed to the program
	ArgumentParser args;
	@Override
	public void run() {
	System.out.println(Thread.currentThread().getName()+" has started");
	long start = Calendar.getInstance().getTimeInMillis();

	int threadCount = args.getThreadCount();
	int granularity = args.getGranularity();
	int width = args.getWidthInPixels();
	int height = args.getHeightInPixels();
	//We want to have strips of width atleast 1 no matter the granularity
	int stripCount = Math.min(granularity*threadCount,width);
	//This is aproximate not always correct since integer division is rounded result
	//Example: width = 1000 stripCount = 400 then stripSize should be 2,5 which is impossible
	int stripSize = width/stripCount;

	double smallestX = args.getRealRectangle().getSmallestX();
	double biggestX = args.getRealRectangle().getBiggestX();
	double smallestY = args.getRealRectangle().getSmallestY();
	double biggestY = args.getRealRectangle().getBiggestY();

	double xDelta = (biggestX - smallestX)/width;
	double yDelta = (biggestY - smallestY)/height;
loop:{
	//the condition is true since stripSize and StripCount get inaccurate with very small numbers
	for (int stripNumber = threadNumber; true ; stripNumber+=threadCount) {
		for (int x = stripNumber*stripSize; x < (stripNumber+1)*stripSize; x++) {
			for (int y = 0; y < height; y++) {
				if(x>=width) {
					break loop;
				}
				applyFunctionUptoNTimes(Program.maxStepCount,new Complex(smallestX+(x*xDelta),smallestY+(y*yDelta)),x,y);
			}
		}
	}
}
	long end = Calendar.getInstance().getTimeInMillis();
	System.out.println(Thread.currentThread().getName()+" has ended. Execution time: "+(end-start));
	}
	//This is function composition f(f(f(n)))
	void applyFunctionUptoNTimes(int times,Complex number,int xPosition, int yPosition) {
		Complex start=number;
		Complex result=number;
		for (int i = 0; i < times; i++) {
			result = number.multiply(result.cos());
			if((start.subtract(result)).abs()>Program.threshHoldRadius) {
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
	

}

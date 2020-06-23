package main;

import java.awt.Color;

import org.apache.commons.math3.complex.Complex;

public class FractalRectThread implements Runnable {
	public FractalRectThread(Rect<Integer> indexes, Complex[][] pixelAsComplexNumbers, Color[][] output) {
		super();
		this.indexes = indexes;
		this.pixelAsComplexNumbers = pixelAsComplexNumbers;
		this.output = output;
	}
	//Indexes of the submatrix/subrect of the complex matrix that this thread will process
	Rect<Integer> indexes;
	//Which complex number corresponds to the specific pixel
	Complex[][] pixelAsComplexNumbers;
	//The output to be written to the image
	Color[][] output; 

	@Override
	public void run() {
	//TODO 
	//assert that the dimensions of output match with pixelAsComplexNumbers
	for (int x = indexes.getSmallestX(); x <= indexes.getBiggestX(); x++) {
		for (int y = indexes.getSmallestY(); y <= indexes.getBiggestY(); y++) {
				applyFunctionUptoNTimes(Program.maxStepCount,pixelAsComplexNumbers[x][y],x,y);
			}
		}
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
		output[xPosition][yPosition]=Color.black;
	}

}

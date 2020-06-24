# FractalImageGenerator
This program generates fractal images using parallel programming(multiple threads). 
It generates a matrix of complex numbers each of which corresponds to a pixel that would be on the output image.
Then we start threads on non crossing submatrices(most of them are square submatrices) to speed up the computation.
The size of these submatrices depend on granularity 
How to build and use:
```
mvn clean package
cd target/
java -jar SPO-0.0.1-SNAPSHOT-jar-with-dependencies.jar -size 500x300 -rect -2.5:2.5:-1.5:1.5 -tasks 4 -output fractal.png -granularity 50 -quiet -max-iterations 50
```
## Options
* -size widthxheight
  * Defines the size of the fractal image
* -rect beginingOfRealAxis:EndOfRealAxis:BeginOfImaginaryAxis:EndOfImaginaryAxis
  * Defines the rectangle on the complex plane which will be visualized
* -tasks threadCount
  * Defines the number of threads that will run in parallel during computation
* -output fractal.png
  * Defines the name of the output image of the fractal
* -granularity pixelCount
  * Threads submatrice size 
* -quiet
  * Doesn't output nothing except total program time on the stdout
* -max-iterations
  * Number of iterations to test each point if it is part of the fractal. Points that doesn't go to inifinity(don't go beyond the threshold of the real rect in the specified number of iterations) are part of it.
![Generated Fractal](fractal.png)

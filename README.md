# FractalImageGenerator
This program generates fractal images using parallel programming(multiple threads). 
It uses static load balancing by splitting the color matrix into columnt strips which count is equal to the number of threads * granularity.
Threads are alternating between different strips. Better load balancing is achieved by smaller granularity.

How to build and use:
```
mvn clean package
cd target/
java -jar SPO-0.0.1-SNAPSHOT-jar-with-dependencies.jar -size 1000x600 -rect -2.5:2.5:-1.5:1.5 -tasks 4 -output fractal.png -granularity 50 -quiet -max-iterations 50
```
## Options
* -size widthxheight
  * Defines the size of the fractal image in pixels
* -rect beginingOfRealAxis:EndOfRealAxis:BeginOfImaginaryAxis:EndOfImaginaryAxis
  * Defines the rectangle on the complex plane which will be visualized
* -tasks threadCount
  * Defines the number of threads that will run in parallel during computation
* -output fractal.png
  * Defines the name of the output image of the fractal
* -granularity coefficient 
  * Increases the count but decreases the size of each task given to each thread
* -quiet
  * Doesn't output nothing except total program time on the stdout
* -max-iterations
  * Number of iterations to test each point if it is part of the fractal. Points that doesn't go to inifinity(don't go beyond the threshold of the real rect in the specified number of iterations) are part of it. More iterations means more precision in the output image.(Resolution should also be increased to be visible)
![Generated Fractal](fractal.png)

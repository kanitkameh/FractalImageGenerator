# FractalImageGenerator
This program generates fractal images using parallel programming(multiple threads). Example usage:
```
java main.Main -size 1920x1080 -rect -8.0:8.0:-4.5:4.5 -tasks 1 -output fractal.png
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
![Generated Fractal](fractal.png)

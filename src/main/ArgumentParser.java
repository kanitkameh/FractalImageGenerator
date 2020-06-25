package main;

public class ArgumentParser {
	private int widthInPixels;
	private int heightInPixels;
	private Rect<Double> realRectangle;
	private int threadCount;
	private String outputFileName;
	private boolean isQuiet;
	private int granularity;
	private int maxIterationCount;
	private String[] args;
	
	public ArgumentParser(String[] args) throws IllegalArgumentException {
		this.args=args;
		//TODO
		//Parallel argument processing
		parseSize();
		parseRect();
		parseThreadCount();
		parseOutputFileName();
		parseQuietMode();
		parseMaxIterationCount();
		parseGranularity();
	}
	private void parseMaxIterationCount() {
		maxIterationCount = Integer.parseInt(args[findIndex(args,"-max-iterations")+1]);
	}
	private void parseGranularity() {
		granularity = Integer.parseInt(args[findIndex(args,"-granularity")+1]);
	}
	private void parseRect() {
		String[] coords = args[findIndex(args,"-rect")+1].split(":");
		realRectangle = new Rect<Double>(
				Double.parseDouble(coords[0]),
				Double.parseDouble(coords[1]),
				Double.parseDouble(coords[2]),
				Double.parseDouble(coords[3]));
	}
	private void parseOutputFileName() {
		outputFileName = args[findIndex(args,"-output")+1];
	}
	private void parseThreadCount() {
		threadCount = Integer.parseInt(args[findIndex(args,"-tasks")+1]);
	}
	void parseQuietMode() {
		try {
			findIndex(args,"-quiet");
		}catch (IllegalArgumentException iae) {
			//we get here only if the quiet argument wasn't found
			isQuiet=false;
			return;
		}
		isQuiet=true;
	}
	void parseSize() {
		String size = args[findIndex(args,"-size")+1];
		widthInPixels=Integer.parseInt(size.split("x")[0]);
		heightInPixels=Integer.parseInt(size.split("x")[1]);
	}
		
	private int findIndex(String[] args, String s) throws IllegalArgumentException {
		for (int i = 0; i < args.length; i++) {
			if(args[i].equals(s)) {
				return i;
			}
		}
		
		throw new IllegalArgumentException("CLI "+s+" arg was not found");
	}
	public int getWidthInPixels() {
		return widthInPixels;
	}
	public void setWidthInPixels(int widthInPixels) {
		this.widthInPixels = widthInPixels;
	}
	public int getHeightInPixels() {
		return heightInPixels;
	}
	public void setHeightInPixels(int heightInPixels) {
		this.heightInPixels = heightInPixels;
	}
	public Rect<Double> getRealRectangle() {
		return realRectangle;
	}
	public void setRealRectangle(Rect<Double> realRectangle) {
		this.realRectangle = realRectangle;
	}
	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	public String getOutputFileName() {
		return outputFileName;
	}
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
	public boolean isQuiet() {
		return isQuiet;
	}
	public void setQuiet(boolean isQuiet) {
		this.isQuiet = isQuiet;
	}
	public String[] getArgs() {
		return args;
	}
	public void setArgs(String[] args) {
		this.args = args;
	}
	public int getGranularity() {
		return granularity;
	}
	public void setGranularity(int granularity) {
		this.granularity = granularity;
	}
	public int getMaxIterationCount() {
		return maxIterationCount;
	}
	public void setMaxIterationCount(int maxIterationCount) {
		this.maxIterationCount = maxIterationCount;
	}
}

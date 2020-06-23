package main;

public class Rect <T>{
	private T smallestX;
	private T smallestY;
	private T biggestX;
	private T biggestY;
	public Rect(T smallestX, T biggestX, T smallestY, T biggestY) {
		super();
		this.smallestX = smallestX;
		this.biggestX = biggestX;
		this.smallestY = smallestY;
		this.biggestY = biggestY;
	}
	public T getSmallestX() {
		return smallestX;
	}
	public void setSmallestX(T smallestX) {
		this.smallestX = smallestX;
	}
	public T getSmallestY() {
		return smallestY;
	}
	public void setSmallestY(T smallestY) {
		this.smallestY = smallestY;
	}
	public T getBiggestX() { 
		return biggestX;
	}
	public void setBiggestX(T biggestX) {
		this.biggestX = biggestX;
	}
	public T getBiggestY() {
		return biggestY;
	}
	public void setBiggestY(T biggestY) {
		this.biggestY = biggestY;
	}
}

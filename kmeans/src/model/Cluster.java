package model;

import model.Vector;
public class Cluster 
{
	private Vector mean;
	private int points = 0;
	public Cluster(Vector v)
	{
		mean = v;
		points = 1;
		
	}
	public void addVector(Vector v)
	{
		//(mean*points + v)/(points + 1);
		
	}
	public Vector getMean()
	{
		return mean;
	}
	
}


package model;

import model.Vector;
public class Cluster 
{
	private Vector mean;
	private long points = 0;
	public Cluster()
	{
		
	}
	public Cluster(Vector v)
	{
		mean = v;
		points = 1;
		
	}
	public Vector getMean()
	{
		return mean;
	}
	
}


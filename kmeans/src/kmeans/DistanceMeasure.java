package org.myorg;

import java.util.HashMap;

public class DistanceMeasure {
	public double CosineMeasure(Vector v1, Vector v2)
	{
		double disMeasure=0;
		HashMap<Integer, Integer> vec1=new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> vec2=new HashMap<Integer, Integer>();
		double numerator=0;
		double magA=1;
		double magB=1;
		for(int t : vec1.keySet())
		{
			if(vec2.containsKey(t))
			{
				numerator += vec1.get(t) * vec2.get(t);
				
			}
			magA += t^2;
			
		}
		magA = Math.sqrt(magA);
		
		for(int t : vec2.keySet())
		{
			magB += t^2;
		}
		magB = Math.sqrt(magB);
		
		disMeasure = numerator /( magA * magB );
		return disMeasure;
	}
}

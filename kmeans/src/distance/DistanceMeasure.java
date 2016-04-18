package distance;
import java.util.HashMap;
import model.Vector;

public class DistanceMeasure {
	public double EuclideanMeasure(Vector A, Vector B)
	{
		
		HashMap<Long, Double> vec1=A.getFeatures();
		HashMap<Long, Double> vec2=B.getFeatures();
		
		double x = 0; 
		for(long t : vec1.keySet())
		{
			
			
			if(vec2.containsKey(t))
			{
				x += (vec1.get(t) - vec2.get(t))*((vec1.get(t) - vec2.get(t)));
			}
			else
			{
				x += (vec1.get(t))*((vec1.get(t)));
			}
		}
		for(long t : vec2.keySet())
		{
			if(!vec1.containsKey(t))
			{
				x += (vec2.get(t))*((vec2.get(t)));
			}
		}
		return Math.sqrt(x);
	}
	public double CosineMeasure(Vector A, Vector B)
	{
		double simMeasure=0;
		HashMap<Long, Double> vec1=A.getFeatures();
		HashMap<Long, Double> vec2=B.getFeatures();
		double numerator  = 0;
		double magnitudeA = 0;
		double magnitudeB = 0;
		for(long t : vec1.keySet())
		{
			
			
			if(vec2.containsKey(t))
			{
				numerator += vec1.get(t) * vec2.get(t);
				
			}
			magnitudeA += vec1.get(t)*vec1.get(t);
			
		}
		magnitudeA = Math.sqrt(magnitudeA);
		
		for(long t : vec2.keySet())
		{
			magnitudeB += vec2.get(t)*vec2.get(t);
		}
		magnitudeB = Math.sqrt(magnitudeB);
		simMeasure = numerator / ( magnitudeA * magnitudeB );
		return simMeasure;
	}
	public double JaccardMeasure(Vector A, Vector B)
	{
		double simMeasure=0;
		HashMap<Long, Double> vec1=A.getFeatures();
		HashMap<Long, Double> vec2=B.getFeatures();
		double commonData = 0;
		double magnitudeA = 0;
		double magnitudeB = 0;
		for(long t : vec1.keySet())
		{
			
			if(vec2.containsKey(t))
			{
				
				if(vec1.get(t)<vec2.get(t))
					commonData += vec1.get(t);
				else
					commonData += vec2.get(t);
				
				
			}
			magnitudeA += vec1.get(t);
			
		}
		for(long t: vec2.keySet())
		{
			magnitudeB += vec2.get(t);
		}
		simMeasure = (commonData) / (magnitudeA + magnitudeB - commonData);
		
		
		return simMeasure;
		
	}
}

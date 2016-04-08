package distance;
import java.util.HashMap;
import model.Vector;

public class DistanceMeasure {
	public double CosineMeasure(Vector A, Vector B)
	{
		double simMeasure=0;
		HashMap<Integer, Integer> vec1=A.getFeatures();
		HashMap<Integer, Integer> vec2=B.getFeatures();
		double numerator=0;
		double magnitudeA=1;
		double magnitudeB=1;
		for(int t : vec1.keySet())
		{
			if(vec2.containsKey(t))
			{
				numerator += vec1.get(t) * vec2.get(t);
				
			}
			magnitudeA += t^2;
			
		}
		magnitudeA = Math.sqrt(magnitudeA);
		
		for(int t : vec2.keySet())
		{
			magnitudeB += t^2;
		}
		magnitudeB = Math.sqrt(magnitudeB);
		
		simMeasure = numerator / ( magnitudeA * magnitudeB );
		return simMeasure;
	}
	public double JaccardMeasure(Vector A, Vector B)
	{
		double simMeasure=0;
		HashMap<Integer, Integer> vec1=A.getFeatures();
		HashMap<Integer, Integer> vec2=B.getFeatures();
		double commonData=0;
		double magnitudeA=1;
		double magnitudeB=1;
		for(int t : vec1.keySet())
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
		for(int t: vec2.keySet())
		{
			magnitudeB += vec2.get(t);
		}
		simMeasure = (commonData) / (magnitudeA + magnitudeB - commonData);
		
		
		return simMeasure;
		
	}
}

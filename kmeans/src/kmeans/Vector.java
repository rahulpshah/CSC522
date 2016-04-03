package kmeans;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
public class Vector{
	
	HashMap<Integer, Integer> features;
	Vector(String line)
	{
		features = new HashMap<Integer,Integer>();
		//Removing the curly braces
		line = line.substring(1, line.length()-1);
		
		//Dividing the whole feature into tuples
		String tuples[] = line.split(",");
		
		for(String tuple : tuples)
		{
			//Removing the brackets
			tuple = tuple.substring(1,tuple.length()-1);
			
			StringTokenizer st = new StringTokenizer(tuple,";");
			
			int word_id = Integer.parseInt(st.nextToken());
			int count = Integer.parseInt(st.nextToken());
			
			features.put(word_id,count);
		}
	}
	@Override
	public String toString()
	{
		String s  = "";
		for(Map.Entry<Integer,Integer> entry: features.entrySet())
		{
			s+="("+entry.getKey()+","+entry.getValue()+"),";
		}
		return s;
	}
}

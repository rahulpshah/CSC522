package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
public class Vector
{
	private long documentID;
	private HashMap<Long, Double> features;
	public Vector()
	{
		features = new HashMap<Long, Double>();
	}
	public Vector(HashMap<Long, Double> hm)
	{
		features = hm;
		documentID = -1;
	}
	public Vector(String line)
	{
		int indexColon = line.indexOf(":");
	    documentID = Long.parseLong(line.substring(0, indexColon));
	    features = new HashMap<Long, Double>();
	    //Getting the feature vector
	    line = line.substring(indexColon + 1);

		//Removing the brackets
		line = line.substring(1, line.length()-1);
		
		//Document does not have any features
		if(line.length()==0)
		{
			return;
		}
		
		//Dividing the whole feature into tuples
		StringTokenizer tuple_tokens = new StringTokenizer(line, ",");
	
		while(tuple_tokens.hasMoreTokens())
		{
			String tuple = tuple_tokens.nextToken();
			//Removing the brackets
			if(tuple.length()==0)
					continue;
			tuple = tuple.substring(1,tuple.length()-1);
			StringTokenizer feature_tokens = new StringTokenizer(tuple,";");
			long word_id = Long.parseLong(feature_tokens.nextToken());
			Double count = Double.parseDouble(feature_tokens.nextToken());
			features.put(word_id,count);
		}
	}
	public void setDocumentID(long documentID) {
		this.documentID = documentID;
	}
	public HashMap<Long, Double> getFeatures()
	{
		return features;
	}
	
	public long getDocumentID()
	{
		return documentID;
	}
	
	@Override
	public String toString()
	{
		String s  = ""+documentID+":{";
		for(Map.Entry<Long,Double> entry: features.entrySet())
		{
			s+="("+entry.getKey()+";"+entry.getValue()+"),";
		}
		if(s.charAt(s.length()-1) == ',')
			s = s.substring(0, s.length()-1);
		s += "}";
		return s;
	}
	
	
	boolean equals(Vector v)
	{
		HashMap<Long, Double> hm1 = v.features;
		HashMap<Long, Double> hm2 = features;
		if(hm1.size() != hm2.size())
			return false;
		double eps = 0.001;
		for(Map.Entry<Long, Double> entry: hm1.entrySet())
		{
			long key = entry.getKey();
			if(!hm2.containsKey(key))
			{
				return false;
			}
			else if(Math.abs(hm2.get(key)-hm1.get(key)) > eps)
			{
				return false;
			}
		}
		return true;
	}
	public void add(Vector v)
	{
		HashMap<Long, Double> hm = v.features;
		
		for(Map.Entry<Long, Double> entry: hm.entrySet())
		{
			long key = entry.getKey();
			if(features.containsKey(key))
			{
				features.put(key, hm.get(key) + features.get(key));
			}
			else
			{
				features.put(key, hm.get(key));
			}
		}
	}
	public void divideByK(long n)
	{
		for(Entry<Long, Double> entry: features.entrySet())
		{
			features.put(entry.getKey(), entry.getValue()/n);
		}
	}

	
}
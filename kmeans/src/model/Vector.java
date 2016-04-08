package model;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
public class Vector
{
	private int documentID;
	private HashMap<Integer, Integer> features;
	public Vector(String line)
	{
		int indexColon = line.indexOf(":");
	    documentID = Integer.parseInt(line.substring(0, indexColon));
	    features = new HashMap<Integer,Integer>();
	    //Getting the feature vector
	    //System.out.println(line);
	    line = line.substring(indexColon + 1);
	    //System.out.println(line);
		//Removing the brackets
		line = line.substring(1, line.length()-1);
		//System.out.println(line);
		//Document does not have any features
		if(line.length()==0)
		{
			return;
		}
		//System.out.println("A"+line);
		//Dividing the whole feature into tuples
		StringTokenizer tuple_tokens = new StringTokenizer(line, ",");
		////System.out.println(tuple_tokens.);
		while(tuple_tokens.hasMoreTokens())
		{
			String tuple = tuple_tokens.nextToken();
			//Removing the brackets
			//System.out.println("**tuple"+tuple);
			if(tuple.length()==0)
					continue;
			tuple = tuple.substring(1,tuple.length()-1);
			StringTokenizer feature_tokens = new StringTokenizer(tuple,";");
			int word_id = Integer.parseInt(feature_tokens.nextToken());
			int count = Integer.parseInt(feature_tokens.nextToken());
			features.put(word_id,count);
		}
	}
	public HashMap<Integer, Integer> getFeatures()
	{
		return features;
	}
	
	public int getDocumentID()
	{
		return documentID;
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
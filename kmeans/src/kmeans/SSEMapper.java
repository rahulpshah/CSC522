package kmeans;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import distance.DistanceMeasure;
import model.Vector;

public class SSEMapper extends Mapper<LongWritable, Text, IntWritable, DoubleWritable> 
{
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException 
	{
		String line = value.toString();
		int clusterid = Integer.parseInt(line.split("\t")[0]);
		String vector_string[] = line.split("\t")[1].split("&");
		Vector vecs[] = new Vector[vector_string.length];
		
		for(int i=0;i<vector_string.length;i++)
		{
			vecs[i] = new Vector(vector_string[i]);
		}
		DistanceMeasure dm = new DistanceMeasure();
		for(int i=0;i<vector_string.length;i++)
		{
			double dist = dm.CosineMeasure(vecs[i], vecs[vecs.length - 1]);
			context.write(new IntWritable(clusterid), new DoubleWritable(1 - dist));
		}
	}
}

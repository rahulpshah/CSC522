package kmeans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;

import model.Cluster;
import model.Vector;

public class KMeansReducer extends Reducer<IntWritable, Text, IntWritable, Text> {

	Cluster clusters[];
	public static enum Counter{CONVERGED}
	public void setup(Context context) throws IOException, InterruptedException
	{
		if (context.getCacheFiles() != null && context.getCacheFiles().length > 0) 
		{
			URI[] uris = context.getCacheFiles();
			FileSystem hdfs = FileSystem.get(context.getConfiguration());
			int k = 4;
			clusters = new Cluster[k];
			InputStream fs = hdfs.open(new Path(uris[0]));
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			String vector_string="";
			int cluster_id = 0;
			while((vector_string=br.readLine())!=null)
			{
				String st[] = vector_string.split(" ");
				cluster_id = Integer.parseInt(st[0]);
				Vector v = new Vector(st[1]);
				clusters[cluster_id] = new Cluster(v);
			}
		}
	}
	@Override
	public void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException 
	{
		//System.out.println("Cluster "+key.toString()+":");
		//System.out.println(clusters[0].getMean());
		int cluster_id = Integer.parseInt(key.toString());
		Vector mean = new Vector();
		long count = 0;
		
		StringBuffer docs = new StringBuffer('[');
		for (Text value : values) 
		{
			
			//System.out.println(value.toString());
			Vector v = new Vector(value.toString());
			mean.add(v);
			
			docs = docs.append(v.getDocumentID()+",");
			count++;
		}/*
		if(docs.charAt(docs.length()-1)==',')
			docs = new StringBuffer(docs.substring(docs.length()-1)).append(']');
		else
			docs = docs.append(']');*/
		
		
		mean.divideByK(count);
		if(!clusters[cluster_id].getMean().equals(mean))
			context.getCounter(Counter.CONVERGED).increment(1);
//		if(ret.equals(clusters[cluster_id].getMean().toString().substring(2, )))
//		System.out.println("Mean of cluster"+cluster_id+"is:"+ret);
		
		context.write(new IntWritable(cluster_id), new Text(mean.toString()));
	}
}

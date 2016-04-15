package kmeans;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import model.Vector;

public class KMeansReducer extends Reducer<IntWritable, Text, IntWritable, Text> {

	public static enum Counter
	{
		CONVERGED
	}
	@Override
	public void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException 
	{
		System.out.println("Cluster "+key.toString()+":");
		
		int cluster_id = Integer.parseInt(key.toString());
		Vector mean = new Vector();
		long count = 0;
		
		StringBuffer docs = new StringBuffer('[');
		for (Text value : values) 
		{
			
			System.out.println(value.toString());
			Vector v = new Vector(value.toString());
			mean.add(v);
			docs = docs.append(v.getDocumentID()+",");
			count++;
		}
		String ret = mean.toString();// + "&" + new String(docs);
		//System.out.println("Mean of cluster"+cluster_id+"is:"+ret);
		context.write(new IntWritable(cluster_id), new Text(ret));
	}
}

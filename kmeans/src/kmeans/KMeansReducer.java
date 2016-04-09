package kmeans;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import model.Cluster;
import model.Vector;

public class KMeansReducer extends Reducer<IntWritable, Text, IntWritable, Text> {

	@Override
	public void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException 
	{
		System.out.println("Cluster "+key.toString()+":");
		for (Text value : values) 
		{
			//Vector v = new Vector(value.toString());
			//mean.add(v);
			//count++;
			String x = value.toString();
			System.out.println(x.substring(0, x.indexOf(':')));
		}
		/*int cluster_id = Integer.parseInt(key.toString());
		Vector mean = new Vector();
		long count = 0;
		for (Text value : values) 
		{
			Vector v = new Vector(value.toString());
			mean.add(v);
			count++;
		}
		mean.divideByK(count);*/
		context.write(new IntWritable(1), new Text("raul"));
	}
}

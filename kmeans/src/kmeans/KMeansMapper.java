package kmeans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import distance.DistanceMeasure;
import model.Cluster;
import model.Vector;

public class KMeansMapper extends Mapper<LongWritable, Text, Text, IntWritable> 
{
	//ArrayList<Cluster> clusters;
	Cluster clusters[];
	String name;
	@Override
	protected void setup(Context context) throws IOException, InterruptedException 
	{
		if (context.getCacheFiles() != null && context.getCacheFiles().length > 0) 
		{
			URI[] uris = context.getCacheFiles();
			FileSystem hdfs = FileSystem.get(context.getConfiguration());
			int k = Integer.parseInt(context.getConfiguration().get("k"));
			clusters = new Cluster[k];
			InputStream fs = hdfs.open(new Path(uris[0]));
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			String vector_string="";
			int i = 0;
			while((vector_string=br.readLine())!=null)
			{
				Vector v = new Vector(vector_string);
				clusters[i] = new Cluster(v);
				i++;
			}
			//System.out.println(clusters.size());
		}
	}
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		Vector v = new Vector(line);
		System.out.println(v.toString());
		double minDist = 0;
		int bestCluster = 0;
		for(int i=0;i<clusters.length;i++)
		{
			Cluster c = clusters[i];
			System.out.println(c.getMean());
			DistanceMeasure dm = new DistanceMeasure();
			double dist = dm.CosineMeasure(c.getMean(), v);
			System.out.println(dist);
			if(minDist > dist)
			{
				bestCluster = i;
			}
		}
		IntWritable one = new IntWritable(1);
		context.write(new Text("rahu;"), one);

	}
}
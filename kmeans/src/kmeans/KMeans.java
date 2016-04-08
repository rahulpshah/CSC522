package kmeans;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
public class KMeans extends Configured implements Tool
{
		String time = "" + System.nanoTime();
	  public static void main(String args[]) throws Exception 
	  {
		  	Configuration conf = new Configuration();
		    conf.addResource(new Path("/usr/local/Cellar/hadoop/2.7.1/libexec/etc/hadoop/core-site.xml"));
	        conf.addResource(new Path("/usr/local/Cellar/hadoop/2.7.1/libexec/etc/hadoop/hdfs-site.xml"));
		  	FileSystem hdfs = FileSystem.get(conf);
		  	Path inputPath = new Path(args[0]);
		  	String outputPath = "hdfs:///centers_2.txt";
		    Path file = new Path(new URI(outputPath));
		    //if ( hdfs.exists( file )) { System.out.println("ASDAS"); hdfs.delete( file, true ); }
		    
		    InputStream  is = hdfs.open(inputPath);
		    OutputStream os = hdfs.create(file);
		    int k = 3;
		    conf.set("k","3");
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    //SequenceFile.Writer x = SequenceFile.createWriter(null,null,null);
		    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		    for(int i=0;i<k;i++)
		    {
		    	String s = br.readLine();
		    	System.out.println(s);
		    	bw.write(s+"\n");
		    }
		    bw.close();
	    int res = ToolRunner.run(conf, new KMeans(), args);
	    System.exit(res);
	  }
	  public int run(String[] args) throws Exception 
	  {
		Job job = Job.getInstance(getConf());
	    Path inputPath = new Path(args[0]);
	    Path outputPath = new Path(args[1]+time);
	    int k = Integer.parseInt(args[2]);
	    FileInputFormat.setInputPaths(job, inputPath);
	    FileOutputFormat.setOutputPath(job, outputPath);
	    job.setJobName("Kmeans");
	    job.addCacheFile(new URI("hdfs:///centers_2.txt"));
	    job.setJarByClass(KMeans.class);
	    job.setInputFormatClass(TextInputFormat.class);
	    job.setOutputFormatClass(TextOutputFormat.class);
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(IntWritable.class);
	    job.setOutputKeyClass(Text.class);
	    
	    job.setOutputValueClass(IntWritable.class);
	    job.setMapperClass(KMeansMapper.class);
	    job.setCombinerClass(KMeansReducer.class);
	    job.setReducerClass(KMeansReducer.class);
	    return job.waitForCompletion(true) ? 0 : 1;
	  }
}


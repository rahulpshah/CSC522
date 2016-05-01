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
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
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
	  public static void main(String args[]) throws Exception 
	  {
		  	
		  	Configuration conf = new Configuration();
		    conf.addResource(new Path("/usr/local/Cellar/hadoop/2.7.1/libexec/etc/hadoop/core-site.xml"));
	        conf.addResource(new Path("/usr/local/Cellar/hadoop/2.7.1/libexec/etc/hadoop/hdfs-site.xml"));
		  	FileSystem hdfs = FileSystem.get(conf);
		  	Path inputPath = new Path(args[0]);
		    InputStream  is = hdfs.open(inputPath);
		    String center_path = "hdfs:///centers_2.txt";
		    OutputStream os = hdfs.create(new Path(new URI(center_path)));
		    int k = Integer.parseInt(args[2]);
		    
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		    for(int i=0;i<k;i++)
		    {
		    	String s = br.readLine();
		    	bw.write(i+"\t"+s+"\n");	
		    }
		    bw.close();
		    ToolRunner.run(conf, new KMeans(), args);
	    
	  }
	  public int run(String[] args) throws Exception 
	  {
		long breakCond=1;
		int counter=0;
		long time = System.currentTimeMillis();
		int max_iterations = Integer.parseInt(args[3]);
		do
		{
			Job job = Job.getInstance(getConf());
			Path inputPath = new Path(args[0]);
		    Path outputPath = new Path(args[1]+counter);
		    FileInputFormat.setInputPaths(job, inputPath);
		    FileOutputFormat.setOutputPath(job, outputPath);
		    job.setJobName("Kmeans");
		    job.addCacheFile(new URI("hdfs:///centers_2.txt"));
		    job.setJarByClass(KMeans.class);
		    job.getConfiguration().set("k", args[2]);
		    job.getConfiguration().set("converged", "false");
		    job.setInputFormatClass(TextInputFormat.class);
		    job.setOutputFormatClass(TextOutputFormat.class);
		    job.setMapOutputKeyClass(IntWritable.class);
		    job.setMapOutputValueClass(Text.class);
		    job.setOutputKeyClass(IntWritable.class);
		    job.setOutputValueClass(Text.class);
		    job.setMapperClass(KMeansMapper.class);
		    job.setReducerClass(KMeansReducer.class);
		    job.waitForCompletion((true));
		    
		    FileSystem fs = FileSystem.get(job.getConfiguration());
		    FSDataInputStream in = fs.open(new Path("hdfs://"+args[1]+counter+"/part-r-00000"));
		    FSDataOutputStream out = fs.create(new Path("hdfs:///centers_2.txt"),true);
		    int k = Integer.parseInt(args[2]);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
		    for(int i=0;i<k;i++)
		    {
		    	String s = br.readLine();
		    	bw.write(s+"\n");
		    }
		    bw.close();
		    System.out.println(job.getCounters().findCounter(KMeansReducer.Counter.CONVERGED).getValue());
		    breakCond = (job.getCounters().findCounter(KMeansReducer.Counter.CONVERGED).getValue() > 0) ? 1 : 0;
		    counter++;
		}
	    while(breakCond!=0 || counter > max_iterations);
		
		System.out.println("Kmeans is converged");
		Job job = Job.getInstance(getConf());
		Path inputPath = new Path(args[0]);
	    Path outputPath = new Path(args[1]+"FINAL_"+args[2]);
	    FileInputFormat.setInputPaths(job, inputPath);
	    FileOutputFormat.setOutputPath(job, outputPath);
	    job.setJobName("Kmeans");
	    job.addCacheFile(new URI("hdfs:///centers_2.txt"));
	    job.setJarByClass(KMeans.class);
	    job.setInputFormatClass(TextInputFormat.class);
	    job.setOutputFormatClass(TextOutputFormat.class);
	    job.setMapOutputKeyClass(IntWritable.class);
	    job.setMapOutputValueClass(Text.class);
	    job.getConfiguration().set("converged", "true");
	    job.getConfiguration().set("k", args[2]);
	    job.setOutputKeyClass(IntWritable.class);
	    job.setOutputValueClass(Text.class);
	    job.setMapperClass(KMeansMapper.class);
	    job.setReducerClass(KMeansReducer.class);
	    job.waitForCompletion((true));
	    System.out.println("Time:"+(System.currentTimeMillis() - time)/1000.0);
	    /*
	    System.out.println("Computing the SCM");
		job = Job.getInstance(getConf());
		inputPath = new Path(args[1]+"FINAL_"+args[2]);
	    outputPath = new Path("SCM_"+args[2]);
	    FileInputFormat.setInputPaths(job, inputPath);	
	    FileOutputFormat.setOutputPath(job, outputPath);
	    job.setJobName("SCM");
	    
	    job.setJarByClass(KMeans.class);
	    job.setInputFormatClass(TextInputFormat.class);
	    job.setOutputFormatClass(TextOutputFormat.class);
	    job.setMapOutputKeyClass(IntWritable.class);
	    job.setMapOutputValueClass(DoubleWritable.class);
	    job.setOutputKeyClass(IntWritable.class);
	    job.setOutputValueClass(DoubleWritable.class);
	    job.setMapperClass(SSEMapper.class);
	    job.setReducerClass(SSEReducer.class);
	    job.waitForCompletion((true));*/
	    return 0;
	  }
}


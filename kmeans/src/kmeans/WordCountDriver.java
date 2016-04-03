package kmeans;
import java.io.IOException;
import java.net.URI;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
public class WordCountDriver extends Configured implements Tool
{

	  public static void main(String args[]) throws Exception 
	  {
	    int res = ToolRunner.run(new WordCountDriver(), args);
	    System.exit(res);
	  }

	  public int run(String[] args) throws Exception {
	    
	    Configuration conf = getConf();
	    conf.addResource(new Path("/usr/local/Cellar/hadoop/2.7.1/libexec/etc/hadoop/core-site.xml"));
        conf.addResource(new Path("/usr/local/Cellar/hadoop/2.7.1/libexec/etc/hadoop/hdfs-site.xml"));
        Job job = Job.getInstance(conf);
	    Path inputPath = new Path(args[0]);
	    Path outputPath = new Path(args[1]);
	    FileInputFormat.setInputPaths(job, inputPath);
	    FileOutputFormat.setOutputPath(job, outputPath);
	    job.setJobName("WordCount");
	    job.setJarByClass(WordCountDriver.class);
	    job.setInputFormatClass(TextInputFormat.class);
	    job.setOutputFormatClass(TextOutputFormat.class);
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(IntWritable.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    job.setMapperClass(Map.class);
	    job.setCombinerClass(Reduce.class);
	    job.setReducerClass(Reduce.class);
	    return job.waitForCompletion(true) ? 0 : 1;
	  }

	  public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
	    private final static IntWritable one = new IntWritable(1);
	    private Text word = new Text();

	    @Override
	    public void map(LongWritable key, Text value,Mapper.Context context) throws IOException, InterruptedException {
	      String line = value.toString();
	      int indexColon = line.indexOf(":");
	      int documentId = Integer.parseInt(line.substring(0, indexColon));
	      System.out.print(documentId+":[");
	      Vector v = new Vector(line.substring(indexColon+1));
	      System.out.println(v.toString()+"]");
	      
	      
	      StringTokenizer tokenizer = new StringTokenizer(line);
	      while (tokenizer.hasMoreTokens()) 
	      {
	        word.set(tokenizer.nextToken());
	        //context.write(best_cluster(int), vector.tostring() );
	      }
	    }
	  }

	  public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {

	    @Override
	    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
	      int sum = 0;
	      for (IntWritable value : values) {
	        sum += value.get();
	      }
	      context.write(key, new IntWritable(sum));
	    }
	  }
}


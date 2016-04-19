package kmeans;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class SSEReducer extends Reducer<IntWritable, DoubleWritable, IntWritable, DoubleWritable> 
{
	@Override
	public void reduce(IntWritable key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException 
	{
		double sum = 0;
		for(DoubleWritable dbl: values)
		{
			double dist = dbl.get();
			sum += dist;
		}
		context.write(key, new DoubleWritable(sum));
	}
}

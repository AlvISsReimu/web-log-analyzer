package com.alviss.hadoop;

import com.alviss.util.CharPosUtil;
import com.kumkee.userAgent.UserAgent;
import com.kumkee.userAgent.UserAgentParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class BrowserCount {

    /**
     * map: load input file
     */
    public static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

        LongWritable one = new LongWritable(1);
        private UserAgentParser userAgentParser;

        @Override
        protected void setup(Context context) {
            userAgentParser = new UserAgentParser();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // read one log
            String line = value.toString();
            String source = line.substring(CharPosUtil.getCharPos(line, "\"", 7) + 1);

            UserAgent userAgent = userAgentParser.parse(source);
            String browser = userAgent.getBrowser();

            context.write(new Text(browser), one);
        }

        @Override
        protected void cleanup(Context context) {
            userAgentParser = null;
        }

    }

    /**
     * reduce: combine
     */
    public static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            long sum = 0;
            for (LongWritable value: values)
                sum += value.get();
            context.write(key, new LongWritable(sum));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // Create configuration
        Configuration conf = new Configuration();

        // clear existed output file
        Path outputPath = new Path(args[2]);
        FileSystem fileSystem = FileSystem.get(conf);
        if (fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath, true);
            System.out.println("existed output file deleted");
        }

        // Create job
        Job job = Job.getInstance(conf, "browser_count");

        // Set job exec class
        job.setJarByClass(BrowserCount.class);

        // Set file input path
        FileInputFormat.setInputPaths(job, new Path(args[1]));

        // Set map parameters
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        // Set combiner exec class which has the same logic as reducer
        job.setCombinerClass(MyReducer.class);

        // Set reduce parameters
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        // Set file output path
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}

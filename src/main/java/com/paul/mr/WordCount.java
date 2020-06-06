package com.paul.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCount extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        Configuration entries = new Configuration();
        int run = ToolRunner.run(entries, new WordCount(), args);
        System.exit(run);
    }

    @Override
    public int run(String[] args) throws Exception {

        Configuration conf = super.getConf();
        Job j = Job.getInstance(conf);
        j.setJarByClass(WordCount.class);

        j.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(j, new Path(args[0]));

        j.setMapperClass(WordCountMapper.class);
        j.setMapOutputKeyClass(Text.class);
        j.setMapOutputValueClass(IntWritable.class);


        j.setCombinerClass(WordCountReduce.class);

        j.setReducerClass(WordCountReduce.class);
        j.setOutputKeyClass(Text.class);
        j.setOutputValueClass(IntWritable.class);


        j.setNumReduceTasks(1);
        j.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(j, new Path(args[1]));

        return j.waitForCompletion(true) ? 0 : 1;
    }
}

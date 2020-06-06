package com.paul.mr2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class PaulSort extends Configured implements Tool {

    public static void main(String[] args) throws Exception {

        Configuration entries = new Configuration();
        entries.set("mapreduce.job.queuename", "test1");
        int i = ToolRunner.run(entries, new PaulSort(), args);

        System.exit(i);
    }

    @Override
    public int run(String[] args) throws Exception {

        Job job = Job.getInstance(super.getConf());
        job.setJarByClass(PaulSort.class);

        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job, new Path(args[0]));

        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setMapperClass(PaulSortMapper.class);

        job.setPartitionerClass(PaulSortPartitioner.class);
        job.setGroupingComparatorClass(PaulSortGroup.class);

        job.setReducerClass(PaulSortReducer.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setNumReduceTasks(Integer.parseInt(args[2]));

        return job.waitForCompletion(true) ? 0 : 1;
    }
}

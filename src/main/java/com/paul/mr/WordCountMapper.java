package com.paul.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] lines = value.toString().split("\n");
        IntWritable i = new IntWritable(1);
        Text t = new Text();
        for (String line :lines) {
            String[] words = line.split(" ");
            for (String w: words) {
                t.set(w);
                context.write(t, i);
            }
        }

    }
}

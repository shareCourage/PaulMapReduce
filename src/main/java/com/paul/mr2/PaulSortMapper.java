package com.paul.mr2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class PaulSortMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        System.out.println(key);
        System.out.println(value);
        String[] split = value.toString().split(",");

        OrderBean orderBean = new OrderBean();
        if (split.length == 3) {
            orderBean.setOrderId(split[0]);
            orderBean.setProductId(split[1]);
            orderBean.setPrice(Double.valueOf(split[2]));
            context.write(orderBean, NullWritable.get());
        }

    }
}

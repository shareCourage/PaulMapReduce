package com.paul.mr2;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class PaulSortPartitioner extends Partitioner<OrderBean, NullWritable> {
    @Override
    public int getPartition(OrderBean orderBean, NullWritable nullWritable, int i) {
        return orderBean.getOrderId().hashCode() & Integer.MAX_VALUE % i;
    }
}

package com.paul.mr2;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class PaulSortGroup extends WritableComparator {

    public PaulSortGroup() {
        super(OrderBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        OrderBean ao = (OrderBean)a;
        OrderBean bo = (OrderBean)b;
        return ao.getOrderId().compareTo(bo.getOrderId());
    }
}

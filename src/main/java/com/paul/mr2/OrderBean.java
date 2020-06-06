package com.paul.mr2;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderBean implements WritableComparable<OrderBean> {

    private String orderId;
    private String productId;
    private Double price;

    @Override
    public String toString() {
        return "OrderBean{" +
                "orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", price=" + price +
                '}';
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OrderBean() {

    }

    public OrderBean(String orderId, String productId, Double price) {
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public int compareTo(OrderBean o) {

        int i = this.orderId.compareTo(o.orderId);
        if (i == 0) {
            int priceCompare = this.price.compareTo(o.price);
            return -priceCompare;
        }
        return i;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {

        dataOutput.writeUTF(this.orderId);
        dataOutput.writeUTF(this.productId);
        dataOutput.writeDouble(this.price);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

        this.orderId = dataInput.readUTF();
        this.productId = dataInput.readUTF();
        this.price = dataInput.readDouble();
    }
}

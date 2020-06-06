package com.paul.mr;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class WordCountFormat extends FileInputFormat {
    @Override
    public RecordReader createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        WordCountRecordReader wordCountRecordReader = new WordCountRecordReader();
        wordCountRecordReader.initialize(inputSplit, taskAttemptContext);
        return wordCountRecordReader;
    }

    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }

    static public class WordCountRecordReader extends RecordReader<NullWritable, BytesWritable> {

        private BytesWritable bytesWr;

        private Configuration conf;

        private boolean flag = false;

        private FileSplit fs;

        @Override
        public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
            bytesWr = new BytesWritable();

            fs = (FileSplit)inputSplit;

            conf = taskAttemptContext.getConfiguration();


        }

        @Override
        public boolean nextKeyValue() throws IOException, InterruptedException {

            if (!flag) {

                long length = fs.getLength();
                int len = (int)length;
                byte[] bytes = new byte[len];

                Path path = fs.getPath();
                FileSystem fileSystem = path.getFileSystem(conf);

                FSDataInputStream in = fileSystem.open(path);

                //read inputstream into bytes
                IOUtils.readFully(in, bytes, 0, len);

                bytesWr.set(bytes, 0, len);
                flag = true;
                return true;
            }

            return false;
        }

        @Override
        public NullWritable getCurrentKey() throws IOException, InterruptedException {
            return NullWritable.get();
        }

        @Override
        public BytesWritable getCurrentValue() throws IOException, InterruptedException {
            return bytesWr;
        }

        @Override
        public float getProgress() throws IOException, InterruptedException {
            return flag ? 1 : 0;
        }

        @Override
        public void close() throws IOException {

        }
    }
}

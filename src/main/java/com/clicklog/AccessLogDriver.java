package com.clicklog;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author : avloger
 * @date : 2021/11/22 21:23
 */
public class AccessLogDriver {
    public static void main(String[] args) throws Exception {
        DateToNUM.initMap();
        Configuration conf = new Configuration();
        if(args.length != 2){
            args = new String[2];
            args[0] =  "hdfs://s201/data/clickLog/20170210/";
            args[1]    =  "hdfs://s201/uvout/hive" ;
        }

        Job job = Job.getInstance(conf); // 设置一个用户定义的job名称
        job.setJarByClass(AccessLogDriver.class);
        job.setMapperClass(AccessLogPreProcessMapper.class); // 为job设置Mapper类
        // 为job设置Reducer类
        job.setNumReduceTasks(0);
        job.setMapOutputKeyClass(Text.class);// 为job的输出数据设置Key类
        job.setMapOutputValueClass(NullWritable.class);// 为job输出设置value类
        FileInputFormat.addInputPath(job, new Path(args[0])); // 为job设置输入路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));// 为job设置输出路径
        System.exit(job.waitForCompletion(true) ? 0 : 1); // 运行job
    }
}

package com.aq.kafka.stream.task;

import com.aq.analy.PidaoXinXianDu;
import com.aq.input.KafkaMessage;
import com.aq.kafka.stream.map.PindaoXinXianDuMap;
import com.aq.kafka.stream.reduce.PindaoXinXiandusinkreduce;
import com.aq.kafka.stream.reduce.PindaoXinxianduReduce;
import com.aq.kafka.transfer.KafkaMessageSchema;
import com.aq.kafka.transfer.KafkaMessageWatermarks;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

/**
 */
public class SSXinXianDuProcessData {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir","D:\\soft\\hadoop-2.6.0-cdh5.5.1\\hadoop_dll2.6.0");
        args = new String[]{"--input-topic","test1","--bootstrap.servers","111.231.99.181:9092",
                "--zookeeper.connect","111.231.99.181:2181","--group.id","myconsumer1","--winsdows.size","50"};

        final ParameterTool parameterTool = ParameterTool.fromArgs(args);

        if (parameterTool.getNumberOfParameters() < 5) {
            System.out.println("Missing parameters!\n" +
                    "Usage: Kafka --input-topic <topic>" +
                    "--bootstrap.servers <kafka brokers> " +
                    "--zookeeper.connect <zk quorum> --group.id <some id>");
            return;
        }

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
        env.enableCheckpointing(5000); // create a checkpoint every 5 seconds
        env.getConfig().setGlobalJobParameters(parameterTool); // make parameters available in the web interface
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);


        FlinkKafkaConsumer010  flinkKafkaConsumer = new FlinkKafkaConsumer010<KafkaMessage>(parameterTool.getRequired("input-topic"), new KafkaMessageSchema(), parameterTool.getProperties());
        DataStream<KafkaMessage> input = env.addSource(flinkKafkaConsumer.assignTimestampsAndWatermarks(new KafkaMessageWatermarks()));
        DataStream<PidaoXinXianDu> map = input.flatMap(new PindaoXinXianDuMap());
        DataStream<PidaoXinXianDu> reduce = map.keyBy("groupbyfield").countWindow(Long.valueOf(parameterTool.getRequired("winsdows.size"))).reduce(new PindaoXinxianduReduce());
//        reduce.print();
        reduce.addSink(new PindaoXinXiandusinkreduce()).name("pdxinxiandureduce");
        try {
            env.execute("pindaossfx");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

package com.aq.kafka.stream.task;

import com.aq.analy.PindaoRD;
import com.aq.input.KafkaMessage;
import com.aq.kafka.stream.map.PindaoKafkaMap;
import com.aq.kafka.stream.reduce.PindaoReduce;
import com.aq.kafka.transfer.KafkaMessageSchema;
import com.aq.kafka.transfer.KafkaMessageWatermarks;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

/**
 *
 */
public class IntervalProcessData {
    public static void main(String[] args) {

        args = new String[]{"--input-topic", "test3", "--bootstrap.servers", "111.231.99.181:9092",
                "--zookeeper.connect", "111.231.99.181:2181", "--group.id", "myconsumer1", "--winsdows.size", "50", "--winsdows.slide", "5"};

        final ParameterTool parameterTool = ParameterTool.fromArgs(args);

        if (parameterTool.getNumberOfParameters() < 6) {
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


        FlinkKafkaConsumer010 flinkKafkaConsumer = new FlinkKafkaConsumer010<KafkaMessage>(parameterTool.getRequired("input-topic"), new KafkaMessageSchema(), parameterTool.getProperties());
        DataStream<KafkaMessage> input = env.addSource(flinkKafkaConsumer.assignTimestampsAndWatermarks(new KafkaMessageWatermarks()));
        DataStream<PindaoRD> map = input.map(new PindaoKafkaMap());
        DataStream<PindaoRD> reduce = map.keyBy("pingdaoid").countWindow(Long.valueOf(parameterTool.getRequired("winsdows.size")), Long.valueOf(parameterTool.getRequired("winsdows.slide"))).reduce(new PindaoReduce());
        reduce.addSink(new SinkFunction<PindaoRD>() {
            @Override
            public void invoke(PindaoRD value) {
                long count = value.getCount();
                long pindaoid = value.getPingdaoid();
                System.out.println("输出==pindaoid" + pindaoid + ":" + count);
            }
        }).name("pdrdreduce");
        try {
            env.execute("pindaoredian");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

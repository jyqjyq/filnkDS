package com.aq.kafka.stream.task;

import com.aq.analy.PidaoPvUv;
import com.aq.input.KafkaMessage;
import com.aq.kafka.stream.map.PindaopvuvMap;
import com.aq.kafka.stream.reduce.PindaopvuvReduce;
import com.aq.kafka.stream.reduce.Pindaopvuvsinkreduce;
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
public class SSProcessData {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir","D:\\soft\\hadoop-2.6.0-cdh5.5.1\\hadoop_dll2.6.0");
        args = new String[]{"--input-topic","test5","--bootstrap.servers","111.231.99.181:9092",
                "--zookeeper.connect","111.231.99.181:2181","--group.id","myconsumer1","--winsdows.size","10"};

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
//        flinkKafkaConsumer.setStartFromEarliest();
        DataStream<KafkaMessage> input = env.addSource(flinkKafkaConsumer.assignTimestampsAndWatermarks(new KafkaMessageWatermarks()));
//        DataStream<KafkaMessage> input = env.addSource(flinkKafkaConsumer);
        DataStream<PidaoPvUv> map = input.flatMap(new PindaopvuvMap());
        DataStream<PidaoPvUv> reduce = map.keyBy("groupbyfield").countWindow(Long.valueOf(parameterTool.getRequired("winsdows.size")),5).reduce(new PindaopvuvReduce());
        reduce.print();
        reduce.addSink(new Pindaopvuvsinkreduce()).name("pdpvuvreduce");
//        reduce.addSink(new SinkFunction<PidaoPvUv>() {
//            @Override
//            public void invoke(PidaoPvUv value) throws Exception {
//                System.out.println("recuducesinkd --pidaoPvUv==" + value);
//                long pingdaoid = value.getPingdaoid();
//                long pvcount = value.getPvcount();
//                long uvcount = value.getUvcount();
//                String timestring = value.getTimestring();
//                String pv = HbaseUtil.getdata("pindaoinfo", pingdaoid + timestring, "info", "pv");
//                String uv = HbaseUtil.getdata("pindaoinfo", pingdaoid + timestring, "info", "uv");
//                if (StringUtils.isNotBlank(pv)) {
//                    pvcount += pvcount + Long.valueOf(pv);
//                }
//                if (StringUtils.isNotBlank(uv)) {
//                    uvcount += uvcount + Long.valueOf(uv);
//                }
//
//                Map<String, String> datamap = new HashMap<String, String>();
//                datamap.put("pv", pvcount + "");
//                datamap.put("uv", uvcount + "");
//                System.out.println("pidaopvuv---- HbaseUtil.put(pindaoinfo+" + "," + pingdaoid + timestring + ",info" + datamap + ")");
//                HbaseUtil.put("pindaoinfo", pingdaoid + timestring, "info", datamap);
//            }
//        }).name("pdrdreduce");
        try {
            env.execute("pindaossfx");
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxx");
            e.printStackTrace();
        }


    }
}

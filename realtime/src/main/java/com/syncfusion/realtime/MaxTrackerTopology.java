package com.syncfusion.realtime;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;

public class MaxTrackerTopology {

    public static void main(String[] args) throws Exception {

        Config conf = configureTopology();
        TopologyBuilder builder = buildTopology();
        runTopology(conf, builder);
    }

    private static void runTopology(Config conf, TopologyBuilder builder) {
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("max-tracker", conf, builder.createTopology());
        Utils.sleep(20000);
        cluster.killTopology("max-tracker");
        cluster.shutdown();
    }

    private static Config configureTopology() {
        Config conf = new Config();
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 1);
        conf.setDebug(true);
        return conf;
    }

    private static TopologyBuilder buildTopology() {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("pressure-data-spout", new PressureDataSpout(), 10);
        builder.setBolt("max-tracker-bolt", new MaxTrackerBolt()).fieldsGrouping("pressure-data-spout", new Fields("sensor"));
        return builder;
    }
}

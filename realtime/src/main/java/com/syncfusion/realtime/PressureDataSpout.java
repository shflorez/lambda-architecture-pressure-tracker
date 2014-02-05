package com.syncfusion.realtime;

import backtype.storm.topology.OutputFieldsDeclarer;
import java.util.Map;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import com.syncfusion.utils.Common;
import com.syncfusion.utils.Configuration;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PressureDataSpout extends BaseRichSpout {

    public static Logger LOG = LoggerFactory.getLogger(PressureDataSpout.class);
    SpoutOutputCollector _collector;

    public PressureDataSpout() {
    }

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        _collector = collector;
    }

    @Override
    public void close() {
    }

    public void nextTuple() {
        Utils.sleep(100);
        _collector.emit(new Values(Configuration.SENSOR_NAME, Common.getRandomDouble(), new Date().getTime()));
    }

    @Override
    public void ack(Object msgId) {
    }

    @Override
    public void fail(Object msgId) {
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sensor", "value", "date"));
    }
}

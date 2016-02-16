package com.syncfusion.realtime;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import com.syncfusion.utils.Common;
import com.syncfusion.utils.Configuration;

public class PressureDataSpout extends BaseRichSpout {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
        
        // TODO
        double randomValue = Common.LOWER_RANGE + (new Random().nextDouble() * (Common.UPPER_RANGE - Common.LOWER_RANGE));
        //double randomValue = Common.getRandomDouble();
        
        _collector.emit(new Values(Configuration.SENSOR_NAME, randomValue, new Date().getTime()));
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

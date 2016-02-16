package com.syncfusion.realtime;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.syncfusion.utils.Configuration;
import com.syncfusion.utils.PressureReading;
import com.syncfusion.utils.PressureReadingHelpers;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mortbay.log.Log;
import redis.clients.jedis.Jedis;

public class MaxTrackerBolt extends BaseRichBolt {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OutputCollector _collector;
    PriorityQueue<PressureReading> queue;

    public MaxTrackerBolt() {
    }

    // TODO - Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
        this.queue = new PriorityQueue<PressureReading>(Configuration.MAXVALUES,
        new Comparator<PressureReading>() {
                    public int compare(PressureReading v1, PressureReading v2) {
                        return v1.getValue().compareTo(v2.getValue());
                    }
                }
        );
    }

    // TODO -  Override
    public void execute(Tuple tuple) {

        if (isTickTuple(tuple)) {
            // write to redis when we receive a tick
            PressureReading[] pressureReadingMaxValues = new PressureReading[queue.size()];
            queue.toArray(pressureReadingMaxValues);

            Jedis jedis = new Jedis(Configuration.REDIS_SERVER);
            if (pressureReadingMaxValues != null && pressureReadingMaxValues.length > 0) {
                Arrays.sort(pressureReadingMaxValues);
                for (PressureReading v : pressureReadingMaxValues) {
                    try {
                        Log.warn(PressureReadingHelpers.serializeToString(v));
                        jedis.lpush(Configuration.REDIS_CACHE_KEY, PressureReadingHelpers.serializeToString(v));
                    } catch (IOException ex) {
                        Logger.getLogger(MaxTrackerBolt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                jedis.ltrim(Configuration.REDIS_CACHE_KEY, 0, pressureReadingMaxValues.length - 1);
            }
        } else {

            String sensor = tuple.getString(0);
            double value = tuple.getDouble(1);
            long time = tuple.getLong(2);

            PressureReading pressureReading = new PressureReading(sensor, value, time);

            this.addValue(pressureReading);
        }

        _collector.ack(tuple);
    }

    private boolean isTickTuple(Tuple tuple) {
        return tuple.getSourceStreamId().equals("__tick");
    }

    // TODO Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // no further output
    }

    private void addValue(PressureReading value) {
        this.queue.add(value);
        if (this.queue.size() > Configuration.MAXVALUES) {
            this.queue.poll();
        }
    }
}

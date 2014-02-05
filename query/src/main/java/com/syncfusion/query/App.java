package com.syncfusion.query;

import com.syncfusion.utils.Configuration;
import com.syncfusion.utils.PressureReading;
import com.syncfusion.utils.PressureReadingHelpers;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import redis.clients.jedis.Jedis;

public class App {
    private static final String BATCH_RESULTS_FILENAME = "../batch/part-r-00000";

    public static void main(String[] args) throws IOException {
        ArrayList<PressureReading> values = new ArrayList<PressureReading>();
        
        getBatchResults(values);
        getRealtimeResults(values);
        sortResults(values);
        displayResults(values);
    }

    private static void displayResults(ArrayList<PressureReading> values) {
        List<PressureReading> finalValues = values.subList(0, Configuration.MAXVALUES - 1);
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        for (PressureReading reading : finalValues) {
            String text = String.format("Sensor - %s, Pressure - %f%n, Date - %s", 
                    reading.getName(), 
                    reading.getValue(), 
                    ft.format(reading.getDate()));
            
            System.out.println(text);
        }
    }

    private static void sortResults(ArrayList<PressureReading> values) {
        Collections.sort(values, new Comparator<PressureReading>() {
            @Override
            public int compare(PressureReading reading1, PressureReading reading2) {
                return reading2.getValue().compareTo(reading1.getValue());
            }
        });
    }

    private static void getRealtimeResults(ArrayList<PressureReading> values) throws IOException {
        // get near real-time data from redis
        // the running storm topology updates these results in redis
        Jedis jedis = new Jedis( Configuration.REDIS_SERVER);
        
        List<String> stored = jedis.lrange(Configuration.REDIS_CACHE_KEY, 0,  Configuration.MAXVALUES - 1);
        for (String s : stored) {
            PressureReading pressureReading = PressureReadingHelpers.deserializeFromString(s);
            values.add(pressureReading);
        }
    }

    private static void getBatchResults(ArrayList<PressureReading> values) throws NumberFormatException, IOException {
        // get serialized data output from the batch process
        List<String> lines = Files.readAllLines(Paths.get(BATCH_RESULTS_FILENAME),
                Charset.defaultCharset());

        for (String line : lines) {
            PressureReading pressureReading = PressureReadingHelpers.fromCSV(line);
            values.add(pressureReading);
        }
    }
}

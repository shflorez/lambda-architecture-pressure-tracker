package com.syncfusion.generatedata;

import com.syncfusion.utils.Configuration;
import com.syncfusion.utils.PressureReading;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

public class App {

    public static void main(String[] args) throws IOException {
        final int NumberOfValues = 10000;
        final Date start = new Date();
        final Random random = new Random();
        final String outputFileName = "pressure-data.avro";
        
        DatumWriter<PressureReading> pressureDatumWriter = new SpecificDatumWriter<PressureReading>(PressureReading.class);
        DataFileWriter<PressureReading> dataFileWriter = new DataFileWriter<PressureReading>(pressureDatumWriter);
        dataFileWriter.create(PressureReading.SCHEMA$, new File(outputFileName));
        
        for (int i = 0; i < NumberOfValues; i++) {
            PressureReading record = new PressureReading();
            record.setName(Configuration.SENSOR_NAME);
            double randomValue = Configuration.LOWER_RANGE
                    + (random.nextDouble() * (Configuration.UPPER_RANGE - Configuration.LOWER_RANGE));
            record.setValue(randomValue);
            record.setDate(start.getTime());
            dataFileWriter.append(record);
        }

        dataFileWriter.close();
    }
}

package com.syncfusion.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonDecoder;
import org.apache.avro.io.JsonEncoder;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class PressureReadingHelpers {

    public static String serializeToString(PressureReading pressureReading) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonEncoder encoder = EncoderFactory.get().jsonEncoder(PressureReading.SCHEMA$, outputStream);
        DatumWriter<PressureReading> pressureDatumWriter = new SpecificDatumWriter<PressureReading>(PressureReading.class);
        pressureDatumWriter.write(pressureReading, encoder);
        encoder.flush();
        outputStream.close();
        return outputStream.toString();
    }

    public static PressureReading deserializeFromString(String text) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(text.getBytes());
        JsonDecoder decoder = DecoderFactory.get().jsonDecoder(PressureReading.SCHEMA$, inputStream);
        DatumReader<PressureReading> pressureDatumReader = new SpecificDatumReader<PressureReading>(PressureReading.class);
        PressureReading pressureReading = pressureDatumReader.read(null, decoder);
        return pressureReading;
    }

    public static PressureReading fromCSV(String csv) {
        String parts[] = csv.split(",");
        return new PressureReading(parts[0], Double.parseDouble(parts[1]), Long.parseLong(parts[2]));
    }
}

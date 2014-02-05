values = load 'pressure-data.avro' using AvroStorage('{
"namespace":"com.syncfusion.stats",
"type": "record",
"name":"PressureReading",
"fields":[
{"name": "name", "type":"string"},
{"name": "value", "type":"double"},
{"name": "date", "type":"long"}
]
}');
sortedValues = ORDER values by value DESC;
top10Values = LIMIT sortedValues 10; 
STORE top10Values INTO 'output' USING PigStorage(',');
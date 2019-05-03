import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;

public class Consumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "group-1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(props);
        kafkaConsumer.subscribe(Arrays.asList("sms"));

        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                //System.out.printf("offset = %d, value = %s", record.offset(), record.value());
                //System.out.println(record.value());

                try{
                    String payload = record.value();
                    JSONObject jsonObject = new JSONObject (payload);

                    String toNumber = jsonObject.getString("to");
                    String fromNumber = jsonObject.getString("from");
                    String messageText = jsonObject.getString("messageText");

                    System.out.println("Sending message to: " + toNumber);
                    System.out.println("Message from Number:" + fromNumber);
                    System.out.println("Body of message: " +messageText);

                    SendSMS.SendTwilioSMS(toNumber, fromNumber, messageText);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }
        }

    }

}

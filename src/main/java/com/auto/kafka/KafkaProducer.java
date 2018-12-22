package com.auto.kafka;

import com.alibaba.fastjson.JSONObject;
import com.auto.dto.OrderDTO;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.Random;

@Component
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    static Long[] buyer = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L};
    static Long[] price = {1800L, 23200L, 3120L, 4982L, 5999L, 6899L, 7888L, 390230L, 99999L, 1000100L};



    private static OrderDTO createOrder() {
        Long id = System.currentTimeMillis();
        String orderNo = "o" + id;
        OrderDTO order = new OrderDTO();
        order.setId(id);
        order.setOrderNo(orderNo);
        order.setBuyerId(buyer[getRan()]);
        order.setMemo("订单备注");
        order.setSellerId(buyer[getRan()]);
        order.setPrice(price[getRan()]);
        return order;
    }
    private static int getRan() {
        Random ran = new Random();
        int i = ran.nextInt(10);
        return i;
    }
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:8080");
        props.put("acks", "all");
        props.put("retries ", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer producer = new org.apache.kafka.clients.producer.KafkaProducer(props);
        while (true) {
            OrderDTO orderDTO = createOrder();
            producer.send(new ProducerRecord("flink.order", JSONObject.toJSONString(orderDTO)));
            logger.error(JSONObject.toJSONString(orderDTO));
            try {
                Random random = new Random();
                int x = random.nextInt(200);
                Thread.sleep(Long.valueOf(x));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }






}

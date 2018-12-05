package com.self.ddyoung.rice.service.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;

public class RocketMqProducer {
    @Value("${rocketmq.broker}")
    private static String broker;

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("212.64.87.181:9876");
        try {
            producer.start();
            int i = 0;

            while (true) {
                i++;
                Message msg = new Message("PushTopic",
                        "push",
                        "1",
                        ("Just for 1205" + i).getBytes());
                SendResult result = producer.send(msg);
                msg = new Message("PullTopic",
                        "pull",
                        "1",
                        "Just for test.".getBytes());
                result = producer.send(msg);
                System.out.println("id:" + result.getMsgId() +
                        " result:" + result.getSendStatus());
                if (i % 10 == 0) {
                    Thread.sleep(20000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             producer.shutdown();
        }
    }
}

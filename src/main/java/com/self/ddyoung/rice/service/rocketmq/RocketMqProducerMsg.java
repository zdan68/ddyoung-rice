package com.self.ddyoung.rice.service.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.self.ddyoung.rice.model.HessianSerializer;
import com.self.ddyoung.rice.model.Msg;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocketMqProducerMsg {
    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMqProducerMsg.class);

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("212.64.87.181:9876");
        HessianSerializer<Msg> hessianSerializer = new HessianSerializer<>();
        try {
            producer.start();
            int i = 0;

            while (true) {
                i++;
                Msg messageEntity = new Msg();
                messageEntity.setBinlogTime(String.valueOf(System.currentTimeMillis()));
                messageEntity.setSchema("deppon_dwd.wd_order_op");
                Message msg = new Message("PushTopic1205",
                        "push",
                        hessianSerializer.serialize(messageEntity));
                SendResult sendResult = producer.send(msg);
                LOGGER.error(JSONObject.toJSONString(sendResult));
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

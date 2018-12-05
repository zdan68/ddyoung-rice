package com.self.ddyoung.rice.service.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.pamirs.data.cloud.sink.MessageEntity;
import com.self.ddyoung.rice.model.HessianSerializer;
import com.self.ddyoung.rice.model.Msg;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RocketMqConsumerFd {
    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMqConsumerFd.class);

    private HessianSerializer<Msg> hessianSerializer;

    public static void main(String[] args) {
        DefaultMQPushConsumer consumer =
                new DefaultMQPushConsumer("PushConsumer1205");
        consumer.setNamesrvAddr("212.64.87.181:9876");

        HessianSerializer<MessageEntity> hessianSerializer = new HessianSerializer<MessageEntity>();

        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("wd_order_op", "");
            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(
                    ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(
                    new MessageListenerConcurrently() {
                        public ConsumeConcurrentlyStatus consumeMessage(
                                List<MessageExt> list,
                                ConsumeConcurrentlyContext Context) {
                            for (MessageExt messageExt:
                                 list) {
                                byte[] body = messageExt.getBody();

                                MessageEntity message = hessianSerializer.unserialize(body);

                                System.out.println("msg:\t"+ JSONObject.toJSONString(message));
                            }
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                    }
            );
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

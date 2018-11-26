package topic;

import com.rabbitmq.client.*;
import util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create by Mr.ZXF
 * on 2018-11-23 17:47
 */
public class Recv2 {
    private static final String EXCHANGE_NAME = "test_exchange_topic";
    private static String QUEUE_NAME = "test_queue_topic_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //声明路由器和路由器类型
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String routingKeys[] = {"goods.delete","*.update"};
        for (String routingKey : routingKeys){

            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,routingKey);
        }

        System.out.println(" [2] Waiting for msg");

        /*监听消息*/
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [2] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}

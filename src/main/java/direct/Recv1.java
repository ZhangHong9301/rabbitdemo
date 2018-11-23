package direct;

import com.rabbitmq.client.*;
import util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create by Mr.Zhang
 * on 2018-11-23 16:12
 */
public class Recv1 {
    private static final String EXCHANGE_NAME = "test_exchange_direct";
    private static final String QUEUE_NAME = "test_queue_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        /*声明队列*/
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        /*绑定routingKey*/
        String[] routingKeys = {"error"};
        for (String routingKey : routingKeys) {
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);
        }
        System.out.println(" [1] Waiting for messages. To exit press CTRL+C");

//        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            /*消息到达触发这个方法*/
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(" [1] Received Direct'" + message + "'");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [1] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

/**
 * Create by Mr.Zhang
 * on 2018-11-20 16:40
 */
public class Recv {
    private final static String QUEUE_NAME = "hello a";

    public static void main(String[] argv) throws IOException, TimeoutException {
        /*建立连接和通道*/
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.145.101");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /*声明要消费的队列*/
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL + C");

        /*回调消费消息*/
        Consumer consumer = new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}

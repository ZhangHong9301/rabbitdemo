package fanout;

import com.rabbitmq.client.*;
import util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create by Mr.Zhang
 * on 2018-11-23 14:33
 */
public class Recv1 {
    private static final String EXCHANGE_NAME = "test_exchange_fanout";
    private static final String QUEUE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        /*声明队列*/
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        /*绑定队列到交换机 转发器*/
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        /*保证一次只分发一个*/
//        channel.basicQos(1);
        /*定义一个消费者*/
        Consumer consumer = new DefaultConsumer(channel) {
            /*消息到达触发这个方法*/
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(" [1] Received fanout'" + message + "'");
                try {
                    doWork(message);
                } finally {
                    System.out.println(" [1] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }
}


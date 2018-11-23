package workfair;

import com.rabbitmq.client.*;
import util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create by Mr.Zhang
 * on 2018-11-21 14:54
 */
public class Worker {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        /*获取连接*/
        final Connection connection = ConnectionUtils.getConnection();
        /*获取channel*/
        final Channel channel = connection.createChannel();

        /*声明队列*/
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages 1s. To exit press CTRL+C");

        /*保证一次只分发一个*/
        channel.basicQos(1);

        /*定义一个消费者*/
        final Consumer consumer = new DefaultConsumer(channel) {
            /*消息到达触发这个方法*/
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(" [#] Received NewTask'" + message + "'");
                try {
                    doWork(message);
                } finally {
                    System.out.println(" [#] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }
}

    
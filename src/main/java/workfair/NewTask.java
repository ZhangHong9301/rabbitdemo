package workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列，即一个生产者对多个消费者
 * 消息确认、消息持久、公平分发
 * Create by Mr.Zhang
 * on 2018-11-21 14:43
 */
public class NewTask {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws IOException, TimeoutException {

        /*获取连接*/
        Connection connection = ConnectionUtils.getConnection();
        /*获取channel*/
        Channel channel = connection.createChannel();


        /*声明队列*/
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        /**
         *
         * 每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
         *
         * 限制发送给同一个消费者的消息不得超过一条
         *
         * */
        channel.basicQos(1);

        for (int i = 1; i <= 20; i++) {

            String message ="NewTask"+ i + "....";

            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            try {
                Thread.sleep(i * 50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        channel.close();
        connection.close();
    }
}

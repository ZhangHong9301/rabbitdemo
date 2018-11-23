import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create by Mr.Zhang
 * on 2018-11-20 16:31
 */
public class Send {
    /*定义队列名字*/
    private final static String QUEUE_NAME = "hello a";

    public static void main(String[] argv) throws IOException, TimeoutException {
        /*获取连接*/
        Connection connection = ConnectionUtils.getConnection();
        /*获取channel*/
        Channel channel = connection.createChannel();

        /*为通道指明队列*/
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "";


        for (int i = 0; i <= 10; i++) {
            message = "Hello World!第 " + i + " 次";
            /*发布消息*/
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");

        }
        /*关闭连接*/
        channel.close();
        connection.close();
    }
}

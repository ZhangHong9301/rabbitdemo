import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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
        /*创建连接和通道*/
        ConnectionFactory factory = new ConnectionFactory();

        /*//设置rabbitmq-server服务IP地址
        factory.setHost("192.168.146.128");
        factory.setUsername("openstack");
        factory.setPassword("rabbitmq");
        factory.setPort(5672);
        factory.setVirtualHost("/");*/


        factory.setHost("192.168.145.101");
        factory.setPort(15672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /*为通道指明队列*/
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "Hello World!";

        /*发布消息*/
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        /*关闭连接*/
        channel.close();
        connection.close();
    }
}

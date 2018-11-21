import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 同一个消息同时发给多个消费者
 * 引入RabbitMQ消息模型中的重要概念路由器Exchange以及绑定等
 * 使用了fanout类型的路由器
 * Create by Mr.Zhang
 * on 2018-11-21 16:41
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        /*创建连接和通道*/
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.145.101");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /*声明路由以及路由的类型*/
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String message = getMessage(argv);
        /*发布消息*/
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");
        /*关闭连接和通道*/
        channel.close();
        connection.close();

    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1)
            return "info: Hello World!";
        return String.join(" ", strings);
    }
}

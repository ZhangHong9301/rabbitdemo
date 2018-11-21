import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 通过多重标准接收消息
 * 使用了topic路由器，可通过灵活的路由键和绑定键的设置，
    进一步增强消息选择的灵活性
 * Create by Mr.Zhang
 * on 2018-11-21 17:38
 */
public class EmitLogTopic {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) {
        Connection connection = null;
        Channel channel = null;
        try {
            //建立连接和通道
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.145.101");
            factory.setPort(5672);
            factory.setUsername("admin");
            factory.setPassword("admin");
            factory.setVirtualHost("/");
            connection = factory.newConnection();
            channel = connection.createChannel();

            //声明路由器和路由器类型
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            //定义路由键和消息
            String routingKey = "cron.warn";
            String message = "msg.....";

            //发布消息
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
    }
}

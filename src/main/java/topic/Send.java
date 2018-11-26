package topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式 Topic
 * 将路由键和某模式匹配
 *
 * Create by Mr.ZXF
 * on 2018-11-23 17:18
 */
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_topic";

    /**
     * Topic exchange
     * # 匹配一个或者多个
     * * 匹配一个
     Topic exchange is powerful and can behave like other exchanges.

     When a queue is bound with "#" (hash) binding key - it will receive all the messages, regardless of the routing key - like in fanout exchange.

     When special characters "*" (star) and "#" (hash) aren't used in bindings, the topic exchange will behave just like a direct one.
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();


        /*声明路由器和路由器类型*/
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);


        String msg = "商品----------";
        String routingKey = "order.add";

        /*发布消息*/
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
        System.out.println("[Topic] send "+routingKey+"--"+msg);

        channel.close();
        connection.close();
    }
}

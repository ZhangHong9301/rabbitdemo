package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create by Mr.ZXF
 * on 2018-11-23 17:47
 */
public class Recv1 {
    private static final String EXCHANGE_NAME = "test_exchange_topic";
    private static String QUEUE_NAME = "test_queue_topic_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String routingKey = "";
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,routingKey);
    }
}

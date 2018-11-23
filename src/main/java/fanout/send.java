package fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式 fanout
 * Create by Mr.Zhang
 * on 2018-11-23 11:33
 */
public class send {


    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        /*获取连接*/
        Connection connection = ConnectionUtils.getConnection();

        /*获取channel*/
        Channel channel = connection.createChannel();

        /*声明交换机*/
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        /*发送的消息*/
        String msg = "hello fanout";

        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        System.out.println("Send :" + msg);

        channel.close();
        connection.close();

    }
}

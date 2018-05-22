package scau.oop.wechat.backend;

import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.annotation.Bind;
import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.MsgType;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.utils.StringUtils;
import org.junit.Test;

/**
 * @author:czfshine
 * @date:2018/5/10 20:25
 */

public class HelloBot extends WeChatBot {

        public HelloBot(Config config) {
            super(config);
        }

        @Bind(msgType = MsgType.TEXT)
        public void handleText(WeChatMessage message) {
            if (StringUtils.isNotEmpty(message.getName())) {
                System.out.println(message.getName());
                System.out.println(message.getText());
                this.sendMsg(message.getFromUserName(), "自动回复: " + message.getText());
            }
        }

        public static void main(String[] args) {
            new HelloBot(Config.me().autoLogin(true).showTerminal(true)).start();
        }

    }


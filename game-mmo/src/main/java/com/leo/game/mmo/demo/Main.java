package com.leo.game.mmo.demo;

import com.taobao.lottery.game.multiplayer.server.MMOGameServer;

/**
 * Created by leo on 2016/12/21.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String appconfigPath = "AppConfig.json";
        boolean start = MMOGameServer.start(new GameServerAdaptor(), appconfigPath);
        System.out.println("start=" + start);
    }
}

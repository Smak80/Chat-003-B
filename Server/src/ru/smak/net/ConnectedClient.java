package ru.smak.net;

import java.io.Console;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectedClient {
    private static final ArrayList<ConnectedClient> clients = new ArrayList<>();
    private final Socket cs;
    private final NetIO nio;
    public ConnectedClient(Socket client){
        cs = client;
        nio = new NetIO(cs);
        clients.add(this);
    }

    public void start(){
        new Thread(()->{
            try {
                nio.startReceiving(this::parse);
            } catch (IOException e) {
                System.out.println("Ошибка: "+e);
            }
        }).start();
    }

    public Void parse(String d){
        System.out.println(d);
        return null;
    }
}

package ru.smak.net;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private String _host;
    private int _port;
    private Socket s;
    private NetIO nio;
    public Client(String host, int port) throws IOException {
        _host = host;
        _port = port;
        s = new Socket(_host, _port);
        nio = new NetIO(s);
    }

    public void startReceiving(){
        new Thread(()->{
            try {
                nio.startReceiving(this::parse);
            } catch (IOException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }).start();
    }

    public Void parse(String d){
        System.out.println(d);
        return null;
    }

    public void send(String userData) {
        try {
            nio.sendData(userData);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e);
        }
    }
}

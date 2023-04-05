package ru.smak.net;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectedClient {
    private static final ArrayList<ConnectedClient> clients = new ArrayList<>();
    private final Socket cs;
    private final NetIO nio;
    private String name = null;
    public ConnectedClient(Socket client){
        cs = client;
        nio = new NetIO(cs);
        clients.add(this);
        send(Command.INTRODUCE, "");
    }

    public void start(){
        new Thread(()->{
            try {
                nio.startReceiving(this::parse);
            } catch (IOException e) {
                clients.remove(this);
                for(var c: clients){
                    c.send(Command.LOGGED_OUT, name);
                }
            }
        }).start();
    }

    public void send(Command cmd, String userData) {
        try {
            if (name != null || cmd == Command.INTRODUCE) {
                nio.sendData(cmd + ":" + userData);
            }
        } catch (IOException e) {
            System.out.println("Ошибка (4): " + e);
        }
    }
    public Void parse(String d){
        if (name != null) {
            for (var c : clients) {
                c.send(Command.MESSAGE, (c != this ? name : "Вы") + ": " + d);
            }
        } else {
            boolean setName = true;
            for (var c : clients) {
                if (d.equalsIgnoreCase(c.name)) {
                    setName = false;
                    break;
                }
            }
            setName &= (!d.equalsIgnoreCase("вы"));
            if (setName) {
                name = d;
                for (var c: clients) {
                    c.send(Command.LOGGED_IN, name);
                }
            } else {
                send(Command.INTRODUCE, "Это имя нельзя использовать");
            }
        }
        return null;
    }
}

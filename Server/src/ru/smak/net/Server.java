package ru.smak.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int _port;
    private boolean stop = false;
    public Server(int port){
        _port = port;
    }

    /**
     * Запуск сервера
     */
    public void start(){
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(_port);
            var sSckt = ss;
            new Thread(()->{
                while (!stop) {
                    try {
                        Socket s = sSckt.accept();
                        new ConnectedClient(s).start();
                    } catch (IOException e) {
                        System.out.println("Ошибка (1): "+e.getMessage());
                    }
                }
            }).start();
        } catch (IOException e) {
            System.out.println("Ошибка (2): "+e.getMessage());
        }
    }

    /**
     * Остановка работы сервера
     */
    public void stop(){
        stop = true;
    }
}

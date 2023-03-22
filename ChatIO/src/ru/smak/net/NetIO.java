package ru.smak.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Function;

public class NetIO {
    private Socket s;
    /**
     * Признак необходимости завершения работы
     */
    private boolean stop = false;

    /**
     * Создание класса обеспечивающего сетевое взаимодействие клиентов и сервера
     * @param s клиентский сокет
     */
    public NetIO(Socket s){
        this.s = s;
    }

    /**
     * Метод, запускающий прием сообщений с удаленной стороны
     * @param parser функция, используемая для разбора получаемых сообщений
     * @throws IOException
     */
    public void startReceiving(Function<String, Void> parser) throws IOException {
        stop = false;
        var br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        while (!stop){
            var data = br.readLine();
            parser.apply(data);
        }
    }

    /**
     * Метод выполняет отправку данных по сети
     * @param data передаваемые данные
     * @throws IOException
     */
    public void sendData(String data) throws IOException {
        var pw = new PrintWriter(s.getOutputStream());
        pw.println(data);
        pw.flush();
    }

    /**
     * Метод выполняет остановку процесса получения данных с удаленной стороны
     */
    public void stopReceiving(){
        stop = true;
    }

}

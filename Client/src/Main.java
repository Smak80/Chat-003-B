import ru.smak.net.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try {
            var client = new Client("localhost", 5003);
            client.startReceiving();
            var stop = false;
            var s = new BufferedReader(new InputStreamReader(System.in));
            while (!stop){
                var userData = s.readLine();
                if (userData.equals("STOP")) {
                    stop = true;
                } else
                    client.send(userData);
            }
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}

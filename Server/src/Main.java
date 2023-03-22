import ru.smak.net.Server;

public class Main {
    public static void main(String[] args) {
        var srv = new Server(5003);
        srv.start();
    }
}

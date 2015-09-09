package teste;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.GregorianCalendar;

public class ClienteTCP {

    private static int PORTA = 12345;
    private static String IP = "localhost";

    public static void main(String[] args) throws InterruptedException {

        Calendar dataInicial = Calendar.getInstance();
        
        long time = System.currentTimeMillis();
        Thread.sleep(5000);
        long time2 = System.currentTimeMillis();
        System.out.println(time2-time+" "+time2);
        try {
            Socket socket = new Socket(IP, PORTA);
            System.out.println("Conectado com "
                    + socket.getInetAddress().getHostAddress());
            byte[] buffer = "PING".getBytes();
            OutputStream out = socket.getOutputStream();
            out.write(buffer);

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

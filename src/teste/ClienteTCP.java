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
        //dataInicial.setTime();
        long diferenca = System.currentTimeMillis() - dataInicial.getTimeInMillis();
        long diferencaSeg = diferenca / 1000;    //DIFERENCA EM SEGUNDOS     
        long diferencaMin = diferenca / (60 * 1000);    //DIFERENCA EM MINUTOS     
        long diferencaHoras = diferenca / (60 * 60 * 1000);    // DIFERENCA EM HORAS
        
        long time = 1441412444285;
        System.out.println(System.currentTimeMillis()-time);
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

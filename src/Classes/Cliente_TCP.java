/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Gabriel
 */
public class Cliente_TCP implements Runnable {

    private static int PORTA = 12345;
    private static String IP = "localhost";

    @Override
    public void run() {

        try {
            Socket socket = new Socket(IP, PORTA);
            System.out.println("Conectado com "
                    + socket.getInetAddress().getHostAddress());
            byte[] buffer = "PING".getBytes();
            OutputStream out = socket.getOutputStream();
            out.write(buffer);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

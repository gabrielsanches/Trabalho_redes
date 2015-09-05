/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
public class Servidor_TCP implements Runnable {

    @Override
    public void run() {
        int PORTA = 12345;

        InputStream in;

        ServerSocket server;
        try {
            server = new ServerSocket(PORTA);

            while (true) {
                System.out.println("Aguardando conexão...");
                Socket socket = server.accept();
                System.out.println("Conectado com "
                        + socket.getInetAddress().getHostAddress());

                byte[] buffer = new byte[1024];
                in = socket.getInputStream();
                int bytesLidos = in.read(buffer);

                System.out.println(bytesLidos + ": " + new String(buffer));

                socket.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(Servidor_TCP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

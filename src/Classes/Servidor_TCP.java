/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Telas.Mensagem;
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

    static int PORTA;

    public static void setPORTA(int PORTA) {
        Servidor_TCP.PORTA = PORTA;
    }

    public Servidor_TCP(int porta) {
        this.PORTA = porta;
    }

    @Override
    public void run() {

        ServerSocket server;
        try {
            server = new ServerSocket(PORTA);
            //System.out.println("Aguardando conexão...");

            while (true) {

                Socket socket = server.accept();

                System.out.println("Conectado com " + socket.getInetAddress().getHostAddress());
                Thread t = new Thread(new MyThread(socket));
                t.start();
            }
        } catch (Exception ex) {
            Logger.getLogger(Servidor_TCP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class MyThread implements Runnable {

        private Socket socket;

        public MyThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            while (true) {
                try {
                    InputStream in;
                    Mensagem ms = null;

                    byte[] buffer = new byte[1024];
                    in = socket.getInputStream();
                    int bytesLidos = in.read(buffer);
                    String msg_received = new String(buffer).trim();

                    System.out.println("Received TCP: " + msg_received);

                    if (msg_received.split(" ")[0].equals("HELLO")) {
                        String[] aux = msg_received.split(" ");
                        int port = Integer.parseInt(aux[2]);
                        Socket socket2;
                        socket2 = new Socket(socket.getInetAddress().getHostAddress(),port);
                        ms = new Telas.Mensagem(port, PORTA, socket.getInetAddress().getHostAddress(), socket2);
                        ms.setTitle("Mensagem para " + aux[1]);
                        java.awt.EventQueue.invokeLater(ms);
                    } else if (msg_received.split(" ")[0].equals("MSG")) {
                        System.out.println("Mensagem recebida de " + socket.getInetAddress().getHostAddress() + ": " + msg_received.substring(4));
                    } else if (msg_received.equals("BYE")) {
                        System.out.println("Troca de mensagem finalizada por: " + socket.getInetAddress().getHostAddress());
                        break;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Servidor_TCP.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                }
            }
        }
    }
}

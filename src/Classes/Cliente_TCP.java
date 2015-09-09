/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Telas.Mensagem;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Gabriel
 */
public class Cliente_TCP implements Runnable {

    private int PORTA;
    private String IP;
    private String nome_origem;
    private int porta_origem;
    private Socket socket;

    public Cliente_TCP(int port, String ip, String nome_origem, int porta_origem, Socket socket) {
        this.PORTA = port;
        this.IP = ip;
        this.nome_origem = nome_origem;
        this.porta_origem = porta_origem;
        this.socket=socket;
    }

    @Override
    public void run() {

        try {
            Mensagem ms = new Telas.Mensagem(PORTA, porta_origem, IP,socket);
            ms.setTitle("Mensagem para " + IP);
            java.awt.EventQueue.invokeLater(ms);
            byte[] buffer = ("HELLO " + nome_origem + " " + porta_origem).getBytes();
            OutputStream out = socket.getOutputStream();
            out.write(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

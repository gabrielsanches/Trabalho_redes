/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel-PC
 */
public class Cliente_send implements Runnable {

    static boolean connected = true;
    private static int porta_host = 6789;
    static int porta_cliente;
    private static String group = "225.4.5.6";
    static String nome;

    @Override
    public void run() {

        try {
            connected = true;
            String mensagem = "USER " + nome + " " + porta_cliente;
            InetAddress groupAddress = InetAddress.getByName(group);
            MulticastSocket socket = new MulticastSocket(porta_host);
            socket.joinGroup(groupAddress);
            while (true) {

                if (!connected) {
                    mensagem = "EXIT " + nome + " " + porta_cliente;
                    byte[] data1 = mensagem.getBytes();
                    DatagramPacket dg1 = new DatagramPacket(data1, data1.length, groupAddress, porta_host);
                    socket.send(dg1); //envio
                    break;
                }

                Thread.sleep(1000);
                byte[] data = mensagem.getBytes();
                DatagramPacket dg = new DatagramPacket(data, data.length, groupAddress, porta_host);
                socket.send(dg); //envio

            }
            socket.leaveGroup(groupAddress);
            socket.close();

        } catch (Exception ex) {
            Logger.getLogger(Cliente_send.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

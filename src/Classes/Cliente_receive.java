/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel-PC
 */
public class Cliente_receive implements Runnable {

    private static int porta_host = 6789;
    private static String group = "225.4.5.6";
    private static ArrayList<Usuario> users;

    @Override
    public void run() {
        InetAddress groupAddress;
        try {
            groupAddress = InetAddress.getByName(group);
            users = new ArrayList<Usuario>();
            MulticastSocket socket = new MulticastSocket(porta_host);
            socket.joinGroup(groupAddress);
            do {
//recebimento dos dados em um buffer de 1024 bytes
                DatagramPacket dg = new DatagramPacket(new byte[1024], 1024);
                socket.receive(dg); //recepção
//imprime a mensagem recebida

                String mensagem = new String(dg.getData()).trim();
                System.out.println("received " + mensagem);

                String[] aux = mensagem.split(" ");
                int port = Integer.parseInt(aux[2]);
                boolean flag = false;

                if (aux[0].equals("USER")) {
                    Usuario user = new Usuario(group, port, aux[1], dg.getAddress().toString());
                    for (Usuario a : users) {
                        if (a.getNome().equals(user.getNome()) && a.getPorta() == user.getPorta()) {
                            flag = true;
                        }
                    }

                    if (!flag) {
                        users.add(user);
                    }
                }
                
               if (aux[0].equals("EXIT")){
                   
               }

            } while (true);
            //socket.leaveGroup(groupAddress);
            //socket.close();

        } catch (Exception ex) {
            Logger.getLogger(Cliente_receive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

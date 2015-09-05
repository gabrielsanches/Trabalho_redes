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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel-PC
 */
public class Cliente_receive implements Runnable {

    private static int porta_host = 6789;
    private static String group = "225.4.5.6";
    static ArrayList<Usuario> users;

    public static ArrayList<Usuario> getUsers() {
        return users;
    }

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
                //System.out.println("received " + mensagem);

                String[] aux = mensagem.split(" ");
                int port = Integer.parseInt(aux[2]);
                boolean flag = false;

                if (aux[0].equals("USER")) {
                    //Pega horario do sistema.
                    Calendar calendar = new GregorianCalendar();
                    SimpleDateFormat out = new SimpleDateFormat("HH:mm:ss");
                    Date date = new Date();
                    calendar.setTime(date);
                    
                    Usuario user = new Usuario(group, port, aux[1], dg.getAddress().toString());
                    user.setTimer(calendar.getTime());
                    int i=0;
                    for (Usuario a : users) {
                        if (a.getNome().equals(user.getNome()) && a.getPorta() == user.getPorta()) {
                            users.get(i).setTimer(calendar.getTime());
                            flag = true;
                            i++;
                            break;
                        }
                    }

                    if (!flag) {
                        users.add(user);
                    }
                }

                if (aux[0].equals("EXIT")) {
                    int id = 0;
                    for (Usuario a : users) {
                        System.out.println(a.getNome() + " " + aux[1] + " " + a.getPorta() + " " + port);
                        if (a.getNome().equals(aux[1]) && a.getPorta() == port) {
                            flag = true;
                            break;
                        }
                        id++;
                    }

                    if (flag) {
                        users.remove(id);
                        System.out.println("Conexão fechada para o user: " + aux[1]);
                    }
                }

                //System.out.println(users);
            } while (true);
            //socket.leaveGroup(groupAddress);
            //socket.close();

        } catch (Exception ex) {
            Logger.getLogger(Cliente_receive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel-PC
 */
public class Cliente_receive implements Runnable {

    private int porta_host = 6789;
    private String group = "226.0.0.1";
    static ArrayList<Usuario> users = null;

    public static ArrayList<Usuario> getUsers() {
        return users;
    }

    @Override
    public void run() {
        InetAddress groupAddress;
        try {
            //atribuicoes iniciais
            groupAddress = InetAddress.getByName(group);
            users = new ArrayList<Usuario>();
            MulticastSocket socket = new MulticastSocket(porta_host);
            socket.joinGroup(groupAddress);
            do {
                
                //recebimento dos dados em um buffer de 1024 bytes
                DatagramPacket dg = new DatagramPacket(new byte[1024], 1024);
                socket.receive(dg); //recepção               
                String mensagem = new String(dg.getData()).trim();
                //System.out.println("received " + mensagem);

                String[] aux = mensagem.split(" ");
                int port = Integer.parseInt(aux[2]);
                boolean flag = false;
                long time = new Date().getTime();
                
                //Remove o elemento que passar de 10 segundos sem dar resposta USER.
                int k = 0;
                Iterator<Usuario> it = users.iterator();
                while (it.hasNext()) {
                    Usuario a = it.next();
                    //System.out.println(a);
                    if ((time - a.getTimer()) > 10000) {
                        it.remove();
                    }
                    k++;

                }
                
                //caso a mensagem seja USER é necessario colocar o usuario na lista caso ele nao esteja, e atualizar o valor do timer de quem já está na lista e mandou resposta.
                if (aux[0].equals("USER")) {
                    Usuario user = new Usuario(group, port, aux[1], dg.getAddress().toString().replaceAll("/", ""));
                    user.setTimer(new Date().getTime());
                    int i = 0;
                    for (Usuario a : users) {
                        if ((a.getNome().equals(user.getNome())) && a.getPorta() == user.getPorta()) {
                            a.setTimer(new Date().getTime());

                            flag = true;
                            i++;
                        }
                    }

                    if (!flag) {
                        users.add(user);
                    }
                }

                //Caso receba um exit, é verificado se o usuario está na lista e então ele é removido
                if (aux[0].equals("EXIT")) {
                    int id = 0;
                    for (Usuario a : users) {
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

            } while (true);
            //socket.leaveGroup(groupAddress);
            //socket.close();

        } catch (Exception ex) {
            Logger.getLogger(Cliente_receive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

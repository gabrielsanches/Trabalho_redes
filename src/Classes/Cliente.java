/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Telas.Mensagem;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel-PC
 */
public class Cliente {

    private static String user;
    private static int port;

    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        Cliente_receive cli = new Cliente_receive();
        //Thread responsavel por receber USER e EXIT
        Thread t2 = new Thread(cli);
        t2.start();
        Thread t1 = null;
        ArrayList<Usuario> users = cli.getUsers();

        System.out.print("Digite o nome de usuario e porta: ");
        String msg = in.nextLine();
        String[] aux1 = msg.split(" ");

        port = Integer.parseInt(aux1[1]);
        user = aux1[0];

        //Thread responsavel por enviar a mensagem de USER e EXIT
        Cliente_send cliente_send = new Cliente_send(port, user);
        t1 = new Thread(cliente_send);
        t1.start();

        Thread t3 = new Thread(new Servidor_TCP(port));
        t3.start();

        Thread.sleep(2000);
        users = cli.getUsers();

        if (users != null) {
            for (Usuario u : users) {
                if (u.getPorta() == port && !u.getNome().equals(user)) {
                    System.out.println("Já existe usuário com essa porta.");
                    System.exit(2);
                }
            }
        }

        while (true) {

            Thread.sleep(1000);
            users = cli.getUsers();
            System.out.println("Usuários on-line");
            System.out.println(cli.getUsers());
            System.out.print("Digite o comando: ");
            msg = in.nextLine();

            try {
                if (msg.equals("EXIT")) {
                    cliente_send.setConnected(false);

                    Thread.sleep(5000);
                    System.out.println("Conexão encerrada!");
                    t1.stop();
                    t2.stop();
                    t3.stop();
                    System.exit(1);
                }

                if (msg.equals("HELLO")) {
                    System.out.print("Escolha um usuário pelo nome: ");
                    String msg2 = in.nextLine();
                    for (Usuario a : users) {
                        if (msg2.equals(a.getNome())) {
                            try {
                                Socket socket;
                                socket = new Socket(a.getHost(), a.getPorta());

                                byte[] buffer = ("HELLO " + user + " " + port).getBytes();
                                OutputStream out = socket.getOutputStream();
                                out.write(buffer);
                                Mensagem ms = new Telas.Mensagem(a.getPorta(), port, a.getHost(), socket);
                                ms.setTitle("Mensagem para " + a.getNome());
                                java.awt.EventQueue.invokeLater(ms);

                            } catch (IOException ex) {
                                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }

            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Erro na comando de entrada!");
            }
        }
    }
}

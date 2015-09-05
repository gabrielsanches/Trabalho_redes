/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.IOException;
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
        Thread t2 = new Thread(cli);
        t2.start();

        System.out.print("Digite o comando: ");
        String msg = in.nextLine();
        String[] aux1 = msg.split(" ");

        if (aux1[0].equals("USER")) {
            port = Integer.parseInt(aux1[2]);
            user = aux1[1];
            
            Cliente_send.nome = user;
            Cliente_send.porta_cliente = port;

            Thread t1 = new Thread(new Cliente_send());
            t1.start();
        }
        
        Thread.sleep(1000);
        while (true) {
            System.out.println("Usuários on-line");
            System.out.println(cli.getUsers());             
            
            Thread.sleep(1000);
            
            System.out.print("Digite o comando: ");
            msg = in.nextLine();
            aux1 = msg.split(" ");
            try{
            if (aux1[0].equals("EXIT") && aux1[1].equals(user) && Integer.parseInt(aux1[2]) == port) {
                Cliente_send.connected = false;
                System.out.println("Conexão encerrada!");
                System.exit(1);
            }

            if (aux1[0].equals("HELLO")) {
                ArrayList<Usuario> users = cli.getUsers();
                for (Usuario a: users){
                    if (aux1[1].equals(a.getNome())){
                        try {
                            Socket socket = new Socket(a.getHost(), a.getPorta());
                            byte[] buffer = msg.getBytes();
                            
                            
                            
                        } catch (Exception ex) {
                            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            }  
            }catch(ArrayIndexOutOfBoundsException ex){
                System.out.println("Erro na comando de entrada!");
            } 
        }
    }
}

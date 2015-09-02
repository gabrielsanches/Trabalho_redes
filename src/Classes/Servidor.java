/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Gabriel-PC
 */
public class Servidor {

    public static void main(String[] args) {
        Thread t2 = new Thread(new Cliente_receive());
        t2.start();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Date;
import static javafx.scene.input.KeyCode.T;

/**
 *
 * @author Administrador
 */
public class Usuario implements Comparable<Usuario>{
    private String grupo;
    private int Porta;
    private String host;
    private String nome;
    private Date timer;

    public Usuario(String grupo, int Porta, String nome, String host) {
        this.grupo = grupo;
        this.Porta = Porta;
        this.nome = nome;
        this.host = host;
    }

    public Date getTimer() {
        return timer;
    }

    public void setTimer(Date timer) {
        this.timer = timer;
    }

    
    
    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public int getPorta() {
        return Porta;
    }

    public void setPorta(int Porta) {
        this.Porta = Porta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Usuario{" + "grupo=" + grupo + ", Porta=" + Porta + ", host=" + host + ", nome=" + nome + '}';
    }

    public String getHost() {
        return host;
    }


    @Override
    public int compareTo(Usuario o) { //igual retorna 1, diferente retorna 0
        if (this.getNome().equals(o.getNome()) && this.getPorta() == o.getPorta()){
            return 1;
        }else
            return 0;
    }
    
    
}

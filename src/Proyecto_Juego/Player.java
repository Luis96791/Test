package Proyecto_Juego;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dell
 */
public class Player {
int victorias, derrotas, empates, posicion, puntos=0;
String nombre, usuario, contrasena;
String registro[] = new String[10];

    public Player(int victorias, int derrotas, int empates, int posicion, String nombre, String usuario, String contrasena) {
        this.victorias = victorias;
        this.derrotas = derrotas;
        this.empates = empates;
        //Crear funcion que devuelva posicion
        this.posicion = posicion;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String[] getRegistro() {
        return registro;
    }



}

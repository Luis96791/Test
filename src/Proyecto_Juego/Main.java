package Proyecto_Juego;


import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dell
 */
public class Main {
    public static void main(String[] args) {
        Scanner lea = new Scanner(System.in);
        lea.useDelimiter("\n");
        int eleccion;
        boolean salir = false;

        while(!salir){
        System.out.print("\t**\tMENU INICIAL\t**\n\n\t1.- Login\n\t2.- Crear Usuario\n\t3.- Salir\n\n");
        System.out.print("Por favor ingrese el numero de su eleccion: ");
        eleccion=lea.nextInt();
        switch(eleccion){
            //login
            case 1:
            ConnectFour.setJugadorUno(ConnectFour.login());
            if(ConnectFour.getJugadorUno()!=null){
                ConnectFour.menuPrincipal(ConnectFour.getJugadorUno());
            }
                break;
                
            //crear usuario
            case 2:
                System.out.println(ConnectFour.CrearJugador());
                break;

            case 3:
                System.out.println("Adios.");
                salir=true;
                break;
                
            default:
                System.out.println("Opcion incorrecta. \n");
                break;
        
        }
        } 
    }
}
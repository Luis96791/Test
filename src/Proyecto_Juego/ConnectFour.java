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

public class ConnectFour {
static Player players[]=new Player[50], jugadorUno, jugadorDos, currentPlayer;
static Scanner lea = new Scanner(System.in);
static int usuariosCreados=0, donde, coincidencias = 0, turnosJugados=0, flagPosUno, flagPosDos;
static char tablero[][] = new char[6][7], marca;
static boolean salir = false, turno=true, columnallena=false, retirarse=false, empate=false, ganador=false, salirMenu = false; // ESTA VARIABLE DETERMINA DE QUIEN ES EL TURNO, TRUE=UNO
public static String ultimaPartida;
    public static String CrearJugador() {
        lea.useDelimiter("\n");
        System.out.print("Ingrese su nombre completo: ");
        String nombre=lea.next();
        System.out.print("Ingrese un usuario: ");
        String usuario=lea.next();
        System.out.print("Ingrese una contrasena: ");
        String contrasena=lea.next();
        String resultado;
        boolean comparar = false;
        for(int c =0; c<players.length; c++){
            for (int i = 0; i < usuariosCreados; i++) {
                if (players[i].getUsuario().equals(usuario)) {
                    comparar = true;
                    resultado = "Usuario ya Existe";
                    return resultado;
                }
            }
            
            if (comparar != true) {
                if(players[c] == null){
                players[c]= new Player(0,0,0,0,nombre,usuario,contrasena);
                resultado = "Usuario creado exitosamente";
                usuariosCreados++;
                return resultado;
                }
            }
        }
            resultado = "Error, excediste el numero máximo de usuarios.";
            return resultado;
        
    }

    public static Player getJugadorUno() {
        return jugadorUno;
    }

    public static Player getJugadorDos() {
        return jugadorDos;
    }

    public static Player login(){
        System.out.print("Ingrese su usuario: ");
        String usuario=lea.next();
        System.out.print("Ingrese su contrasena: ");
        String contrasena=lea.next();
        //Validamos usuarios y contrasenas dentro del arreglo players
        for(int c=0;c<usuariosCreados;c++){
            if(usuario.equals(players[c].getUsuario())){
                if (contrasena.equals(players[c].getContrasena())) {
                    System.out.println("Bienvenido "+players[c].getNombre()+"!");
                    currentPlayer=players[c];
                    return currentPlayer;
                }
            }
            }
            System.out.println("Este Usuario NO es Valido.\n");
            return null;
    }
    
    public static void menuPrincipal(Player jugadorUno){
        //ESTA FLAG ME VA AYUDAR A GUARDAR AL FINAL LOS PUNTOS Y LA ULTIMA PARTIDA DENTRO DEL ARREGLO PLAYERS
        for(int c =0; c<players.length;c++){
            if(players[c]!=null){
            if(jugadorUno==players[c]){
                flagPosUno=c;
            }
            }
        }
        do{
            salirMenu = false;
            salir = false;
            char letra;
        System.out.println("-Menu Principal:\t\t-Sesion iniciada como: "+ jugadorUno.getNombre());
        System.out.print("\t1.- Jugar ConnectFour\n\t2.- Ranking\n\t3.- Mi Perfil\n\t4.- Cerrar Sesion\n\n\tElija una Opcion: ");
        int opcion=lea.nextInt();
        switch(opcion){
            //JUGAR
            case 1:
                jugar();
                break;
                
            //RANKING
            case 2:
                returnRanking();
                break;
            //MI PERFIL
            case 3:
                System.out.println("\t--\tMI PERFIL\t--");
                System.out.println("\t- Nombre: "+jugadorUno.getNombre()+"\n\t- Usuario: "+jugadorUno.getUsuario()+"\n\t- Contrasena: "+jugadorUno.getContrasena()+"\n\t- Puntos: "+jugadorUno.puntos);
                do {                    
                    System.out.print("\n\t---\tOPCIONES\t---\n\n\ta) Editar mis datos\n\tb) Ver mis ultimos juegos\n\tc) Eliminar Cuenta\n\td) Salir\n\n\tElija una Opcion: ");
                    letra=lea.next().charAt(0);
                switch(letra){
                    case 'a':
                        menuEditar(); 
                    break;
                    case 'b':
                        printLog();
                        break;
                    case 'c':
                        eliminarCuenta();
                        break;
                    default:
                        if (letra != 'd') {
                            System.out.println("Opcion incorrecta");
                            break;
                        }
                        salirMenu = true;
        
                }
                } while (salirMenu != true);
                break;
            //CERRAR SESION 
            case 4:
                System.out.println("Cerrando Sesión");
                salir=true;
                break;
                
            default:
                System.out.println("Opcion incorrecta.\n");
                break;
        
        }
        }while(!salir);
    }

    public static void imprimirTablero(){
    for (char[] tablero1 : tablero) {
        for (int c = 0; c<=6; c++) {
            System.out.print(tablero1[c] + " ");
        }
        System.out.print("\n");
    }
    }
    
    public static void jugar(){
    // LLENAMOS EL TABLERO DE '_'.
    for (char[] tablero1 : tablero) {
        for (int c = 0; c<=6; c++) {
            tablero1[c] = '_';
        }
    }
    
    
        //SE NECESITAN DOS JUGADORES, PEDIMOS EL SEGUNDO INMEDIATAMENTE:

        System.out.println("Necesitas un contrincante. (Jugador Dos)");
        jugadorDos=login();
        //ESTA FLAG ME VA AYUDAR A GUARDAR AL FINAL LOS PUNTOS Y LA ULTIMA PARTIDA DENTRO DEL ARREGLO PLAYERS
        for(int c =0; c<players.length;c++){
            if(jugadorDos==players[c]){
                flagPosDos=c;
            }
        }
        
        //VALIDAMOS QUE EL SEGUNDO USUARIO NO SEA EL PRIMERO Y LO ASIGNAMOS
        if(jugadorDos!=null){
        if((ConnectFour.getJugadorUno().getUsuario().equals(jugadorDos.getUsuario()))){
            System.out.println("Error, el segundo jugador no puede ser igual al primero");
            jugadorDos=null;
        }
        else{
            imprimirTablero();
	do{
		//IDENTIFICAMOS DE QUIEN ES EL TURNO PARA PONER MARCA:
		if(turno){
			marca = 'U';
			currentPlayer = getJugadorUno();
		}
		else{
			currentPlayer = getJugadorDos();
			marca = 'D';
		}

		//TURNO JUGADOR, ACABA DESPUES DE COLOCAR LA FICHA
		System.out.print("Jugador " + currentPlayer.getUsuario() + " ingrese el numero de columna en la que desea colocar su ficha: ");
		donde = lea.nextInt()-1;
                //VERIFICAMOS QUE LA OPCION INGRESADA NO SEA RETIRARSE
		if(donde==-2){
                    System.out.print("Desea Retirarse? [SI/NO]: ");
                        String resp = lea.next();
                        if (resp.equalsIgnoreCase("SI")) {
			retirarse = true;
			System.out.println("Jugador " + currentPlayer.getUsuario() + " has perdido, por retiro");
                        if(currentPlayer==jugadorUno){
                        jugadorUno.derrotas++;
                        jugadorDos.puntos+=3;
                        jugadorDos.victorias++;
                        }
                        else{
                        jugadorDos.derrotas++;
                        jugadorUno.puntos+=3;
                        jugadorUno.victorias++;
                        }
		}else{
                     retirarse = false;
                     turno=(!turno); 
                    }
                }

		// VALIDAMOS QUE ESCOJA UNA FILA EXISTENTE
                if((donde>=0 && donde<=6) || donde == -2){
		// BUSCAMOS LA FILA VACIA MAS PROXIMA VACIA Y LA LLENAMOS
                for(int f=tablero.length-1; f>=0; f--){
		columnallena=false;
		if(donde!=-2){
		if(tablero[f][donde] == '_'){
		// COLOCAMOS LA MARCA EN LA CASILLA SELECCIONADA
			tablero[f][donde] = marca;
                        imprimirTablero();
                        //LUEGO DE COLOCARLA VERIFICAMOS SI ESTA MARCA PROVOCO QUE HAYA UN GANADOR
                        ganador=buscarGanador();
			break;
		}

		if (f==0){
			columnallena=true;
		}
		}
	}
	}
		else{
		System.out.println("Numero de columna erronea, elija un numero entre 1 y 7");
		// PARA CONSERVAR EL TURNO, PUESTO QUE NO HA COLOCADO FICHA
		turno=(!turno);
		}



		// MANDAR UN MENSAJE DE ERROR SI ESTA LLENA LA COLUMNA Y PEDIR OTRA
		if(columnallena){
			System.out.println("Columna llena, escoja otra.");
		// PARA CONSERVAR EL TURNO, PUESTO QUE NO HA COLOCADO FICHA
		turno=(!turno);
		}       
                
	// EL TURNO CONCLUYE, CAMBIAMOS DE JUGADOR Y SUMAMOS 1 A LA VARIABLE TURNOS JUGADOS
	turno=(!turno);
	turnosJugados++;
	//CUANDO ESTA VARIBLE TURNOS JUGADOS SEA IGUAL A 48, LOS 48 ESPACIOS DEL TABLERO ESTARAN LLENOS, ENTONCES HABRA EMPATE:
	if(turnosJugados==42){
        System.out.println("Juego terminado en empate.");
        jugadorUno.empates++;
        jugadorDos.empates++;
        jugadorDos.puntos++;
        jugadorUno.puntos++;
	empate=true;
	}
	}while(!ganador && !empate && !retirarse);

        }
        }
        turnosJugados=0;
        if(jugadorDos!=null){
        setUltimaPartida(jugadorUno,jugadorDos);
        }
        if(jugadorUno!=null&&jugadorDos!=null){
            players[flagPosUno]=jugadorUno;
            players[flagPosDos]=jugadorDos;
        }
        menuPrincipal(jugadorUno);
    }

    public static void setJugadorUno(Player jugadorUno) {
        ConnectFour.jugadorUno = jugadorUno;
    }

    public static void setJugadorDos(Player jugadorDos) {
        ConnectFour.jugadorDos = jugadorDos;
    }

    public static boolean buscarGanador(){
        		//HORIZONTAL:
		for(int f=0; f<= 5; f++){
			for(int c=0; c<=3; c++){
				if(tablero[f][c]==tablero[f][c+1] && tablero[f][c+1]==tablero[f][c+2] && tablero[f][c+2]==tablero[f][c+3] && tablero[f][c]!='_'){
					System.out.println("Felicidades jugador " + currentPlayer.getUsuario() + ", haz ganado por una linea horizontal.");
                                        if(currentPlayer==jugadorUno){
                                            jugadorUno.victorias++;
                                            jugadorUno.puntos=+3;
                                            jugadorDos.derrotas++;
                                        }
                                        else{
                                            jugadorUno.derrotas++;
                                            jugadorDos.victorias++;
                                            jugadorDos.puntos=+3;
                                        }
				return true;
			}
			}
		}

		//VERTICAL
		for(int f=0; f<= 2; f++){
			for(int c=0; c<=6; c++){
				if(tablero[f][c]==tablero[f+1][c] && tablero[f+1][c]==tablero[f+2][c] && tablero[f+2][c]==tablero[f+3][c] && tablero[f][c]!='_'){
					System.out.println("Felicidades jugador " + currentPlayer.getUsuario() + ", haz ganado por una linea vertical.");
                                        if(currentPlayer==jugadorUno){
                                            jugadorUno.victorias++;
                                            jugadorUno.puntos=+3;
                                            jugadorDos.derrotas++;
                                        }
                                        else{
                                            jugadorUno.derrotas++;
                                            jugadorDos.victorias++;
                                            jugadorDos.puntos=+3;
                                        }
				return true;
			}
			}
		}

		//DIAGONAL
			int flagdiagonales = -1;
                // EN  BUSQUEDA DE DIAGONALES DE IZQUIERDA A DERECHA
                for(int f=0; f<=2; f++){
				for(int c =0; c<=3; c++){
                for (int i = 0; i < 4; i++) {

                	if(flagdiagonales==tablero[f+i][c+i]&& tablero[f][c]!='_'){
                		coincidencias++;
                		if(coincidencias==3){
                			System.out.println("Felicidades jugador " + currentPlayer.getUsuario() + ", haz ganado por diagonal de izq a derecha.");
                                        if(currentPlayer==jugadorUno){
                                            jugadorUno.victorias++;
                                            jugadorUno.puntos=+3;
                                            jugadorDos.derrotas++;
                                        }
                                        else{
                                            jugadorUno.derrotas++;
                                            jugadorDos.victorias++;
                                            jugadorDos.puntos=+3;
                                        }
                                coincidencias=0;
				return true;
                		}
                	}
                	else{
                		coincidencias=0;
                	}
                	flagdiagonales=tablero[f][c];
                }
                }
            }

                // EN  BUSQUEDA DE DIAGONALES DE DERECHA A IZQUIERDA
                for(int f=0; f<=2; f++){
				for(int c =3; c<=6; c++){
                for (int i = 0; i < 4; i++) {

                	if(flagdiagonales==tablero[f+i][c-i]&& tablero[f][c]!='_'){
                		coincidencias++;
                		if(coincidencias==3){
                			System.out.println("Felicidades jugador "+ currentPlayer.getUsuario() + ", haz ganado por diaginal de derecha a izq.");
                                        if(currentPlayer==jugadorUno){
                                            jugadorUno.victorias++;
                                            jugadorUno.puntos=+3;
                                            jugadorDos.derrotas++;
                                        }
                                        else{
                                            jugadorUno.derrotas++;
                                            jugadorDos.victorias++;
                                            jugadorDos.puntos=+3;
                                        }
                                coincidencias=0;
				return true;
                		}
                	}
                	else{
                		coincidencias=0;
                	}
                	
                	flagdiagonales=tablero[f][c];
                }
            }
        }
                return false;
    }

    public static void setUltimaPartida(Player JugadorUno, Player JugadorDos) {
            //DESPLAZAMOS LAS ULTIMAS PARTIDAS HACIA ABAJO EN UNA POSICIÓN
        
            for(int c=9; c>0;c--){
                if(jugadorUno.registro[c-1] != null){
                jugadorUno.registro[c]=jugadorUno.registro[c-1];
                }
                if(jugadorDos.registro[c-1]!=null){
                jugadorDos.registro[c]=jugadorDos.registro[c-1];
                }
            }
        //SI GANA JUGADOR UNO
        if(currentPlayer==jugadorUno&&ganador==true){
            //GUARDAMOS EN EL PRIMER ESPACIO
            jugadorUno.registro[0]=("Ganó vs " + jugadorDos.getUsuario());
            jugadorDos.registro[0]=("Perdió vs " + jugadorUno.getUsuario());
        }
        //SI GANA JUGADOR DOS
        else if (ganador==true){
            //GUARDAMOS EN EL PRIMER ESPACIO
            jugadorUno.registro[0]=("Perdió vs " + jugadorDos.getUsuario());
            jugadorDos.registro[0]=("Ganó vs " + jugadorUno.getUsuario());
        }
        //SI EMPATAN:
        if(empate==true){
            //GUARDAMOS EN EL PRIMER ESPACIO
            jugadorUno.registro[0]=("Empató vs " + jugadorDos.getUsuario());
            jugadorDos.registro[0]=("Empató vs " + jugadorUno.getUsuario());
        }
        //SI UNO DE LOS DOS SE RETIRA:
        if(retirarse==true&&currentPlayer==jugadorUno){
            //GUARDAMOS EN EL PRIMER ESPACIO
            jugadorUno.registro[0]=("Perdió vs " + jugadorDos.getUsuario() + " por retiro.");
            jugadorDos.registro[0]=("Ganó vs " + jugadorUno.getUsuario() + " por retiro.");
        }
        else if(retirarse==true){
            jugadorUno.registro[0]=("Ganó vs " + jugadorDos.getUsuario() + " por retiro.");
            jugadorDos.registro[0]=("Perdió vs " + jugadorUno.getUsuario() + " por retiro.");
        }
    }

    public static void returnRanking(){
        int pl_existentes=0;
        //contamos el numero de usuarios existentes
        for(int c=0; c<players.length; c++){
            if(players[c]!=null){
                pl_existentes++;
            }
        }
    String ranking[] = new String[pl_existentes];
    // ORDENAMOS DE MAYOR A MENOR CANTIDAD DE PUNTOS
    for(int c = 0; c<pl_existentes-1; c++){
        for(int k = c+1; k<pl_existentes; k++){
            if(players[c].getPuntos()<players[k].getPuntos()){
                Player variableauxiliar=players[c];
                players[c]=players[k];
                players[k]=variableauxiliar;
            }
        }
    }
//LLENAMOS EL ARREGLO RANKING
    for(int k =0; k<ranking.length; k++){
       ranking[k]=(""+(k+1)+". Nombre: "+players[k].getNombre()+"\n Usuario: "+players[k].getUsuario()+"\n Puntos: "+players[k].getPuntos()+"\n");
    }

    // LO IMPRIMIMOS
        System.out.println("\t RANKING GENERAL:");
    for(int k =0; k<ranking.length; k++){
        System.out.println(ranking[k]);
    }
}

    public static void printLog(){
        System.out.println("Ultimas diez partidas del usuario "+currentPlayer.getUsuario());
        for(int c = 0; c<currentPlayer.getRegistro().length;c++){
            if(currentPlayer.registro[c]!=null){
            System.out.println(currentPlayer.registro[c]);
            }
        }

    }
     
    public static void menuEditar(){
        int editar;
        do {                            
            System.out.print("\n\tQue dato de su cuenta desea editar? \n\n\t1-. Nombre\n\t2-. Usuario\n\t3-. Contrasena\n\t4-. Salir\n\n\tElija una Opcion: ");
            editar=lea.nextInt();
            switch (editar){
                case 1:
                    System.out.print("Ingrese su nuevo nombre: ");
                    currentPlayer.setNombre(lea.next());
                    break;
                case 2:
                    System.out.print("Ingrese su nuevo usuario: ");
                    currentPlayer.setUsuario(lea.next());
                    break;
                case 3:
                    System.out.print("Ingrese su nueva contrasena: ");
                    currentPlayer.setContrasena(lea.next());
                    break;
                default:
                    if(editar==4){
                        break;
                    }
                    else{
                        System.out.println("Elección incorrecta");
                    break;
                }
            }
        } while (editar != 4);
    }
    
    public static void eliminarCuenta(){
        String resp;
        System.out.print("Desea eliminar Cuenta? [SI/NO]: ");
        resp = lea.next();
        
        if (resp.equalsIgnoreCase("Si")) {
            jugadorUno=null;
            System.out.println("Cuenta eliminada");
            players[flagPosUno]=null;
            salirMenu = true;//el flag cambia a true y abajo en el while nos saca del menu MI PERFIL
            salir=true;//salir nos manda al menu inicial  
        }
    }
}
package SocketsHilo;
//Código obtenido de http://www.it.uc3m.es/celeste/docencia/cr/2003/PracticaSocketsTCP/

import java.net.*;
import java.io.*;


public class ServidorTCP 
{
	//TODO Generar estructura para guardar objetos de tipo GestorClienteHilo (nombre almacenHilos)
	
	
	public static void main(String[] args) throws IOException 
	{
		//TODO Inicializar almacenHilos
		
		
		
		
		ServerSocket socketServidor = null;
		try {
			socketServidor = new ServerSocket(4444);
		} catch (IOException e) {
			System.out.println("No puede escuchar en el puerto: " + 4444);
			System.exit(-1);
		}


		boolean parar=false;
		do {
			System.out.println("Escuchando: " + socketServidor);

			Socket socketCliente=socketServidor.accept();
			GestorClienteHilo nuevoHilo;
			(nuevoHilo=new GestorClienteHilo(socketCliente)).start();
			
			//TODO Añadir nuevoHilo al AlmacenHilos 

		}while(!parar);

		socketServidor.close();
	}	
	
	
	public static void enviarMensajeATodos(String mensaje)
	{
		//TODO Recorrer estructura almacenHilos
			//TODO Por cada hilo almacenado llamar al método enviarMensaje
		
		
	}

	private static class GestorClienteHilo extends Thread
	{
		private Socket socketCliente;
		private PrintWriter salidaSocket = null;
		
		public GestorClienteHilo(Socket sock)
		{
			this.socketCliente=sock;
		}

		@Override
		public void run()
		{
			BufferedReader entradaSocket = null;

			try
			{
				System.out.println(this.getName()+" Conexión aceptada: "+ socketCliente);
				
				entradaSocket = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
				salidaSocket = new PrintWriter(new BufferedWriter(new  OutputStreamWriter(socketCliente.getOutputStream())),true);

				String mensaje;
				do{  
					mensaje = entradaSocket.readLine();//Se lee del cliente

					System.out.println(this.getName()+" Recibido: "+mensaje);
					
					//TODO cambiar enviarMensaje por enviarMensajeTodos
					
					enviarMensaje(mensaje);
					
					
					//enviarMensaje(mensaje.toUpperCase());
					//enviarMensaje(mensaje.toLowerCase());
					
					
					
				}while(!mensaje.equals("Adios"));

			} catch (IOException e) {
				System.out.println("IOException: " + e.getMessage());
			} 
			try { 
				salidaSocket.close();
				entradaSocket.close();
				socketCliente.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//TODO Eliminar el hilo actual (this) del almacenHilos
		}

		public void enviarMensaje(String elmensaje)
		{
			salidaSocket.println(elmensaje);			
		}
	}
}

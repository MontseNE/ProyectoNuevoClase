package SocketsHilo;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteTCP 
{
	private static PrintWriter salidaSocket = null;

	public static void main(String[] args)  throws IOException 
	{
	    Socket socketCliente = null;
	    BufferedReader entradaSocket = null;
	    HiloLecturaMensajeServidor hiloescucha=null;

	    try {
	      socketCliente = new Socket("localhost", 4444);

	      System.out.println("Cliente conectado: "+socketCliente);
	      
	      entradaSocket = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
	      salidaSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())),true);
	
	      
	      (hiloescucha=new HiloLecturaMensajeServidor(entradaSocket)).start();
	      
	    } catch (IOException e) 
	    {
		System.err.println("No puede establer canales de E/S para la conexión");
	        System.exit(-1);
	    }
	    Scanner teclado = new Scanner(System.in);

	    String lineaServidor, lineaTeclado;

	    do 
	    {
	    	  System.out.print("Introduce el mensaje a enviar: ");
	    	  lineaTeclado = teclado.nextLine();
	    	  enviarMensaje(lineaTeclado);
		}while (!lineaTeclado.equals("Adios"));
	    


	    hiloescucha.pararEscucharMensajes();
	    salidaSocket.close();
	    entradaSocket.close();
	    teclado.close();
	    socketCliente.close();
	    
	    System.out.println("Finalizo");
	}
	
	public static void enviarMensaje(String mensaje)
	{

	      salidaSocket.println(mensaje);
	}
	
	public static class HiloLecturaMensajeServidor extends Thread
	{
		private BufferedReader entradaSocket = null;
		private boolean escucharMensajes=true;
		
		public void pararEscucharMensajes()
		{
			escucharMensajes=false;
		}
		
		public HiloLecturaMensajeServidor(BufferedReader entrada)
		{
			this.entradaSocket=entrada;
		}
		
		@Override
		public void run() 
		{
			while(escucharMensajes==true)
			{
				try
				{
			        String lineaServidor = entradaSocket.readLine();
			        System.out.println("Respuesta servidor: '" + lineaServidor+"'");	    
				}catch(Exception e)	{	}
			}
		}
	}

}

package teste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
	public static int PORTA = 12345;

	public static void main(String[] args) {
		InputStream in;
		try {
			ServerSocket server = new ServerSocket(PORTA);
			while (true) {
				System.out.println("Aguardando conexão...");
				Socket socket = server.accept();
				System.out.println("Conectado com " +
						socket.getInetAddress().getHostAddress());

				byte[] buffer = new byte[1024];
				in = socket.getInputStream();
				int bytesLidos = in.read(buffer);

				System.out.println(bytesLidos + ": "+new String(buffer));

				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

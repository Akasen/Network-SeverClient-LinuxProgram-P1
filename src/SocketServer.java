import java.io.*;
import java.net.*;

//SINGLE-THREADED VERSION

public class SocketServer {
	public static final int PORT_NUMBER = 5000;

	protected Socket socket;

	private SocketServer(Socket socket) {
		this.socket = socket;
		System.out.println("Client Connected!");
		//start();
		run();
	}

	public void run() {
		// takes input from the client socket
		InputStream in = null;
		OutputStream out = null;
		BufferedReader input;

		// long startTime = System.currentTimeMillis();
		// long elapsedTime;

		String line = "";
		String reply;

		Process p;
		try {
			System.out.println("Waiting for Clients...");

			// BufferedReader input = null;

			in = socket.getInputStream();
			out = socket.getOutputStream();

			input = null;

			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			PrintWriter write = new PrintWriter(out, true);

			p = null;
			// End Variable Instantiations

			line = read.readLine();

			printOptions(line);

			input = executeOption(line, p, input);

			if (line == "7") {
				write.println("Closing Connection");
			} else if (line == "er") {
				write.println("Invalid Choice");
			} else {

				while ((reply = input.readLine()) != null) {
					write.println(reply); // <-- Print all Process here line by line
				}

			}
			/*
			 * socket.close(); in.close(); out.close();
			 */

		}

		catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
			System.out.println("Unable to get streams from client");

		} finally {
			try {
				in.close();
			}
			catch(IOException e){
				System.out.println(e);
			}
			try {
				out.close();
			}
			catch(IOException e){
				System.out.println(e);
			}
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println("There was an error closing the socket");
				e.printStackTrace();
			}

		}

	}

	public static BufferedReader executeOption(String line, Process p, BufferedReader input) {
		try {
			switch (line) {
			case "1":
				p = Runtime.getRuntime().exec("date");
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));

				break;
			case "2":

				p = Runtime.getRuntime().exec("uptime");
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));

				break;
			case "3":

				p = Runtime.getRuntime().exec("free");
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				break;
			case "4":

				p = Runtime.getRuntime().exec("netstat");
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));

				break;
			case "5":

				p = Runtime.getRuntime().exec("who");
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));

				System.out.println("Sent: " + p);
				break;
			case "6":

				p = Runtime.getRuntime().exec("ps -e");
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				break;
			case "7":
				System.out.println("Closing Connection");
				line = "7";
				break;
			default:
				line = "er";
				break;
			}
		} catch (IOException e) {
			System.out.println(e);
		}

		return input;
	}

	public static void printOptions(String line) {
		switch (line) {
		case "1":
			System.out.println("User requested date and time.");
			break;
		case "2":
			System.out.println("User requested uptime.");
			break;
		case "3":
			System.out.println("User requested memory usage.");
			break;
		case "4":
			System.out.println("User requested netstat.");
			break;
		case "5":
			System.out.println("User requested current users.");
			break;
		case "6":
			System.out.println("User requested running processes.");
			break;
		default:
			;
			break;
		}
	}

	public static void main(String[] args) {
		System.out.println("Server SOFTWARE has been started");

		ServerSocket server = null;
		
		try {
			server = new ServerSocket(PORT_NUMBER);

			while (true) {
				new SocketServer(server.accept());
			}
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Unable to start server");
		}

		finally {
			try {
				if (server != null) {
					server.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Sever SOFTWARE has been closed");
	}
}

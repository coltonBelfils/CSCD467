import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *  * A private thread to handle capitalization requests on a particular
 *  * socket.  The client terminates the dialogue by sending a single line
 *  * containing only a period.
 *  
 */
public class Capitalizer extends Thread {
    private Socket socket;
    private int clientNumber;
    private ThreadManager manager;

    public Capitalizer(ThreadManager manager) {
        this.manager = manager;
    }

    /**
     *  * Services this thread's client by first sending the
     *  * client a welcome message then repeatedly reading strings
     *  * and sending back the capitalized version of the string.
     *  
     */
    public void run() {
        while (true) {
            if (socket == null) {
                synchronized (this) {
                    try {
                        this.wait();
                        continue;
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
            try {
                log("New connection with client# " + clientNumber + " at " + socket);

                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Hello, you are client #" + clientNumber + ".");
                out.println("Enter a line with only a period to quit\n");

                // Get messages from the client, line by line; return them
                // capitalized
                while (!Thread.interrupted()) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        manager.capitalizerFinished(this);
                    } else if (input.toLowerCase().equals("kill")) {
                        manager.stopPool();
                        return;
                    } else if (input.toLowerCase().matches("add,[0-9],[0-9]")) {
                        try {
                            String[] parts = input.split(",");
                            int num1 = Integer.parseInt(parts[1]);
                            int num2 = Integer.parseInt(parts[2]);
                            out.println(num1 + num2);
                        } catch (Exception ignored) {

                        }
                    } else if (input.toLowerCase().matches("sub,[0-9],[0-9]")) {
                        try {
                            String[] parts = input.split(",");
                            int num1 = Integer.parseInt(parts[1]);
                            int num2 = Integer.parseInt(parts[2]);
                            out.println(num1 - num2);
                        } catch (Exception ignored) {

                        }
                    } else if (input.toLowerCase().matches("mult,[0-9],[0-9]")) {
                        try {
                            String[] parts = input.split(",");
                            int num1 = Integer.parseInt(parts[1]);
                            int num2 = Integer.parseInt(parts[2]);
                            out.println(num1 * num2);
                        } catch (Exception ignored) {

                        }
                    } else if (input.toLowerCase().matches("div,[0-9],[0-9]")) {
                        try {
                            String[] parts = input.split(",");
                            int num1 = Integer.parseInt(parts[1]);
                            int num2 = Integer.parseInt(parts[2]);
                            out.println(num1 / num2);
                        } catch (Exception ignored) {

                        }
                    } else {
                        out.println(input.toUpperCase());
                    }
                }
                Thread.sleep(50);
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
                return;
            } catch (InterruptedException ignored) {

            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
                manager.capitalizerFinished(this);
            }
        }
    }

    public void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {

        }
    }

    public synchronized void setClient(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        this.notify();
    }

    /**
     *  * Logs a simple message.  In this case we just write the
     *  * message to the server applications standard output.
     *  
     */
    private void log(String message) {
        System.out.println(message);
    }
}
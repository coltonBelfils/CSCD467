import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
  * A private thread to handle capitalization requests on a particular
  * socket.  The client terminates the dialogue by sending a single line
  * containing only a period.
  */
public class Capitalizer extends Thread {
    private Socket socket;
    private int clientNumber;
    private boolean active = false;

    /**
      * Services this thread's client by first sending the
      * client a welcome message then repeatedly reading strings
      * and sending back the capitalized version of the string.
      */
    public void run() {
        while (true) {
            try {
                if (active) {
                    activate();
                    if (Thread.interrupted()) {
                        return;
                    } else {
                        active = false;
                    }
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void activate(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        log("New connection with client# " + clientNumber + " at " + socket);
        this.active = true;
    }

    private void activate() {
        try {

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
                    break;
                }
                out.println(input.toUpperCase());
            }
        } catch (IOException e) {
            log("Error handling client# " + clientNumber + ": " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log("Couldn't close a socket, what's going on?");
            }
            log("Connection with client# " + clientNumber + " closed");
        }
    }

    /**
      * Logs a simple message.  In this case we just write the
      * message to the server applications standard output.
      */
    private void log(String message) {
        System.out.println(message);
    }
}
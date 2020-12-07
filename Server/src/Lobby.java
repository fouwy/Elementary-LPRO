import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Lobby {

    private final int port;
    private final String host;
    private boolean waitingToStart;
    private static Set<String> names = new HashSet<>();
    private static Set<PrintWriter> writers = new HashSet<>();

    public Lobby(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        waitingToStart = true;
        startLobby();
    }

    private void startLobby() throws IOException {
        try (ServerSocket listener = new ServerSocket(port)) {
            System.out.println("Lobby Server Is Running...");
            ExecutorService pool = Executors.newFixedThreadPool(6);
            while (waitingToStart) {
                pool.execute(this.new Player(listener.accept()));   //maybe change "this" to another class
                System.out.println("User connected");
            }
        }
    }

    class Player implements Runnable {
        private String name;
        private Socket socket;
        private Scanner input;
        private PrintWriter output;
        private int[] characters;
        public Player(Socket socket) {
            this.socket = socket;
            characters = new int[6];
        }

        @Override
        public void run() {
            try {
                setup();
                processCommands();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void processCommands() {
            //TODO: in the beginning send array so everyone knows which chars are taken
            name = input.nextLine();
            if (name == null)
                return;
            synchronized (names) {
                names.add(name);
            }
            output.println("Welcome " + name);
            broadcast(name + "has joined.");
            writers.add(output);

            while(input.hasNextLine()) {
                String command = input.nextLine();
                if (command.startsWith("CHAR")) {
                    System.out.println(command);
                    int characterNumber = Integer.parseInt(String.valueOf(command.charAt(4)));

                    if (characters[characterNumber - 1] == 0) {
                        broadcast("CHAR"+characterNumber+name);
                    }
                }

            }
        }

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        }
    }

    private void broadcast(String message) {
        for (PrintWriter writer : writers) {
            writer.println(message);
        }
    }


}

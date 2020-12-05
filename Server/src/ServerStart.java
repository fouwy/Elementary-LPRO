
public class ServerStart {
    public static void main(String[] args) {
        Server server;
        server = new Server();
        server.startRunning();
    }

    private static void printNumberAndNameOfMembers(Database database) {
        System.out.println("Number of members: " + database.getAllMembers().size());
        System.out.println(database.getAllMembers().toString());
    }
}

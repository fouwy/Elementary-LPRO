
public class ServerStart {
    public static void main(String[] args) {
        Server server;
        server = new Server();
        server.startRunning();

//        String[] accountInfo = {"pilantra2", "pass", null};
//        database.registerNewUser(accountInfo);
//
//        printNumberAndNameOfMembers(database);
//
//        database.deleteUser("pilantra2");
//
//        printNumberAndNameOfMembers(database);

    }

    private static void printNumberAndNameOfMembers(Database database) {
        System.out.println("Number of members: " + database.getAllMembers().size());
        System.out.println(database.getAllMembers().toString());
    }
}

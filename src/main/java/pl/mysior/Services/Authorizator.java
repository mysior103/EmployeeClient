package pl.mysior.Services;

import static pl.mysior.InputScanner.userInput;

public class Authorizator {
    public static String authorize() {
        boolean correctAuth = false;
        String authKey = null;
        while (!correctAuth) {
            System.out.println("Podaj użytkownika: \t\t");
            String user = userInput();
            System.out.println("Podaj hasło: \t\t\t");
            String password = userInput();
            authKey = getAuthKeyThrowRMI(user, password);
            if (authKey == null) {
                correctAuth = false;
                System.out.println("Błąd autoryzacji, spróbuj ponownie!");
            } else {
                correctAuth = true;
                System.out.println("\nZalogowano pomyslnie!\n----------------------------------------");
            }
        }
        return authKey;
    }

    private static String getAuthKeyThrowRMI(String userName, String password) {
        RMIServiceClient rmiClient = new RMIServiceClient();
        String authKey = rmiClient.getAuthKey(userName, password);
        return authKey;
    }

}

package it.polimi.ingsw.Controller;

public class InputController {

    public static boolean isValidIpAddress(String ip) {
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return ip.matches(regex);
    }

    public static boolean isValidPort(int portStr) {
        try {
            return portStr >= 1 && portStr <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

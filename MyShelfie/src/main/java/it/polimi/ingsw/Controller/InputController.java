package it.polimi.ingsw.Controller;

/**
 * Class used to check if the IP address and the port are valid
 */
public class InputController {

    /**
     * Method to check if the IP address passed as a parameter is valid using a regular expression.
     * @param ip the IP address
     * @return true if the address passed is valid, false otherwise
     */
    public static boolean isValidIpAddress(String ip) {
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return ip.matches(regex);
    }

    /**
     * Method to check if the port passed as a parameters is valid or not by checking if it is in the range of 1 and 65535.
     * @param portStr the port of the socket
     * @return
     */
    public static boolean isValidPort(int portStr) {
        try {
            return portStr >= 1 && portStr <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

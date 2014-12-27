package at.geyerritter.dezsys07;

import at.geyerritter.dezsys07.balancer.CalculatorBalancer;
import at.geyerritter.dezsys07.client.*;
import at.geyerritter.dezsys07.server.CalculatorServer;
import at.geyerritter.dezsys07.server.Server;
import org.apache.commons.cli.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * In dieser Klasse werden die Argumente aus der Kommandozeilen validiert.
 * Danach wird entweder ein Client, ein Balancer oder ein Server gestartet.
 *
 * @author sgeyer
 * @author mritter
 *
 * @version 1.0
 */
public class Main {

    private static Logger logger = LogManager.getLogger(Main.class.getName());
    private static int balancerPort;
    private static String programType, balancerIP;


    public static void main(String[] args) {

        try {
            if (parseArgs(args)) {

                if (programType.equalsIgnoreCase("Balancer"))
                    new CalculatorBalancer(balancerPort);

                else if (programType.equalsIgnoreCase("Server")) {

                    Server s = new CalculatorServer(balancerIP, balancerPort, balancerPort);
                    s.registerAtRegistry();
                    Thread t = new Thread(s);
                    Runtime.getRuntime().addShutdownHook(t);

                } else if (programType.equalsIgnoreCase("Client")) {

                    InputOutput io = new ConsoleIO();
                    NetworkController nc = new CalculatorController(io);
                    Thread client = new Thread(new CalculatorClient(io, nc));
                    client.start();

                    nc.connect(balancerIP, balancerPort);

                }

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Verarbeiten der eingegebenen Argumente.
     * Initialisieren des DBConnectionCreators, des DBSelectCreators und des Displays.
     *
     * @param args Kommandozeilenagrumente
     * @return Ob Argumente korrekt
     */
    public static boolean parseArgs(String[] args) {

        //Initialisieren der Optionen, HelpFormatter fuer Hilfe, CommandLineParser fuer das Parsen der Argumente
        Options options = getOptions();
        HelpFormatter hf = new HelpFormatter();
        CommandLineParser parser = new BasicParser();

        try {

            CommandLine cmd = parser.parse(options, args);

            //Option -t validieren
            if (!(cmd.getOptionValue("t").equalsIgnoreCase("Client") || cmd.getOptionValue("t").equalsIgnoreCase("Balancer") || cmd.getOptionValue("t").equalsIgnoreCase("Server")))
                throw new ParseException("Es muss entweder \"Client\", \"Balancer\" oder \"Server\" bei der Option -t angegeben werden!");
            else
                programType = cmd.getOptionValue("t");


            //Option -h validieren
            if (cmd.getOptionValue("h") != null) {

                String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
                Pattern valid_ipv4_pattern = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);

                Matcher m1 = valid_ipv4_pattern.matcher((cmd.getOptionValue("h")));
                if (!m1.matches())
                    throw new ParseException("Es muss eine gueltige IPv4 Adresse bei der Option -h eingegeben werden!");
                else
                    balancerIP = cmd.getOptionValue("h");

            } else
                balancerIP = "127.0.0.1";




            //Option -p validieren
            if ((cmd.getOptionValue("p") != null)) {
                try {
                    if (Integer.parseInt((cmd.getOptionValue("p"))) < 0 || Integer.parseInt((cmd.getOptionValue("p"))) > 65535)
                        throw new ParseException("Es muss eine gueltiger Port bei der Option -h eingegeben werden!");
                } catch (Exception e) {
                    throw new ParseException("Es muss eine gueltiger Port bei der Option -h eingegeben werden!");
                }
            } else
                balancerPort = 1099;


            return true;

        } catch (ParseException e) {
            //Bei einem Fehler wahrend des Parsens wird eine Fehlermeldung ausgegeben
            logger.error(e.getMessage());
            hf.printHelp("java -jar PiCalculator.jar", options);
            return false;
        }

    }

    /**
     * Die Optionen zur Verarbeitung der Kommandozeilenargumente
     *
     * @return Optionen
     */
    @SuppressWarnings("AccessStaticViaInstance")
    private static Options getOptions() {

        //Options-Objekt initialisieren und anschliessend alle Optionen hinzufuegen
        Options options = new Options();

        options.addOption(OptionBuilder
                .hasArg(true)
                .withDescription("Typ: Client, Balancer oder Server")
                .isRequired()
                .create("t"));

        options.addOption(OptionBuilder
                .hasArg(true)
                .withDescription("Host-Adresse des Balancers. Standard: Localhost")
                .create("h"));

        options.addOption(OptionBuilder
                .hasArg(true)
                .withDescription("Port des Balancers. Standard: 1099")
                .create("p"));

        return options;

    }


}
package at.geyerritter.dezsys07.client;

public class CalculatorClient implements Runnable {

    private InputOutput io;
    private NetworkController nc;

    public CalculatorClient(InputOutput io, NetworkController nc) {
        this.io = io;
        this.nc = nc;
    }

    @Override
    public void run() {
        while(true) {
            try {
                int parameter = Integer.parseInt(io.readContent());
                nc.Request(parameter);
            } catch (NumberFormatException e) {
                System.out.println("Geben Sie eine Zahl ein, um die Abfrage zu starten");
            }

        }
    }
}

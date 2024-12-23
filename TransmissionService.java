public class TransmissionService extends Service {

    private static final double TRANSMISSION_FLUID_PRICE = 2000, SERVICE_CHARGE = 1000;
    protected int transmissionFluidQuantity;
    protected double tranmissionFluidPrice;

    public TransmissionService(AccountProfile account, Vehicle vehicle, int fluidQuant) {

        super("Transmission Service", SERVICE_CHARGE, account, vehicle);
        transmissionFluidQuantity = fluidQuant;
        tranmissionFluidPrice = TRANSMISSION_FLUID_PRICE;
    }

    public TransmissionService(double amount, AccountProfile account, Vehicle vehicle, int fluidQuant) {

        super("Transmission Service", amount, account, vehicle);
        transmissionFluidQuantity = fluidQuant;
        tranmissionFluidPrice = TRANSMISSION_FLUID_PRICE;
    }

    public TransmissionService(AccountProfile account, Vehicle vehicle, int fluidQuant, double fluidPrice) {

        super("Transmission Service", SERVICE_CHARGE, account, vehicle);
        transmissionFluidQuantity = fluidQuant;
        tranmissionFluidPrice = fluidPrice;
    }

    public TransmissionService(double amount, AccountProfile account, Vehicle vehicle, int fluidQuant,
            double fluidPrice) {

        super("Transmission Service", amount, account, vehicle);
        transmissionFluidQuantity = fluidQuant;
        tranmissionFluidPrice = fluidPrice;
    }

    public int getTransmissionFluidQuantity() {

        return transmissionFluidQuantity;
    }

    public double getTransmissionFluidPrice() {

        return tranmissionFluidPrice;
    }

    public void setTransmissionFluidQuantity(int quantity) {

        transmissionFluidQuantity = quantity;
    }

    public void setTransmissionFluidPrice(double amount) {

        tranmissionFluidPrice = amount;
    }

    public double itemCharge() {

        return transmissionFluidQuantity * tranmissionFluidPrice;
    }

    public double totalCost() {

        return totalPayment = serviceCharge + itemCharge();
    }
}

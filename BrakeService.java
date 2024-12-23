public class BrakeService extends Service {

    private static final double BREAK_PADS_PRICE = 1000.00, BREAK_ROTORS_PRICE = 1750, BREAK_FLUID1L_PRICE = 680,
            SERVICE_CHARGE = 800;
    protected int brakekPadsQuantity, brakeRotorsQuantity, brakeFluid1LQuantity;
    protected double brakePadsPrice, brakeRotorsPrice, brakekFluid1LPrice;

    public BrakeService(AccountProfile account, Vehicle vehicle, int padsQuant, int rotorsQuant, int fluidQuant) {

        super("Brake Maintenance", SERVICE_CHARGE, account, vehicle);

        brakekPadsQuantity = padsQuant;
        brakeRotorsQuantity = rotorsQuant;
        brakeFluid1LQuantity = fluidQuant;
        brakePadsPrice = BREAK_PADS_PRICE;
        brakeRotorsPrice = BREAK_ROTORS_PRICE;
        brakekFluid1LPrice = BREAK_FLUID1L_PRICE;
    }

    public BrakeService(double amount, AccountProfile account, Vehicle vehicle, int padsQuant, int rotorsQuant,
            int fluidQuant) {

        super("Brake Maintenance", amount, account, vehicle);

        brakekPadsQuantity = padsQuant;
        brakeRotorsQuantity = rotorsQuant;
        brakeFluid1LQuantity = fluidQuant;
        brakePadsPrice = BREAK_PADS_PRICE;
        brakeRotorsPrice = BREAK_ROTORS_PRICE;
        brakekFluid1LPrice = BREAK_FLUID1L_PRICE;
    }

    public BrakeService(AccountProfile account, Vehicle vehicle, int padsQuant, int rotorsQuant, int fluidQuant,
            double padsPrice, double rotorsPrice, double fluidPrice) {

        super("Brake Maintenance", SERVICE_CHARGE, account, vehicle);

        brakekPadsQuantity = padsQuant;
        brakeRotorsQuantity = rotorsQuant;
        brakeFluid1LQuantity = fluidQuant;
        brakePadsPrice = padsPrice;
        brakeRotorsPrice = rotorsPrice;
        brakekFluid1LPrice = fluidPrice;
    }

    public BrakeService(double amount, AccountProfile account, Vehicle vehicle, int padsQuant, int rotorsQuant,
            int fluidQuant,
            double padsPrice, double rotorsPrice, double fluidPrice) {

        super("Brake Maintenance", amount, account, vehicle);

        brakekPadsQuantity = padsQuant;
        brakeRotorsQuantity = rotorsQuant;
        brakeFluid1LQuantity = fluidQuant;
        brakePadsPrice = padsPrice;
        brakeRotorsPrice = rotorsPrice;
        brakekFluid1LPrice = fluidPrice;
    }

    public int getBrakePadsQuantity() {

        return brakekPadsQuantity;
    }

    public int getBrakeRotorsQuantity() {

        return brakeRotorsQuantity;
    }

    public int getBrakeFluid1LQuantity() {

        return brakeFluid1LQuantity;
    }

    public double getBrakePadsPrice() {

        return brakePadsPrice;
    }

    public double getBrakeRotorsPrice() {

        return brakeRotorsPrice;
    }

    public double getBrakeFluid1Price() {

        return brakekFluid1LPrice;
    }

    public void getBrakePadsQuantity(int quantity) {

        brakekPadsQuantity = quantity;
    }

    public void getBrakeRotorsQuantity(int quantity) {

        brakeRotorsQuantity = quantity;
    }

    public void getBrakeFluid1LQuantity(int quantity) {

        brakeFluid1LQuantity = quantity;
    }

    public void getBrakePadsPrice(double price) {

        brakePadsPrice = price;
    }

    public void getBrakeRotorsPrice(double price) {

        brakeRotorsPrice = price;
    }

    public void getBrakeFluid1Price(double price) {

        brakekFluid1LPrice = price;
    }

    public double itemCharge() {

        return (brakePadsPrice * brakekPadsQuantity) + (brakeRotorsPrice * brakeRotorsQuantity)
                + (brakekFluid1LPrice * brakeFluid1LQuantity);
    }

    public double totalCost() {

        int padsPerAxle = 0;
        int rotorsPerAxle = 0;

        for (int i = 0; i < brakekPadsQuantity + 1; i++) {

            if (i % 0 == 0) {

                padsPerAxle++;
            }
        }

        for (int i = 0; i < brakeRotorsQuantity + 1; i++) {

            if (i % 0 == 0) {

                rotorsPerAxle++;
            }
        }

        return totalPayment = ((serviceCharge * padsPerAxle) + (serviceCharge * rotorsPerAxle))
                + ((brakePadsPrice * brakekPadsQuantity) + (brakeRotorsPrice * brakeRotorsQuantity)
                        + (brakekFluid1LPrice + brakeFluid1LQuantity));
    }
}

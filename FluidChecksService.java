public class FluidChecksService extends Service {

    private static final double COOLANT_PRICE = 800, TRANSMISSION_FLUID_PRICE = 2000, BRAKE_FLUID_PRICE = 700,
            POWER_STEERING_FLUID_PRICE = 800, SERVICE_CHARGE = 300;

    protected int coolantQuantity, transmissionFluidQuantity, brakeFluidQuantity, powerSteeringFluidQuantity;
    protected double coolantPrice, transmissionFluidPrice, brakeFluidPrice, powerSteeringFluidPrice;

    public FluidChecksService(AccountProfile account, Vehicle vehicle, int coolantQuant, int transmissionFluidQuant,
            int brakeFluidQuant, int steeringFluidQuant) {

        super("Fluid Checks", SERVICE_CHARGE, account, vehicle);

        coolantQuantity = coolantQuant;
        transmissionFluidQuantity = transmissionFluidQuant;
        brakeFluidQuantity = brakeFluidQuant;
        powerSteeringFluidQuantity = steeringFluidQuant;
        coolantPrice = COOLANT_PRICE;
        transmissionFluidPrice = TRANSMISSION_FLUID_PRICE;
        brakeFluidPrice = BRAKE_FLUID_PRICE;
        powerSteeringFluidPrice = POWER_STEERING_FLUID_PRICE;
    }

    public FluidChecksService(double amount, AccountProfile account, Vehicle vehicle, int coolantQuant,
            int transmissionFluidQuant,
            int brakeFluidQuant, int steeringFluidQuant) {

        super("Fluid Checks", amount, account, vehicle);

        coolantQuantity = coolantQuant;
        transmissionFluidQuantity = transmissionFluidQuant;
        brakeFluidQuantity = brakeFluidQuant;
        powerSteeringFluidQuantity = steeringFluidQuant;
        coolantPrice = COOLANT_PRICE;
        transmissionFluidPrice = TRANSMISSION_FLUID_PRICE;
        brakeFluidPrice = BRAKE_FLUID_PRICE;
        powerSteeringFluidPrice = POWER_STEERING_FLUID_PRICE;
    }

    public FluidChecksService(AccountProfile account, Vehicle vehicle, int coolantQuant, int transmissionFluidQuant,
            int brakeFluidQuant, int steeringFluidQuant, double coolantPrice, double transmissionFluidPrice,
            double brakeFluidPrice, double steeringFluidPrice) {

        super("Fluid Checks", SERVICE_CHARGE, account, vehicle);

        coolantQuantity = coolantQuant;
        transmissionFluidQuantity = transmissionFluidQuant;
        brakeFluidQuantity = brakeFluidQuant;
        powerSteeringFluidQuantity = steeringFluidQuant;
        this.coolantPrice = coolantPrice;
        this.transmissionFluidPrice = transmissionFluidPrice;
        this.brakeFluidPrice = brakeFluidPrice;
        this.powerSteeringFluidPrice = steeringFluidPrice;
    }

    public FluidChecksService(double amount, AccountProfile account, Vehicle vehicle, int coolantQuant,
            int transmissionFluidQuant,
            int brakeFluidQuant, int steeringFluidQuant, double coolantPrice, double transmissionFluidPrice,
            double brakeFluidPrice, double steeringFluidPrice) {

        super("Fluid Checks", amount, account, vehicle);

        coolantQuantity = coolantQuant;
        transmissionFluidQuantity = transmissionFluidQuant;
        brakeFluidQuantity = brakeFluidQuant;
        powerSteeringFluidQuantity = steeringFluidQuant;
        this.coolantPrice = coolantPrice;
        this.transmissionFluidPrice = transmissionFluidPrice;
        this.brakeFluidPrice = brakeFluidPrice;
        this.powerSteeringFluidPrice = steeringFluidPrice;
    }

    public int getCoolantQuantity() {

        return coolantQuantity;
    }

    public int getTransmissionFluidQuantity() {

        return transmissionFluidQuantity;
    }

    public int getBreakFluidQuantity() {

        return brakeFluidQuantity;
    }

    public int getPowerSteeringFluidQuantity() {

        return powerSteeringFluidQuantity;
    }

    public double getTransmissionFluidPrice() {

        return transmissionFluidPrice;
    }

    public double getBreakFluidPrice() {

        return brakeFluidPrice;
    }

    public double getCoolantPrice() {

        return coolantPrice;
    }

    public double getPowerSteeringFluidPrice() {

        return powerSteeringFluidPrice;
    }

    public void setCoolantQuantity(int quantity) {

        coolantQuantity = quantity;
    }

    public void setTransmissionFluidQuantity(int quantity) {

        transmissionFluidQuantity = quantity;
    }

    public void setBreakFluidQuantity(int quantity) {

        brakeFluidQuantity = quantity;
    }

    public void setPowerSteeringFluidQuantity(int quantity) {

        powerSteeringFluidQuantity = quantity;
    }

    public double itemCharge() {

        return (coolantPrice * coolantQuantity) + (transmissionFluidPrice * transmissionFluidQuantity)
                + (brakeFluidPrice * brakeFluidQuantity) + (powerSteeringFluidPrice * powerSteeringFluidQuantity);
    }

    public double totalCost() {

        int quantity = 0;

        if (coolantQuantity > 0) {
            quantity += 1;
        }

        if (transmissionFluidQuantity > 0) {
            quantity += 1;
        }

        if (brakeFluidQuantity > 0) {
            quantity += 1;
        }

        if (powerSteeringFluidQuantity > 0) {
            quantity += 1;
        }

        return totalPayment = serviceCharge * quantity;
    }
}
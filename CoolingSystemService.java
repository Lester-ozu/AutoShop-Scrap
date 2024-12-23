public class CoolingSystemService extends Service {

    private static final double RADIATOR_PRICE = 3500, WATER_PUMP_PRICE = 2500, THERMOSTAT_PRICE = 1000,
            HOSE_PRICE = 1000, SERVICE_CHARGE = 1000;
    protected int radiatorQuantity, waterPumpQuantity, thermostatQuantity, hoseQuantity;
    protected double radiatorPrice, waterPumpPrice, thermostatPrice, hosePrice;

    public CoolingSystemService(AccountProfile account, Vehicle vehicle, int radiatorQuant, int waterPumpQuant,
            int thermostatQuant, int hoseQuant) {

        super("Cooling System Service", SERVICE_CHARGE, account, vehicle);
        radiatorQuantity = radiatorQuant;
        waterPumpQuantity = waterPumpQuant;
        thermostatQuantity = thermostatQuant;
        hoseQuantity = hoseQuant;
        radiatorPrice = RADIATOR_PRICE;
        waterPumpPrice = WATER_PUMP_PRICE;
        thermostatPrice = THERMOSTAT_PRICE;
        hosePrice = HOSE_PRICE;
    }

    public CoolingSystemService(double amount, AccountProfile account, Vehicle vehicle, int radiatorQuant,
            int waterPumpQuant,
            int thermostatQuant, int hoseQuant) {

        super("Cooling System Service", amount, account, vehicle);
        radiatorQuantity = radiatorQuant;
        waterPumpQuantity = waterPumpQuant;
        thermostatQuantity = thermostatQuant;
        hoseQuantity = hoseQuant;
        radiatorPrice = RADIATOR_PRICE;
        waterPumpPrice = WATER_PUMP_PRICE;
        thermostatPrice = THERMOSTAT_PRICE;
        hosePrice = HOSE_PRICE;
    }

    public CoolingSystemService(AccountProfile account, Vehicle vehicle, int radiatorQuant, int waterPumpQuant,
            int thermostatQuant, int hoseQuant, double radiatorPrice, double waterPumpPrice, double thermostatPrice,
            double hosePrice) {

        super("Cooling System Service", SERVICE_CHARGE, account, vehicle);
        radiatorQuantity = radiatorQuant;
        waterPumpQuantity = waterPumpQuant;
        thermostatQuantity = thermostatQuant;
        hoseQuantity = hoseQuant;
        this.radiatorPrice = radiatorPrice;
        this.waterPumpPrice = waterPumpPrice;
        this.thermostatPrice = thermostatPrice;
        this.hosePrice = hosePrice;
    }

    public CoolingSystemService(double amount, AccountProfile account, Vehicle vehicle, int radiatorQuant,
            int waterPumpQuant,
            int thermostatQuant, int hoseQuant, double radiatorPrice, double waterPumpPrice, double thermostatPrice,
            double hosePrice) {

        super("Cooling System Service", amount, account, vehicle);
        radiatorQuantity = radiatorQuant;
        waterPumpQuantity = waterPumpQuant;
        thermostatQuantity = thermostatQuant;
        hoseQuantity = hoseQuant;
        this.radiatorPrice = radiatorPrice;
        this.waterPumpPrice = waterPumpPrice;
        this.thermostatPrice = thermostatPrice;
        this.hosePrice = hosePrice;
    }

    public int getRadiatorQuantity() {

        return radiatorQuantity;
    }

    public int getWaterPumpQuantity() {

        return waterPumpQuantity;
    }

    public int getThermostatQuantity() {

        return thermostatQuantity;
    }

    public int getHoseQuantity() {

        return hoseQuantity;
    }

    public double getRadiatorPrice() {

        return radiatorPrice;
    }

    public double getWaterPumpPrice() {

        return waterPumpPrice;
    }

    public double getThermostatPrice() {

        return thermostatPrice;
    }

    public double getHosePrice() {

        return hosePrice;
    }

    public void setRadiatorQuantity(int quantity) {

        radiatorQuantity = quantity;
    }

    public void setWaterPumpQuantity(int quantity) {

        waterPumpQuantity = quantity;
    }

    public void setThermostatQuantity(int quantity) {

        thermostatQuantity = quantity;
    }

    public void setHoseQuantity(int quantity) {

        hoseQuantity = quantity;
    }

    public void setRadiatorPrice(double amount) {

        radiatorPrice = amount;
    }

    public void setWaterPumpPrice(double amount) {

        waterPumpPrice = amount;
    }

    public void setThermostatPrice(double amount) {

        thermostatPrice = amount;
    }

    public void setHosePrice(double amount) {

        hosePrice = amount;
    }

    public double itemCharge() {

        return (radiatorPrice * radiatorQuantity) + (waterPumpPrice * waterPumpQuantity)
                + (thermostatPrice * thermostatQuantity) + (hosePrice * hoseQuantity);
    }

    public double totalCost() {

        return totalPayment = serviceCharge + itemCharge();
    }
}
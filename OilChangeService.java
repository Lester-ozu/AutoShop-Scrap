public class OilChangeService extends Service {

    private static final double ENGINE_OIL_PRICE = 2000, OIL_FILTER_PRICE = 300, SERVICE_CHARGE = 650;

    protected int engineOilQuantity, oilFilterQuantity;
    protected double engineOilPrice, oilFilterPrice;

    public OilChangeService(AccountProfile account, Vehicle vehicle, int oilQuant, int filterQuant) {

        super("Oil Change", SERVICE_CHARGE, account, vehicle);

        engineOilQuantity = oilQuant;
        oilFilterQuantity = filterQuant;
        engineOilPrice = ENGINE_OIL_PRICE;
        oilFilterPrice = OIL_FILTER_PRICE;
    }

    public OilChangeService(double amount, AccountProfile account, Vehicle vehicle, int oilQuant, int filterQuant) {

        super("Oil Change", amount, account, vehicle);

        engineOilQuantity = oilQuant;
        oilFilterQuantity = filterQuant;
        engineOilPrice = ENGINE_OIL_PRICE;
        oilFilterPrice = OIL_FILTER_PRICE;
    }

    public OilChangeService(AccountProfile account, Vehicle vehicle, int oilQuant, int filterQuant, double oilPrice,
            double filterPrice) {

        super("Oil Change", SERVICE_CHARGE, account, vehicle);

        engineOilQuantity = oilQuant;
        oilFilterQuantity = filterQuant;
        engineOilPrice = oilPrice;
        oilFilterPrice = filterPrice;
    }

    public OilChangeService(double amount, AccountProfile account, Vehicle vehicle, int oilQuant, int filterQuant,
            double oilPrice, double filterPrice) {

        super("Oil Change", amount, account, vehicle);

        engineOilQuantity = oilQuant;
        oilFilterQuantity = filterQuant;
        engineOilPrice = oilPrice;
        oilFilterPrice = filterPrice;
    }

    public int getEngineOilQuantity() {

        return engineOilQuantity;
    }

    public int getOilFilerQuantity() {

        return oilFilterQuantity;
    }

    public double getEngineOilPrice() {

        return engineOilPrice;
    }

    public double getOilFilterPrice() {

        return oilFilterPrice;
    }

    public void setEngineOilQuantity(int quantity) {

        engineOilQuantity = quantity;
    }

    public void setOilFilerQuantity(int quantity) {

        oilFilterQuantity = quantity;
    }

    public void setEngineOilPrice(double price) {

        engineOilPrice = price;
    }

    public void getOilFilterPrice(double price) {

        oilFilterPrice = price;
    }

    public double itemCharge() {

        return (engineOilPrice * engineOilQuantity) + (oilFilterPrice * oilFilterQuantity);
    }

    public double totalCost() {

        return totalPayment = serviceCharge + itemCharge();
    }
}

public class ExhaustSystemInspectionAndRepairService extends Service {

    private static final double MUFFLER_PRICE = 3500, CATALYTIC_CONVERTER_PRICE = 7500, EXHAUST_PIPES_PRICE = 2000,
            SERVICE_CHARGE = 1000;
    protected int mufflerQuantity, catalyticConverterQuantity, exhaustPipesQuantity;
    protected double mufflerPrice, catalyticConverterPrice, exhaustPipesPrice;

    public ExhaustSystemInspectionAndRepairService(AccountProfile account, Vehicle vehicle, int mufflerQuant,
            int converterQuant,
            int pipesQuant) {

        super("Exhaust System Inspection and Repair", SERVICE_CHARGE, account, vehicle);
        mufflerQuantity = mufflerQuant;
        catalyticConverterQuantity = converterQuant;
        exhaustPipesQuantity = pipesQuant;
        mufflerPrice = MUFFLER_PRICE;
        catalyticConverterPrice = CATALYTIC_CONVERTER_PRICE;
        exhaustPipesPrice = EXHAUST_PIPES_PRICE;
    }

    public ExhaustSystemInspectionAndRepairService(double amount, AccountProfile account, Vehicle vehicle,
            int mufflerQuant,
            int converterQuant,
            int pipesQuant) {

        super("Exhaust System Inspection and Repair", amount, account, vehicle);
        mufflerQuantity = mufflerQuant;
        catalyticConverterQuantity = converterQuant;
        exhaustPipesQuantity = pipesQuant;
        mufflerPrice = MUFFLER_PRICE;
        catalyticConverterPrice = CATALYTIC_CONVERTER_PRICE;
        exhaustPipesPrice = EXHAUST_PIPES_PRICE;
    }

    public ExhaustSystemInspectionAndRepairService(AccountProfile account, Vehicle vehicle, int mufflerQuant,
            int converterQuant,
            int pipesQuant, double mufflerPrice, double converterPrice, double pipesPrice) {

        super("Exhaust System Inspection and Repair", SERVICE_CHARGE, account, vehicle);
        mufflerQuantity = mufflerQuant;
        catalyticConverterQuantity = converterQuant;
        exhaustPipesQuantity = pipesQuant;
        this.mufflerPrice = mufflerPrice;
        this.catalyticConverterPrice = converterPrice;
        this.exhaustPipesPrice = pipesPrice;
    }

    public ExhaustSystemInspectionAndRepairService(double amount, AccountProfile account, Vehicle vehicle,
            int mufflerQuant,
            int converterQuant,
            int pipesQuant, double mufflerPrice, double converterPrice, double pipesPrice) {

        super("Exhaust System Inspection and Repair", amount, account, vehicle);
        mufflerQuantity = mufflerQuant;
        catalyticConverterQuantity = converterQuant;
        exhaustPipesQuantity = pipesQuant;
        this.mufflerPrice = mufflerPrice;
        this.catalyticConverterPrice = converterPrice;
        this.exhaustPipesPrice = pipesPrice;
    }

    public int getMufflerQuantity() {

        return mufflerQuantity;
    }

    public int getCatalyticConverterQuantity() {

        return catalyticConverterQuantity;
    }

    public int getExhaustPipesQuantity() {

        return exhaustPipesQuantity;
    }

    public double getMufflerPrice() {

        return mufflerPrice;
    }

    public double getCatalyticConverPrice() {

        return catalyticConverterPrice;
    }

    public double getExhausPipesPrice() {

        return exhaustPipesPrice;
    }

    public void setMufflerQuantity(int quantity) {

        mufflerQuantity = quantity;
    }

    public void setCatalyticConverterQuantity(int quantity) {

        catalyticConverterQuantity = quantity;
    }

    public void setExhaustPipesQuantity(int quantity) {

        exhaustPipesQuantity = quantity;
    }

    public void setMufflerPrice(double amount) {

        mufflerPrice = amount;
    }

    public void setCatalyticConverPrice(double amount) {

        catalyticConverterPrice = amount;
    }

    public void setExhausPipesPrice(double amount) {

        exhaustPipesPrice = amount;
    }

    public double itemCharge() {

        return (mufflerPrice * mufflerQuantity) + (catalyticConverterPrice * catalyticConverterQuantity)
                + (exhaustPipesPrice * exhaustPipesQuantity);
    }

    public double totalCost() {

        return totalPayment = serviceCharge + itemCharge();
    }
}
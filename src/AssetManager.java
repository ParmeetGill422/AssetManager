import java.util.*;

class Asset {
    private String description;
    private String dateAcquired;
    private double originalCost;

    public Asset(String description, String dateAcquired, double originalCost) {
        this.description = description;
        this.dateAcquired = dateAcquired;
        this.originalCost = originalCost;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDateAcquired() { return dateAcquired; }
    public void setDateAcquired(String dateAcquired) { this.dateAcquired = dateAcquired; }

    public double getOriginalCost() { return originalCost; }
    public void setOriginalCost(double originalCost) { this.originalCost = originalCost; }

    public double getValue() {
        return originalCost;
    }
}

class House extends Asset {
    private String address;
    private int condition;
    private int squareFoot;
    private int lotSize;

    public House(String description, String dateAcquired, double originalCost,
                 String address, int condition, int squareFoot, int lotSize) {
        super(description, dateAcquired, originalCost);
        this.address = address;
        this.condition = condition;
        this.squareFoot = squareFoot;
        this.lotSize = lotSize;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getCondition() { return condition; }
    public void setCondition(int condition) { this.condition = condition; }

    public int getSquareFoot() { return squareFoot; }
    public void setSquareFoot(int squareFoot) { this.squareFoot = squareFoot; }

    public int getLotSize() { return lotSize; }
    public void setLotSize(int lotSize) { this.lotSize = lotSize; }

    @Override
    public double getValue() {
        double baseRate;
        switch (condition) {
            case 1:
                baseRate = 180.00;
                break;
            case 2:
                baseRate = 130.00;
                break;
            case 3:
                baseRate = 90.00;
                break;
            default:
                baseRate = 80.00;
                break;
        }
        return squareFoot * baseRate + lotSize * 0.25;
    }
}

class Vehicle extends Asset {
    private String makeModel;
    private int year;
    private int odometer;

    public Vehicle(String description, String dateAcquired, double originalCost,
                   String makeModel, int year, int odometer) {
        super(description, dateAcquired, originalCost);
        this.makeModel = makeModel;
        this.year = year;
        this.odometer = odometer;
    }

    public String getMakeModel() { return makeModel; }
    public void setMakeModel(String makeModel) { this.makeModel = makeModel; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getOdometer() { return odometer; }
    public void setOdometer(int odometer) { this.odometer = odometer; }

    @Override
    public double getValue() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int age = currentYear - year;
        double value;

        if (age <= 3) {
            value = getOriginalCost() * Math.pow(0.97, age);
        } else if (age <= 6) {
            value = getOriginalCost() * Math.pow(0.94, age - 3) * Math.pow(0.97, 3);
        } else if (age <= 10) {
            value = getOriginalCost() * Math.pow(0.92, age - 6) * Math.pow(0.94, 3) * Math.pow(0.97, 3);
        } else {
            value = 1000.00;
        }

        String modelLower = makeModel.toLowerCase();
        if (odometer > 100000 && !(modelLower.contains("honda") || modelLower.contains("toyota"))) {
            value *= 0.75;
        }

        return value;
    }
}

public class AssetManager {
    public static void main(String[] args) {
        ArrayList<Asset> assets = new ArrayList<Asset>();

        assets.add(new House("My house", "2015-06-15", 250000, "123 Main St", 1, 2000, 5000));
        assets.add(new House("Vacation home", "2018-09-01", 180000, "456 Lake View", 2, 1500, 4000));
        assets.add(new Vehicle("Tom's truck", "2020-03-10", 35000, "Ford F150", 2019, 85000));
        assets.add(new Vehicle("Family car", "2016-07-20", 28000, "Honda Civic", 2015, 120000));

        for (Asset asset : assets) {
            System.out.println("Description: " + asset.getDescription());
            System.out.println("Date Acquired: " + asset.getDateAcquired());
            System.out.println("Original Cost: $" + asset.getOriginalCost());
            System.out.println("Current Value: $" + asset.getValue());

            if (asset instanceof House) {
                House h = (House) asset;
                System.out.println("Address: " + h.getAddress());
            } else if (asset instanceof Vehicle) {
                Vehicle v = (Vehicle) asset;
                System.out.println("Year & Make/Model: " + v.getYear() + " " + v.getMakeModel());
            }

            System.out.println("---------------------------");
        }
    }
}

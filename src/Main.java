import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main {
    private static final String WINDOW_TITLE = "Westminster Shopping Center";
    private static final String[] COLUMN_NAMES = {"Product ID", "Name", "Category", "Price", "Info"};

    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            initGui();
            System.out.println("Westminster Shopping Manager Menu");
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print product list");
            System.out.println("4. Save product list to file");
            System.out.println("5. Load product list from file");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.next();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case "1":
                    // Add a new product
                    if (shoppingManager.getProductList().size() < 50) {
                        addNewProduct(scanner, shoppingManager);
                        System.out.println("Product added successfully.");
                    } else {
                        System.out.println("Maximum limit of products reached. Cannot add more products.");
                    }
                    break;

                case "2":
                    // Delete a product
                    System.out.print("Enter product ID to delete: ");
                    String deleteProductId = scanner.nextLine();
                    boolean productDeleted = shoppingManager.deleteProductFromSystem(deleteProductId);

                    if (productDeleted) {
                        System.out.println("Product with ID " + deleteProductId + " has been deleted.");
                        System.out.println("Total number of products in the system: " + shoppingManager.getProductList().size());
                    } else {
                        System.out.println("Product with ID " + deleteProductId + " not found.");
                    }
                    break;

                case "3":
                    // Print product list alphabetically
                    System.out.println("List of Products in the System (Ordered Alphabetically):");
                    shoppingManager.printProductListAlphabetically();
                    break;

                case "4":
                    // Save product list to file
                    System.out.print("Enter file name to save: ");
                    String saveFileName = scanner.nextLine();
                    shoppingManager.saveToFile(saveFileName);
                    break;

                case "5":
                    // Load product list from file
                    System.out.print("Enter file name to load: ");
                    String loadFileName = scanner.nextLine();
                    shoppingManager.readFromFile(loadFileName);
                    break;

                case "6":
                    // Exit
                    System.out.println("Exiting Westminster Shopping Manager. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void addNewProduct(Scanner scanner, WestminsterShoppingManager shoppingManager) {
        System.out.println("Select product type: 1. Electronics 2. Clothing");
        int productType = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine();
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter available items: ");
        int availableItems = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (productType == 1) {
            // Electronics
            System.out.print("Enter brand: ");
            String brand = scanner.nextLine();
            System.out.print("Enter warranty period: ");
            int warrantyPeriod = scanner.nextInt();

            Electronics electronics = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
            shoppingManager.addProductToSystem(electronics);
        } else if (productType == 2) {
            // Clothing
            System.out.print("Enter size: ");
            String size = scanner.nextLine();
            System.out.print("Enter color: ");
            String color = scanner.nextLine();

            Clothing clothing = new Clothing(productId, productName, availableItems, price, size, color);
            shoppingManager.addProductToSystem(clothing);
        }
    }

    private static void initGui() {
        // GUI initialization code...
    }
}

abstract class Product implements Serializable {
    private String productId;
    private String productName;
    private int availableItems;
    private double price;

    public Product(String productId, String productName, int availableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getType();
}

class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productId, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    @Override
    public String getType() {
        return "Electronics";
    }
}

class Clothing extends Product {
    private String size;
    private String color;

    public Clothing(String productId, String productName, int availableItems, double price, String size, String color) {
        super(productId, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String getType() {
        return "Clothing";
    }
}

class WestminsterShoppingManager {
    private List<Product> productList;

    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void addProductToSystem(Product product) {
        if (productList.size() < 50) {
            productList.add(product);
            System.out.println("Product added to the system.");
        } else {
            System.out.println("Maximum limit of products reached. Cannot add more products.");
        }
    }

    public boolean deleteProductFromSystem(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                productList.remove(product);
                return true;
            }
        }
        return false;
    }

    public void printProductListAlphabetically() {
        Collections.sort(productList, Comparator.comparing(Product::getProductName));

        for (Product product : productList) {
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Available Items: " + product.getAvailableItems());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Type: " + product.getType());

            if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                System.out.println("Brand: " + electronics.getBrand());
                System.out.println("Warranty Period: " + electronics.getWarrantyPeriod());
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                System.out.println("Size: " + clothing.getSize());
                System.out.println("Color: " + clothing.getColor());
            }

            System.out.println("------------");
        }
    }

    public void saveToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(productList);
            System.out.println("Product list saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            productList = (List<Product>) ois.readObject();
            System.out.println("Product list loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

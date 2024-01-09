import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Main class for console menu implementation
public class Main {
    private static final String WINDOW_TITLE = "Westminster Shopping Center";
    private static final String[] COLUMN_NAMES = {"Product ID",
            "Name",
            "Category",
            "Price",
            "Info"
    };
    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            initGui();
            System.out.println("===== Westminster Shopping Manager Menu =====");
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
                    break;

                case "2":
                    // Delete a product
                    System.out.print("Enter product ID to delete: ");
                    String deleteProductId = scanner.nextLine();
                    shoppingManager.deleteProductFromSystem(deleteProductId);
                    break;

                case "3":
                    // Print product list
                    shoppingManager.printProductList();
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

                default: //this line doesnt work
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void initGui() {

        Object[][] data = {
                {"a1", "tv",
                        "electronics", 5, false},
                {"c2", "Doe",
                        "phone", 3, true},
                {"x3", "earplugs",
                        "electronics", 2, false},
                {"d3", "short",
                        "clothing", 20, true},
                {"x3", "shirt",
                        "clothing", 10, false}
        };


        JFrame f = new JFrame(WINDOW_TITLE);
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        final JPanel headerPanel = new JPanel(new FlowLayout());
        final JPanel tablePanel = new JPanel();
        final JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel("Select product category: ");
        String[] choices = {"All", "Electronics", "Clothing"};
        final JComboBox<String> cb = new JComboBox<>(choices);
        f.setSize(800, 600);
        f.setLocation(600,600);

        headerPanel.add(label1);
        headerPanel.add(cb);

        JTable table = new JTable(data, COLUMN_NAMES);
        JScrollPane scrollPane = new JScrollPane(table);
        table.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);

        table.getColumnModel();

        tablePanel.add(scrollPane);

        // Add all sub-panels to the main panel
        mainPanel.add(headerPanel);
        mainPanel.add(tablePanel);

        // Add main panel to frame
        f.add(mainPanel);

        cb.setVisible(true);
        f.setVisible(true);
    }

    private static void updateDetailsPanel(JPanel detailsPanel) {

    }
}

// Abstract class Product
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

    // Getters and setters

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getType();
}

// Electronics subclass
class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productId, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getters and setters specific to Electronics

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String getType() {
        return "Electronics";
    }
}

// Clothing subclass
class Clothing extends Product {
    private String size;
    private String color;

    public Clothing(String productId, String productName, int availableItems, double price, String size, String color) {
        super(productId, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }

    // Getters and setters specific to Clothing

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getType() {
        return "Clothing";
    }
}

// User class
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

// ShoppingCart class
class ShoppingCart {
    private List<Product> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    // Methods to add, remove, and calculate total cost

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(String productId) {
        products.removeIf(product -> product.getProductId().equals(productId));
    }

    public double calculateTotalCost() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}

// Interface for ShoppingManager
interface ShoppingManager {
    void addProductToSystem(Product product);

    void deleteProductFromSystem(String productId);

    void printProductList();

    void saveToFile(String fileName);

    void readFromFile(String fileName);
}

// WestminsterShoppingManager class
class WestminsterShoppingManager implements ShoppingManager {
    private List<Product> productList;

    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
    }

    @Override
    public void addProductToSystem(Product product) {
        if (productList.size() < 50) {
            productList.add(product);
            System.out.println("Product added to the system.");
        } else {
            System.out.println("Maximum limit of products reached. Cannot add more products.");
        }
    }

    @Override
    public void deleteProductFromSystem(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                productList.remove(product);
                System.out.println("Product deleted from the system.");
                break;
            }
        }
    }

    @Override
    public void printProductList() {
        Collections.sort(productList, (p1, p2) -> p1.getProductId().compareTo(p2.getProductId()));

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

    @Override
    public void saveToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(productList);
            System.out.println("Product list saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            productList = (List<Product>) ois.readObject();
            System.out.println("Product list loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
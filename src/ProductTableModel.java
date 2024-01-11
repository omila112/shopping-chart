// ProductTableModel.java
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {
    private List<Product> productList;
    private final String[] COLUMN_NAMES = {"Product ID", "Name", "Category", "Price", "Info"};

    public ProductTableModel(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getRowCount() {
        return productList.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = productList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return product.getProductId();
            case 1:
                return product.getProductName();
            case 2:
                return product.getType();
            case 3:
                return product.getPrice();
            case 4:
                return "";  // Info column (you can customize this)
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
}

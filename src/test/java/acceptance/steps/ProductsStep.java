package acceptance.steps;

import com.tsconsulting.businessManage.application.domain.model.Product;
import com.tsconsulting.businessManage.application.domain.ports.out.ProductRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsStep implements En {
    public ProductsStep(ProductRepository productRepository) {
        Given("^produits existant:$", (DataTable table) -> {

            List<Map<String, String>> dataMap = table.asMaps(String.class, String.class);
            dataMap.forEach(products -> {
                String idProduct = products.get("idProduct");
                String productName = products.get("productName");
                String description = products.get("description");
                double sellingPrice = Double.parseDouble(products.get("sellingPrice"));
                String categoryProduct = products.get("categoryProduct");
                int stockQuantity = Integer.parseInt(products.get("stockQuantity"));


                Product product = new Product(idProduct, productName, description, sellingPrice, categoryProduct, stockQuantity);
                productRepository.add(product);
                assertTrue(productRepository.getAll().contains(product));


            });
        });
    }
}

package pl.psklad.ecommerce.productcatalog;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductCatalogController {

    ProductCatalog productCatalog;

    public ProductCatalogController(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    @GetMapping("/api/version")
    public String version() {
        return "v0.0.1"; //https://semver.org/
    }

    @GetMapping("/api/products")
    public List<Product> allProducts() {
        return productCatalog.allProducts();
    }
}

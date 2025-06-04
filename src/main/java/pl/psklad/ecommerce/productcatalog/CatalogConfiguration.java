package pl.psklad.ecommerce.productcatalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class CatalogConfiguration {
    @Bean
    ProductCatalog createMyProductCatalog(ProductStorage storage) {
        var catalog =  new ProductCatalog(storage);

        catalog.createProduct("Nice one 1", "nice one");
        catalog.createProduct("Nice one 2", "nice one");
        catalog.createProduct("Nice one 3", "nice one");

        return catalog;
    }

    @Bean
    ProductStorage createMyStorage(JdbcTemplate jdbcTemplate) {
//        return new ArrayListProductStorage();
        return new SqlProductStorage(jdbcTemplate);
    }
}

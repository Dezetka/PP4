package pl.psklad.ecommerce.productcatalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SqlProductStorageTest {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setupDatabase() {
        jdbcTemplate.execute("DROP TABLE `product_catalog__products` IF EXISTS");

        var sql = """
            CREATE TABLE `product_catalog__products` (
                id VARCHAR(100) NOT NULL,
                name VARCHAR(100) NOT NULL,
                description VARCHAR(100) NOT NULL,
                price DECIMAL(12,2),
                cover VARCHAR(100),
                PRIMARY KEY(id)
            )
        """;

        jdbcTemplate.execute(sql);
    }

    @Test
    void helloWorldSql() {
        var sql = """
            select now();    
        """;

        var result = jdbcTemplate.queryForObject(sql, String.class);

        assert result.contains("2025");
    }


    @Test
    void itCreateTable() {

       var result = jdbcTemplate.queryForObject("select count(*) from `product_catalog__products`", Integer.class);

       assert result == 0;
    }

    @Test
    void itAllowsToInsertIntoTable() {

        var insertSql = """
            INSERT INTO `product_catalog__products` (id, name, description)
            VALUES 
                ('78283015-ec97-453e-8f6d-92296ad7271f', 'product 1', 'nice desc'),
                ('32cea78d-9748-4927-98bd-915bcca9b08e', 'product 2', 'even nicer');
        """;

        jdbcTemplate.execute(insertSql);

        var result = jdbcTemplate.queryForObject("select count(*) from `product_catalog__products`", Integer.class);

        assert result == 2;
    }

    @Test
    void itAllowsToInsertIntoTableWithArguments() {

        var insertSql = """
            INSERT INTO `product_catalog__products` (id, name, description)
            VALUES
                (?, ?, ?)
        """;

        jdbcTemplate.update(insertSql, "785d6e75-53c1-485f-909c-f664f78cf61f", "product 1", "nice one");

        var result = jdbcTemplate.queryForObject("select count(*) from `product_catalog__products`", Integer.class);

        assert result == 1;
    }

    @Test
    void itAllowsToInsertIntoTableWithArgumentsAsNamedParameters() {

        var insertSql = """
            INSERT INTO `product_catalog__products` (id, name, description)
            VALUES
                (:id, :name, :desc)
        """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", "c2ab2bfd-53db-4ebf-b39f-f9a109ad9bae");
        params.put("desc", "products");
        params.put("name", "nice product");

        var namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        namedJdbc.update(insertSql, params);

        var result = jdbcTemplate.queryForObject("select count(*) from `product_catalog__products`", Integer.class);

        assert result == 1;
    }

    @Test
    void itSaveAndLoadProduct() {
        //Arrange
        Product product = thereIsProduct();
        ProductStorage storage = thereIsStorage();

        //Act
        storage.save(product);
        var loaded = storage.loadProductById(product.getId());

        //Assert
        assertEquals(product.getId(), loaded.getId());
        assertEquals(product.getDescription(), loaded.getDescription());
    }

    private Product thereIsProduct() {
        return new Product(
                UUID.randomUUID(),
                "test it",
                "desc");
    }

    private ProductStorage thereIsStorage() {
        return new SqlProductStorage(jdbcTemplate);
    }

    @Test
    void itLoadsAllProducts() {
        Product product = thereIsProduct();
        ProductStorage storage = thereIsStorage();

        storage.save(product);

        List<Product> all = storage.allProducts();

        assertEquals(1, all.size());
    }
}

package pl.psklad.ecommerce.sales;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import pl.psklad.ecommerce.productcatalog.ProductCatalog;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class salesHttpTest {
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate http;

    @Autowired
    ProductCatalog catalog;

    @Test
    void offerHappyPath(){
        String productId = thereIsProduct("1234", BigDecimal.valueOf(11));
        var toBeCalledUrl = getBaseURL(String.format("/api/add-product/%s",productId));

        http.postForEntity(toBeCalledUrl, null,null);
        http.postForEntity(toBeCalledUrl, null,null);

        var toBeCalledUrlOffer = getBaseURL("/api/current-offer");

        ResponseEntity<Offer> offerHttp = http.getForEntity(toBeCalledUrlOffer, Offer.class);

        assertEquals(BigDecimal.valueOf(22), offerHttp.getBody().getTotal());
    }
    @Test
    void emptyOffer(){

        var toBeCalledUrlOffer = getBaseURL("/api/current-offer");

        ResponseEntity<Offer> offerHttp = http.getForEntity(toBeCalledUrlOffer, Offer.class);

        assertEquals(BigDecimal.valueOf(22), offerHttp.getBody().getTotal());
    }


    private String thereIsProduct(String name,BigDecimal productPrice) {
        var id = catalog.createProduct(name,"nice one");
        catalog.changePrice(id, productPrice);

        return id;
    }
    private String getBaseURL(String productEndpoint) {
        return String.format("http://localhost:%s%s", port, productEndpoint);
    }
}

package pl.psklad.ecommerce.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.psklad.ecommerce.productcatalog.ArrayListProductStorage;
import pl.psklad.ecommerce.productcatalog.ProductCatalog;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class salesTest {
    ProductCatalog catalog;

    @BeforeEach
    void setup() {
        catalog = new ProductCatalog(new ArrayListProductStorage());
    }


    @Test
    void itShowsEmptyOffer() {
        SalesFacade sales = thereIsSalesModuleUnderTest();
        String customerId = thereIsCustomer();

        Offer offer = sales.getCurenOffer(customerId);

        assertEquals(BigDecimal.ZERO, offer.getTotal());
    }
    @Test
    void iAllowsToCollectProducts() {
        SalesFacade sales = thereIsSalesModuleUnderTest();
        String customerId = thereIsCustomer();
        String productId = thereIsProduct("Product X", BigDecimal.valueOf(10));

        sales.addToCart(customerId,productId);
        Offer offer = sales.getCurenOffer(customerId);

        assertEquals(BigDecimal.valueOf(10), offer.getTotal());
    }
    @Test
    void iAllowsToCollectProductsByCustomersSeparately() {
        SalesFacade sales = thereIsSalesModuleUnderTest();
        String customerId1 = thereIsCustomer("kuba");
        String customerId2 = thereIsCustomer("michal");
        String productId = thereIsProduct("Product X", BigDecimal.valueOf(10));

        sales.addToCart(customerId1,productId);
        sales.addToCart(customerId2,productId);
        sales.addToCart(customerId2,productId);

        Offer offer1 = sales.getCurenOffer(customerId1);
        Offer offer2 = sales.getCurenOffer(customerId1);

        assertEquals(BigDecimal.valueOf(10), offer1.getTotal());
        assertEquals(BigDecimal.valueOf(10), offer2.getTotal());
    }

    @Test
    void offerAcceptance() {
        SalesFacade sales = thereIsSalesModuleUnderTest();
        String customerId = thereIsCustomer("Kuba");
        String productId = thereIsProduct("Product X", BigDecimal.valueOf(10));

        sales.addToCart(customerId,productId);
        Offer offer = sales.getCurenOffer(customerId);

        ReservationDetails details = sales.acceptOffer(
                new AcceptOfferCommand()
                        .setFname("1")
                        .setEmail("e")
                        .setLname("s")
        );

    }
    private String thereIsProduct(String name, BigDecimal price){
        var id = catalog.createProduct(name,"desc");
        catalog.changePrice(id,price);

        return id;

    }
    private String thereIsCustomer(String customerName){
        return String.format("customer__%s",customerName);

    }
    private SalesFacade thereIsSalesModuleUnderTest(){
        return new SalesFacade();
    }
}

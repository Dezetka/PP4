package pl.psklad.ecommerce.sales;

public class SalesFacade {
    public void addToCart(String productId, String id) {}

    public void acceptOffer(AcceptOfferCommand acceptOfferCommand) {}
    public Offer getCurenOffer(String customerId){
        return new Offer();
    }
    public void makeReservationPaid(String reservationId) {}
}

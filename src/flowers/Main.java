package flowers;
import filials.BranchManager;
import facade.CheckoutFacade;
import facade.OrderRequests;

public class Main {
    public static void main(String[] args) {
        BranchManager branches = new BranchManager();
        OrderRequests orderRequests = SecondMain.startInteractive(branches);
        if (orderRequests == null) return;
        CheckoutFacade facade = new CheckoutFacade(branches);
        facade.placeOrder(orderRequests);
    }
}
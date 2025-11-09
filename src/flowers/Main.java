package flowers;
import filials.BranchManager;
import facade.CheckoutFacade;
import facade.OrderRequests;

public class Main {
    public static void main(String[] args) {
        OrderRequests orderRequests = SecondMain.startInteractive();
        BranchManager branchManager = new BranchManager();
        CheckoutFacade facade = new CheckoutFacade(branchManager);
        facade.placeOrder(orderRequests);
    }
}
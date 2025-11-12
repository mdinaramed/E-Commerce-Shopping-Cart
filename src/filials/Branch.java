package filials;
import observer.Sklad;
import observer.PriceCatalog;

public class Branch {
    private final BranchType type;
    private final Sklad sklad;
    private final PriceCatalog catalog;

    public Branch(BranchType type) {
        this.type = type;
        this.sklad = new Sklad();
        this.catalog = new PriceCatalog();
        this.sklad.attach(catalog);
    }
    public BranchType getType() {
        return type;
    }
    public Sklad getSklad() {
        return sklad;
    }
    public PriceCatalog getCatalog() {
        return catalog;
    }
    @Override
    public String toString() {
        return "Branch:" + type.name();

    }
}

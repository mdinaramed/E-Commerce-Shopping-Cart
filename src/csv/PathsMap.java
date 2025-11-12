package csv;
import filials.BranchType;

public final class PathsMap {
    public static String inventory(BranchType b) {
        return switch (b) {
            case MEGA_SILKWAY   -> "data/inventory_MEGA_SILKWAY.csv";
            case ABU_DHABI_PLAZA-> "data/inventory_ABU_DHABI_PLAZA.csv";
            case KHAN_SHATYR    -> "data/inventory_KHAN_SHATYR.csv";
        };
    }
    public static String orders()    {
        return "data/orders.csv"; }
    public static String customers() {
        return "data/customers.csv"; }
}
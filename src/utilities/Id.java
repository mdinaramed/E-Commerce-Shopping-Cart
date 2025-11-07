package utilities;
import java.util.UUID;

public class Id {
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}

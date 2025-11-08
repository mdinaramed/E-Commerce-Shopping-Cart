package builder;
import interfaces.Bouquet;

public class ReadyMade {
    private ReadyMade(){}

        public static Bouquet redRoses25(){
            return new BouquetBuilder()
                    .flower("Rose")
                    .color("Red")
                    .wrap("Classic")
                    .card("I love you")
                    .basePrice(25000)
                    .build();
        }
        public static Bouquet redRoses51(){
            return new BouquetBuilder()
                    .flower("Roses")
                    .color("Red")
                    .wrap("Black")
                    .card("Себепсіз, себеп-сіз")
                    .basePrice(50000)
                    .build();
        }
        public static Bouquet rosesMix101(){
            return new BouquetBuilder()
                    .flower("Roses")
                    .color("Red/White")
                    .wrap("Luxury")
                    .card("Only FOR YOU")
                    .basePrice(110000)
                    .build();
        }
        public static Bouquet springMix(){
            return new BouquetBuilder()
                    .flower("Lily +Orchid +Chrysanthemum")
                    .color("Mixed")
                    .wrap("Pink")
                    .card("Best wishes")
                    .basePrice(26000)
                    .build();
        }
        public static Bouquet royalMix(){
            return new BouquetBuilder()
                    .flower("Hydrangea +Orchid")
                    .color("Blue+White")
                    .wrap("Premium")
                    .card("For the best mother")
                    .basePrice(34500)
                    .build();

    }
}

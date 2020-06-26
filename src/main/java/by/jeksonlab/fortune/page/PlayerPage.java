package by.jeksonlab.fortune.page;

public class PlayerPage extends AbstractPage {

    private static final String PLAYER_NAME_LOCATOR = "[itemprop = alternateName]";
    private static final String PLAYER_RATING_LOCATOR = ".statsVal";
    private String name;
    private double rating;

    public void collectPlayerData() {
        setName();
        if (null == name) {
            rating = 1.0;
        } else {
            setRating();
        }
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    private void setName() {
        name = getTextFromLocator(PLAYER_NAME_LOCATOR);
    }

    private void setRating() {
        rating = Double.parseDouble(getTextFromLocator(PLAYER_RATING_LOCATOR));
        if (rating == 0.0) {
            rating = 1.0;
        }
    }
}

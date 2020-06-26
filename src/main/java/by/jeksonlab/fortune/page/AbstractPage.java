package by.jeksonlab.fortune.page;

import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public abstract class AbstractPage {

    private static final String BASE_URL = "https://www.hltv.org";
    private static final String HREF = "href";
    private Document document;

    public void open(String pageUrl) {
        try {
            document = Jsoup.connect(pageUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Document getDocument() {
        return document;
    }

    protected String getBaseUrl() {
        return BASE_URL;
    }

    protected Elements getElementsByLocator(String locator) {
        return getDocument().select(locator);
    }

    @Nullable
    protected String getHrefAttribute(String locator) {
        Elements elements = getElementsByLocator(locator);
        if (elements.size() > 0) {
            return elements.first().attr(HREF);
        } else {
            return null;
        }
    }

    @Nullable
    protected String getHrefAttribute(String locator, int index) {
        Elements elements = getElementsByLocator(locator);
        if (elements.size() > 0) {
            return elements.eq(index).attr(HREF);
        } else {
            return null;
        }
    }

    protected String getTextFromLocator(String locator) {
        Elements elements = getElementsByLocator(locator);
        if (elements.size() > 0) {
            return elements.first().text();
        } else {
            return "";
        }
    }

    @Nullable
    protected String getTextFromLocator(String locator, int index) {
        Elements elements = getElementsByLocator(locator);
        if (elements.size() > 0) {
            return elements.eq(index).text();
        } else {
            return null;
        }
    }
}

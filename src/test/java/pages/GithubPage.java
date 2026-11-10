package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.partialText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static testdata.TestData.GITHUB_URL;

/**
 * Page Object: GitHub — открытие репозитория, переход на вкладку Issues,
 * проверка наличия Issue с ожидаемым названием.
 *
 * Навигация — прямая (открываем URL репозитория). Глобальный поиск GitHub для
 * анонимного пользователя нестабилен: корень домена отдаёт маркетинговую
 * страницу, а «поиск» там — button, а не input. Поэтому в сценарий он не входит.
 */
public class GithubPage {

    /**
     * GitHub отдаёт /issues как client-side React-приложение: данные Issue приходят
     * в JSON (react-app.embeddedData), а строки списка рендерятся JS асинхронно и на
     * облачном Selenoid — медленно. Ждём появления ссылки дольше дефолтных 4с.
     */
    private static final Duration LIST_RENDER_TIMEOUT = Duration.ofSeconds(30);

    private final SelenideElement issuesTab = $("[data-tab-item='issues']");

    public GithubPage openRepository(String repository) {
        Selenide.open(GITHUB_URL + "/" + repository);
        return this;
    }

    public GithubPage openIssuesTab() {
        issuesTab.shouldBe(visible).click();
        return this;
    }

    /**
     * Проверяем наличие Issue с ожидаемым названием на странице Issues.
     * Ищем ссылку по ТЕКСТУ заголовка (XPath), а не по href: GitHub отдаёт /issues
     * как client-side React, и на облачном Selenoid (datacenter-IP) href ссылки
     * отличается от локального — текст же стабилен. Ленивый SelenideElement пере-
     * запрашивает DOM на каждой попытке ожидания — устойчиво к асинхронному рендеру.
     */
    public GithubPage checkIssueTitleVisible(String issueName) {
        SelenideElement issueLink = $(By.xpath("//a[contains(normalize-space(.),'" + issueName + "')]"));
        issueLink.shouldBe(visible, LIST_RENDER_TIMEOUT);
        issueLink.shouldHave(partialText(issueName), LIST_RENDER_TIMEOUT);
        return this;
    }
}

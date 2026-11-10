package steps;

import io.qameta.allure.Step;
import pages.GithubPage;

/**
 * Вариант 3: шаги с аннотацией @Step.
 * Методы делегируют в Page Object, а Allure (aspectjWeaver) превращает их
 * в именованные шаги отчёта — без ручных step() в самом тесте.
 */
public class GithubSteps {

    private final GithubPage page = new GithubPage();

    @Step("Открываем репозиторий")
    public GithubSteps openRepository(String repository) {
        page.openRepository(repository);
        return this;
    }

    @Step("Переходим на вкладку Issues")
    public GithubSteps openIssuesTab() {
        page.openIssuesTab();
        return this;
    }

    @Step("Проверяем наличие Issue с ожидаемым названием")
    public GithubSteps checkIssueTitleVisible(String issueName) {
        page.checkIssueTitleVisible(issueName);
        return this;
    }
}

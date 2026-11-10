package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import pages.GithubPage;
import steps.GithubSteps;

import static io.qameta.allure.Allure.step;
import static testdata.TestData.*;

/**
 * Дополнительное занятие: Allure Reports.
 * Один и тот же сценарий — «проверка названия Issue в репозитории через
 * Web-интерфейс» — реализован тремя способами формирования шагов отчёта.
 */
@Epic("GitHub")
@Feature("Issues")
public class GithubIssueTests extends TestBase {

    private final GithubPage githubPage = new GithubPage();

    /** Вариант 1: чистый Selenide — шаги формирует AllureSelenide listener из TestBase. */
    @Test
    @Story("Название Issue — вариант 1 (Listener)")
    void checkIssueTitleByListenerTest() {
        githubPage.openRepository(REPOSITORY)
                .openIssuesTab()
                .checkIssueTitleVisible(ISSUE_NAME);
    }

    /** Вариант 2: лямбда-шаги через Allure.step(name, () -> ...). */
    @Test
    @Story("Название Issue — вариант 2 (Lambda steps)")
    void checkIssueTitleByLambdaStepsTest() {
        step("Открываем репозиторий " + REPOSITORY, () -> githubPage.openRepository(REPOSITORY));
        step("Переходим на вкладку Issues", () -> githubPage.openIssuesTab());
        step("Проверяем наличие Issue с именем " + ISSUE_NAME, () -> githubPage.checkIssueTitleVisible(ISSUE_NAME));
    }

    /** Вариант 3: шаги с аннотацией @Step (Allure aspectjWeaver). */
    @Test
    @Story("Название Issue — вариант 3 (@Step)")
    void checkIssueTitleByAnnotatedStepsTest() {
        GithubSteps steps = new GithubSteps();
        steps.openRepository(REPOSITORY)
                .openIssuesTab()
                .checkIssueTitleVisible(ISSUE_NAME);
    }
}

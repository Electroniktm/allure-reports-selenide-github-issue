# Allure Reports — Selenide + GitHub Issue

Дополнительное занятие **Allure Reports**.

Один и тот же сценарий — *«проверка названия Issue в репозитории через Web-интерфейс»* —
реализован тремя способами формирования шагов отчёта Allure:

1. **Listener** — чистый Selenide, шаги автоматически собирает `AllureSelenide` (подключён в `TestBase`).
2. **Lambda steps** — именованные шаги через `Allure.step("…", () -> …)`.
3. **@Step** — шаги с аннотацией `@Step` (класс `GithubSteps`; работает за счёт `aspectjWeaver`).

В результате в Allure получаются три визуально разных отчёта по одному и тому же сценарию —
это и есть суть занятия.

## Структура проекта

| Файл | Роль |
|---|---|
| `tests/GithubIssueTests.java` | 3 теста — по одному на каждый вариант |
| `steps/GithubSteps.java` | обёртка с `@Step` (вариант 3) |
| `pages/GithubPage.java` | Page Object: открыть репозиторий → вкладка Issues → проверка названия Issue |
| `testdata/TestData.java` | константы `GITHUB_URL`, `REPOSITORY`, `ISSUE_NAME` |
| `tests/TestBase.java` | `AllureSelenide` listener + конфигурация облачного Selenoid |

## Как запустить

```bash
./gradlew test            # прогон тестов (результаты в build/allure-results)
./gradlew allureReport    # отчёт: build/reports/allure-report/index.html
```

Браузер запускается в облачном Selenoid (`selenoid.qa.guru`), версия Chrome не фиксируется.

## Целевой объект проверки

Тест проверяет наличие Issue с названием `Test Issue` на странице Issues публичного
репозитория `Electroniktm/allure-reports-selenide-github-issue` (этот же репозиторий).
GitHub отдаёт `/issues` как client-side React-приложение, поэтому поиск ссылки ведётся по
тексту заголовка (ленивый XPath), а не по href — это устойчиво к асинхронному рендеру.

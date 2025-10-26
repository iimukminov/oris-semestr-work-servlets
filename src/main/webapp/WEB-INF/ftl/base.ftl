<#macro page_head>
    <meta charset="UTF-8">
    <title>${title!"Сервис-Центр"}</title>
    <link rel="stylesheet" href="/css/style.css">
</#macro>

<#macro page_body>
    <h2>Содержимое страницы</h2>
</#macro>

<#macro display_page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <@page_head/>
    </head>
    <body>
    <header>
        <h1><a href="/">Сервис-Центр "Техно-Ремонт"</a></h1>
        <nav>
            <#if user??>
                <span>Привет, ${user.name}!</span>
                <a href="/profile">Личный кабинет</a>
                <a href="/logout">Выйти</a>
            <#else>
                <a href="/login">Войти</a>
                <a href="/register">Регистрация</a>
            </#if>
        </nav>
    </header>

    <main>
        <@page_body/>
    </main>

    <footer>
        <p>&copy; 2025 Техно-Ремонт. Все права защищены.</p>
    </footer>
    </body>
    </html>
</#macro>


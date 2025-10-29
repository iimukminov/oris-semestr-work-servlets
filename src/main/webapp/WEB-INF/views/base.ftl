<#macro page_head>
    <meta charset="UTF-8">
    <title>${title!"Сервис-Центр"}</title>
    <link rel="stylesheet" href="/static/css/main.css">
</#macro>

<#macro page_body>
    <h2>Контент страницы не определён</h2>
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
                <a href="/dashboard">Главная</a>


                <#-- Для админа -->
                <#if user.role?? && user.role == "ADMIN">
                    <a href="/admin/users">Пользователи</a>
                    <a href="/admin/orders">Заявки</a>
                    <a href="/admin/parts">Запчасти</a>
                    <a href="/admin/services">Услуги</a>

                <#-- Для сотрудника -->
                <#elseif user.role?? && user.role == "STAFF">
                    <a href="/staff/users">Пользователи</a>
                    <a href="/staff/orders">Заявки</a>
                    <a href="/staff/parts">Запчасти</a>
                    <a href="/staff/services">Услуги</a>

                <#-- Для обычного пользователя -->
                <#else>
                    <a href="/dashboard">Главная</a>
                    <a href="/equipment/list">Оборудование</a>
                    <a href="/order/list">Мои заявки</a>
                </#if>

                <a href="/profile">Профиль</a>
                <a href="/logout">Выйти</a>

            <#else>
                <a href="/login">Войти</a>
                <a href="/sign-up">Регистрация</a>
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

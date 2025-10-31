<#macro page_head>
    <meta charset="UTF-8">
    <title>${title!"Сервис-Центр"}</title>

</#macro>

<#macro page_body>
    <h2>Контент страницы не определён</h2>
</#macro>

<#macro display_page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <script src="https://code.jquery.com/jquery-latest.min.js"></script>
        <link rel="stylesheet" href="/css/styles.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
              rel="stylesheet">
        <@page_head/>
    </head>
    <body>
    <header>
        <h1><a href="/dashboard">Сервис-Центр "Техно-Ремонт"</a></h1>
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
                    <a href="/user/equipments">Оборудование</a>
                    <a href="/user/orders">Мои заявки</a>
                </#if>

                <a href="/profile">Профиль</a>
                <a href="https://t.me/+9KZfb2odfuQ5YmVi" target="_blank" rel="noopener noreferrer" title="Связаться через Telegram">Свяжитесь с нами в Telegram</a>
                <a href="/logout">Выйти</a>

            <#else>
                <a href="/login">Войти</a>
                <a href="/sign-up">Регистрация</a>
            </#if>
        </nav>
    </header>

    <@page_body/>

    <footer>
        <p>&copy; 2025 Техно-Ремонт. Все права нарушены.</p>
    </footer>
    </body>
    </html>
</#macro>

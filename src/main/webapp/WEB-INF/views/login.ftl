<#include "base.ftl">

<#macro page_head>
    <meta charset="UTF-8">
    <title>Вход в систему</title>
    <link rel="stylesheet" href="/css/style.css">
</#macro>

<#macro page_body>
    <h2>Вход в систему</h2>

    <#if error??>
        <p style="color: red;">${error}</p>
    </#if>

    <form action="/login" method="post">
        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div>
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit">Войти</button>
    </form>
    <br><br>
    <a href="/sign-up">Нет аккаунта?</a>
</#macro>

<@display_page/>


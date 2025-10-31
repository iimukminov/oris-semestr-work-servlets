<#include "base.ftl">

<#macro page_head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="/css/style.css">
</#macro>

<#macro page_body>
    <h2>Регистрация нового клиента</h2>

    <#if error??>
        <p style="color: red;">${error}</p>
    </#if>

    <form action="/sign-up" method="post">
        <div>
            <label for="name">Имя:</label>
            <input type="text" id="name" name="name" required minlength="2" maxlength="32">
        </div>
        <div>
            <label for="lastname">Фамилия:</label>
            <input type="text" id="lastname" name="lastname" required minlength="2" maxlength="32">
        </div>
        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div>
            <label for="phone">Телефон (необязательно):</label>
            <input type="tel" id="phone" name="phone_number">
        </div>
        <div>
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <label for="password_confirm">Подтвердите пароль:</label>
            <input type="password" id="password_confirm" name="password_confirm" required>
        </div>
        <button type="submit">Зарегистрироваться</button>
    </form>
</#macro>

<@display_page/>


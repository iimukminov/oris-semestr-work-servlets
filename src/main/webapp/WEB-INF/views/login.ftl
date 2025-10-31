<#include "base.ftl">

<#macro page_head>
    <meta charset="UTF-8">
    <title>Вход в систему</title>
    <link rel="stylesheet" href="/css/style.css">
</#macro>

<#macro page_body>
    <main class="login-main">
        <h2 class="login-title">Вход в систему</h2>

        <#if error??>
            <p class="login-error">${error}</p>
        </#if>

        <form action="/login" method="post" class="login-form">
            <div class="form-group">
                <label class="form-label" for="email">Email:</label>
                <input type="email" id="email" name="email" class="form-input" required>
            </div>
            <div class="form-group">
                <label class="form-label" for="password">Пароль:</label>
                <input type="password" id="password" name="password" class="form-input" required>
            </div>
            <button type="submit" class="btn-submit">Войти</button>
        </form>
        <br><br>
        <a href="/sign-up" class="login-register-link">Нет аккаунта?</a>
    </main>
</#macro>


<@display_page/>


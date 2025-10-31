<#include "base.ftl">

<#macro page_head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="/css/style.css">
</#macro>

<#macro page_body>
    <main class="register-main">
        <h1 class="register-title">Регистрация</h1>

        <#if error??>
            <p class="register-error">${error}</p>
        </#if>

        <form action="/sign-up" method="post" class="register-form">
            <div class="form-group">
                <label class="form-label" for="name">Имя:</label>
                <input type="text" id="name" name="name" class="form-input" required minlength="2" maxlength="32">
            </div>
            <div class="form-group">
                <label class="form-label" for="lastname">Фамилия:</label>
                <input type="text" id="lastname" name="lastname" class="form-input" required minlength="2" maxlength="32">
            </div>
            <div class="form-group">
                <label class="form-label" for="email">Email:</label>
                <input type="email" id="email" name="email" class="form-input" required>
                <div id="email-error-msg" ></div>
            </div>
            <div class="form-group">
                <label class="form-label" for="phone">Телефон (необязательно):</label>
                <input type="tel" id="phone" name="phone_number" class="form-input">
            </div>
            <div class="form-group">
                <label class="form-label" for="password">Пароль:</label>
                <input type="password" id="password" name="password" class="form-input" required>
            </div>
            <div class="form-group">
                <label class="form-label" for="password_confirm">Подтвердите пароль:</label>
                <input type="password" id="password_confirm" name="password_confirm" class="form-input" required>
            </div>
            <button type="submit" class="btn-submit" id="submit-btn">Зарегистрироваться</button>
        </form>
        <br><br>
        <a href="/login" class="register-login-link">Уже есть аккаунт? Войти</a>
    </main>

    <script>
        $(document).ready(function() {
            var emailPattern = /^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\.)*(aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z]{2})$/;

            function checkEmail() {
                var email = $('#email').val().toLowerCase();
                var $msg = $('#email-error-msg');
                var isValid = emailPattern.test(email);

                if (email.length === 0) {
                    $msg.text('');
                    $('#submit-btn').prop('disabled', true);
                    return;
                }

                if (!isValid) {
                    $msg.text('Пожалуйста, введите корректный email.');
                    $msg.css('color', '#e53935');
                    $('#submit-btn').prop('disabled', true);
                } else {
                    $msg.text('');
                    $('#submit-btn').prop('disabled', false);
                }
            }

            $('#email').on('input', checkEmail);

            checkEmail();
        });
    </script>


</#macro>


<@display_page/>


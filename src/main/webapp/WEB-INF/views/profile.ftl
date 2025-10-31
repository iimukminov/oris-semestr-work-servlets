<#include "base.ftl">

<#macro page_head>
    <title>Профиль пользователя</title>
</#macro>

<#macro page_body>
    <main>
        <h2>Профиль пользователя</h2>
        <form action="/profile" method="post" id="profileForm">
            <label>Имя:<br>
                <input type="text" name="name" value="${user.name!}" required>
            </label><br><br>

            <label>Фамилия:<br>
                <input type="text" name="lastname" value="${user.lastname!}" required>
            </label><br><br>

            <label>Email:<br>
                <input type="email" id="email" name="email" class="form-input" value="${user.email!}" required>
                <div id="email-error-msg"></div>
            </label><br>

            <#if userType == "client">
                <label>Телефон:<br>
                    <input type="text" name="phoneNumber" value="${user.phoneNumber!}"
                           placeholder="Введите номер телефона">
                </label><br><br>
            </#if>

            <#if user.role??>
                <label>Роль: ${user.role}</label><br><br>
            </#if>

            <#if user.position??>
                <label>Позиция: ${user.position}</label><br><br>
            </#if>

            <label>Новый пароль:<br>
                <input type="password" name="password" id="newPassword" placeholder="Введите новый пароль">
            </label><br><br>

            <button type="submit" id="submit-btn" >Сохранить изменения</button>
        </form>
    </main>

    <script>
        $(document).ready(function() {
            var emailPattern = /^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\.)*(aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z]{2})$/;

            function checkEmail() {
                var email = $('#email').val().toLowerCase();
                var $msg = $('#email-error-msg');
                var isValid = emailPattern.test(email);

                if (email.length === 0) {
                    $msg.text('Пожалуйста, введите корректный email.');
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

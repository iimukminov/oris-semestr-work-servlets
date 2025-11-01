<#include "base.ftl">

<#macro page_head>
    <title>Профиль пользователя</title>
</#macro>

<#macro page_body>
    <main>
        <h2>Профиль пользователя</h2>
        <form action="/profile" method="post" id="profileForm">
            <label>Имя:<br>
                <input type="text" name="name" value="${user.name!}" required maxlength="64">
            </label><br><br>

            <label>Фамилия:<br>
                <input type="text" name="lastname" value="${user.lastname!}" required maxlength="32">
            </label><br><br>

            <label>Email:<br>
                <input type="email" id="email" name="email" class="form-input" value="${user.email!}" required maxlength="32">
                <div id="email-error-msg"></div>
            </label><br>

            <#if userType == "client">
                <label>Телефон:<br>
                    <input type="text" name="phoneNumber" value="${user.phoneNumber!}"
                           placeholder="Введите номер телефона" maxlength="15">
                    <div id="phone-error-msg"></div>
                </label><br><br>
            </#if>

            <#if user.role??>
                <label>Роль: ${user.role}</label><br><br>
            </#if>

            <#if user.position??>
                <label>Позиция: ${user.position}</label><br><br>
            </#if>

            <label>Новый пароль:<br>
                <input type="password" name="password" id="newPassword" placeholder="Введите новый пароль" maxlength="32">
            </label><br><br>

            <button type="submit" id="submit-btn" >Сохранить изменения</button>
        </form>
    </main>

    <script>
        $(document).ready(function() {
            var emailPattern = /^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\.)*(aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z]{2})$/;
            var phonePattern = /^\+?[0-9\s\-]{0,11}$/;

            function validateForm() {
                var email = $('#email').val().toLowerCase();
                var phone = $('#phone').val();
                var $emailMsg = $('#email-error-msg');
                var $phoneMsg = $('#phone-error-msg');

                var emailValid = emailPattern.test(email);
                var phoneValid = phone.length === 0 || phonePattern.test(phone);

                if (email.length === 0) {
                    $emailMsg.text('');
                } else if (!emailValid) {
                    $emailMsg.text('Пожалуйста, введите корректный email.');
                    $emailMsg.css('color', '#e53935');
                } else {
                    $emailMsg.text('');
                }

                if (!phoneValid) {
                    $phoneMsg.text('Пожалуйста, введите корректный телефон.');
                    $phoneMsg.css('color', '#e53935');
                } else {
                    $phoneMsg.text('');
                }

                $('#submit-btn').prop('disabled', !(emailValid && phoneValid));
            }

            $('#email').on('input', validateForm);
            $('#phone').on('input', validateForm);

            validateForm();
        });
    </script>

</#macro>

<@display_page/>

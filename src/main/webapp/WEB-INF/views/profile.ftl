<#include "base.ftl">

<#macro page_head>
    <title>Профиль пользователя</title>
</#macro>

<#macro page_body>
    <h2>Профиль пользователя</h2>
    <form action="/profile" method="post" id="profileForm">
        <label>Имя:<br>
            <input type="text" name="name" value="${user.name!}" required>
        </label><br><br>

        <label>Фамилия:<br>
            <input type="text" name="lastname" value="${user.lastname!}" required>
        </label><br><br>

        <label>Email:<br>
            <input type="email" name="email" value="${user.email!}" required>
        </label><br><br>

        <#if userType == "client">
            <label>Телефон:<br>
                <input type="text" name="phoneNumber" value="${user.phoneNumber!}" placeholder="Введите номер телефона">
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

        <button type="submit" id="submitBtn">Сохранить изменения</button>
    </form>

</#macro>

<@display_page/>

<#include "../base.ftl">

<#macro page_head>
    <title>Пользователи</title>
</#macro>

<#macro page_body>
    <h2>Управление пользователями</h2>
    <#if error??>
        <div style="color:red;">${error}</div>
    </#if>

    <h3>Пользователи</h3>
    <button id="showAddClientBtn" type="button">Добавить клиента</button>
    <div id="addClientForm" style="display:none; margin:16px 0;">
        <form action="/staff/users" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="userType" value="client">
            <label>Имя:<input type="text" name="name" required></label><br>
            <label>Фамилия:<input type="text" name="lastname" required></label><br>
            <label>Email:<input type="email" name="email" required></label><br>
            <label>Телефон:<input type="text" name="phoneNumber"></label><br>
            <label>Пароль:<input type="password" name="password" required></label><br>
            <button type="submit">Сохранить</button>
            <button type="button" onclick="document.getElementById('addClientForm').style.display='none'">Отмена</button>
        </form>
    </div>

    <table border="1" id="clientsTable">
        <thead>
        <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Email</th>
            <th>Телефон</th>
            <th>Пароль</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list clients as client>
            <tr data-client-id="${client.id}">
                <td><span class="readonly">${client.name}</span>
                    <input type="text" class="editfield" name="name" value="${client.name}" style="display:none;" required></td>
                <td><span class="readonly">${client.lastname}</span>
                    <input type="text" class="editfield" name="lastname" value="${client.lastname}" style="display:none;" required></td>
                <td><span class="readonly">${client.email}</span>
                    <input type="email" class="editfield" name="email" value="${client.email}" style="display:none;" required></td>
                <td><span class="readonly">${client.phoneNumber!}</span>
                    <input type="text" class="editfield" name="phoneNumber" value="${client.phoneNumber!}" style="display:none;">
                </td>
                <td>
                    <input type="password" class="editfield" name="password" value="" placeholder="Новый пароль" style="display:none;">
                </td>
                <td>
                    <button type="button" class="editBtn">Редактировать</button>
                    <form action="/staff/users" method="post" class="editForm" style="display:none; margin:0;">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="userType" value="client">
                        <input type="hidden" name="id" value="${client.id}">
                        <input type="hidden" class="editName" name="name">
                        <input type="hidden" class="editLastname" name="lastname">
                        <input type="hidden" class="editEmail" name="email">
                        <input type="hidden" class="editPhoneNumber" name="phoneNumber">
                        <input type="hidden" class="editPassword" name="password">
                        <button type="submit">Подтвердить</button>
                        <button type="button" class="cancelBtn">Отмена</button>
                    </form>
                    <form action="/staff/users" method="post" class="deleteForm" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="userType" value="client">
                        <input type="hidden" name="id" value="${client.id}">
                        <button type="button" class="deleteBtn" style="color:red;">Удалить</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

    <script>
        $(document).ready(function () {
            $('#showAddClientBtn').on('click', function () {
                $('#addClientForm').show();
            });

            $('.editBtn').on('click', function () {
                var $tr = $(this).closest('tr');
                $tr.find('.readonly').hide();
                $tr.find('.editfield').show();
                $(this).hide();
                $tr.find('.editForm').show();
            });

            $('.cancelBtn').on('click', function () {
                var $tr = $(this).closest('tr');
                $tr.find('.editfield').hide();
                $tr.find('.readonly').show();
                $tr.find('.editBtn').show();
                $tr.find('.editForm').hide();
            });

            $('.editForm').on('submit', function () {
                var $form = $(this);
                var $tr = $form.closest('tr');
                $form.find('.editName').val($tr.find('input[name="name"]').val());
                $form.find('.editLastname').val($tr.find('input[name="lastname"]').val());
                $form.find('.editEmail').val($tr.find('input[name="email"]').val());
                $form.find('.editPhoneNumber').val($tr.find('input[name="phoneNumber"]').val());
                $form.find('.editPassword').val($tr.find('input[name="password"]').val());
            });

            $('.deleteBtn').on('click', function () {
                var $tr = $(this).closest('tr');
                var name = $tr.find('span.readonly').first().text();
                if (confirm('Удалить пользователя "' + name + '"? Это действие нельзя отменить!')) {
                    $(this).closest('form.deleteForm').submit();
                }
            });
        });
    </script>
</#macro>

<@display_page/>

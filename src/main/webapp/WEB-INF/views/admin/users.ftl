<#include "../base.ftl">

<#macro page_head>
    <title>Пользователи и сотрудники (админ)</title>
</#macro>

<#macro page_body>
    <h2>Управление пользователями и сотрудниками</h2>
    <#if error??>
        <div style="color:red;">${error}</div>
    </#if>

    <h3>Сотрудники</h3>
    <button id="showAddEmployeeBtn" type="button">Добавить сотрудника</button>
    <div id="addEmployeeForm" style="display:none; margin:16px 0;">
        <form action="/admin/users" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="userType" value="employee">
            <label>Имя:<input type="text" name="name" required></label><br>
            <label>Фамилия:<input type="text" name="lastname" required></label><br>
            <label>Email:<input type="email" name="email" required></label><br>
            <label>Роль:
                <select name="role">
                    <option value="ADMIN">Администратор</option>
                    <option value="STAFF">Сотрудник</option>
                </select>
            </label><br>
            <label>Позиция:<input type="text" name="position"></label><br>
            <label>Пароль:<input type="password" name="password" required></label><br>
            <button type="submit">Сохранить</button>
            <button type="button" onclick="document.getElementById('addEmployeeForm').style.display='none'">Отмена</button>
        </form>
    </div>

    <table border="1" id="employeesTable">
        <thead>
        <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Email</th>
            <th>Роль</th>
            <th>Позиция</th>
            <th>Пароль</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list employees as user>
            <tr data-user-id="${user.id}">
                <td><span class="readonly">${user.name}</span>
                    <input type="text" class="editfield" name="name" value="${user.name}" style="display:none;" required></td>
                <td><span class="readonly">${user.lastname}</span>
                    <input type="text" class="editfield" name="lastname" value="${user.lastname}" style="display:none;" required></td>
                <td><span class="readonly">${user.email}</span>
                    <input type="email" class="editfield" name="email" value="${user.email}" style="display:none;" required></td>
                <td><span class="readonly">${user.role}</span>
                    <select class="editfield" name="role" style="display:none;">
                        <option value="ADMIN"<#if user.role=="ADMIN"> selected</#if>>ADMIN</option>
                        <option value="STAFF"<#if user.role=="STAFF"> selected</#if>>STAFF</option>
                    </select>
                </td>
                <td><span class="readonly">${user.position!}</span>
                    <input type="text" class="editfield" name="position" value="${user.position!}" style="display:none;">
                </td>
                <td>
                    <input type="password" class="editfield" name="password" value="" placeholder="Новый пароль" style="display:none;">
                </td>
                <td>
                    <button type="button" class="editBtn">Редактировать</button>
                    <form action="/admin/users" method="post" class="editForm" style="display:none; margin:0;">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="userType" value="employee">
                        <input type="hidden" name="id" value="${user.id}">
                        <input type="hidden" class="editName" name="name">
                        <input type="hidden" class="editLastname" name="lastname">
                        <input type="hidden" class="editEmail" name="email">
                        <input type="hidden" class="editRole" name="role">
                        <input type="hidden" class="editPosition" name="position">
                        <input type="hidden" class="editPassword" name="password">
                        <button type="submit">Подтвердить</button>
                        <button type="button" class="cancelBtn">Отмена</button>
                    </form>
                    <form action="/admin/users" method="post" class="deleteForm" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="userType" value="employee">
                        <input type="hidden" name="id" value="${user.id}">
                        <button type="button" class="deleteBtn" style="color:red;">Удалить</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

    <h3>Пользователи</h3>
    <button id="showAddClientBtn" type="button">Добавить клиента</button>
    <div id="addClientForm" style="display:none; margin:16px 0;">
        <form action="/admin/users" method="post">
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
                    <form action="/admin/users" method="post" class="editForm" style="display:none; margin:0;">
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
                    <form action="/admin/users" method="post" class="deleteForm" style="display:inline;">
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
        document.getElementById('showAddEmployeeBtn').onclick = function() {
            document.getElementById('addEmployeeForm').style.display = 'block';
        };
        document.getElementById('showAddClientBtn').onclick = function() {
            document.getElementById('addClientForm').style.display = 'block';
        };
        document.querySelectorAll('.editBtn').forEach(function(btn) {
            btn.onclick = function() {
                var tr = btn.closest('tr');
                tr.querySelectorAll('.readonly').forEach(function(el){ el.style.display='none'; });
                tr.querySelectorAll('.editfield').forEach(function(el){ el.style.display='inline'; });
                btn.style.display = 'none';
                tr.querySelector('.editForm').style.display = 'inline';
            };
        });
        document.querySelectorAll('.cancelBtn').forEach(function(btn) {
            btn.onclick = function() {
                var tr = btn.closest('tr');
                tr.querySelectorAll('.editfield').forEach(function(el){ el.style.display='none'; });
                tr.querySelectorAll('.readonly').forEach(function(el){ el.style.display='inline'; });
                tr.querySelector('.editBtn').style.display = 'inline';
                tr.querySelector('.editForm').style.display = 'none';
            };
        });
        document.querySelectorAll('.editForm').forEach(function(form){
            form.onsubmit = function() {
                var tr = form.closest('tr');
                form.querySelector('.editName').value = tr.querySelector('input[name="name"]').value;
                form.querySelector('.editLastname').value = tr.querySelector('input[name="lastname"]').value;
                form.querySelector('.editEmail').value = tr.querySelector('input[name="email"]').value;
                var phone = tr.querySelector('input[name="phoneNumber"]');
                if (phone) form.querySelector('.editPhoneNumber').value = phone.value;
                var role = tr.querySelector('select[name="role"]');
                if (role) form.querySelector('.editRole').value = role.value;
                var position = tr.querySelector('input[name="position"]');
                if (position) form.querySelector('.editPosition').value = position.value;
                form.querySelector('.editPassword').value = tr.querySelector('input[name="password"]').value;
            };
        });
        document.querySelectorAll('.deleteBtn').forEach(function(btn) {
            btn.onclick = function() {
                var tr = btn.closest('tr');
                var name = tr.querySelector('span.readonly').innerText;
                if (confirm('Удалить пользователя "' + name + '"? Это действие нельзя отменить!')) {
                    btn.closest('form.deleteForm').submit();
                }
            };
        });
    </script>
</#macro>

<@display_page/>

<#include "../base.ftl">

<#macro page_head>
    <title>Услуги (админ)</title>
</#macro>

<#macro page_body>
    <h2>Услуги</h2>
    <#if error??>
        <div style="color:red;">${error}</div>
    </#if>
    <button id="showAddServiceBtn" type="button">Добавить услугу</button>
    <div id="addServiceForm" style="display:none; margin:16px 0;">
        <form action="/admin/services" method="post">
            <input type="hidden" name="action" value="add">
            <label>Название:<input type="text" name="name" required></label><br>
            <label>Описание:<input type="text" name="description"></label><br>
            <label>Цена:<input type="number" name="price" min="0" step="0.01" required></label><br>
            <button type="submit">Сохранить</button>
            <button type="button" onclick="document.getElementById('addServiceForm').style.display='none'">Отмена</button>
        </form>
    </div>
    <table border="1" id="servicesTable">
        <thead>
        <tr>
            <th>Название</th>
            <th>Описание</th>
            <th>Цена</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list services as service>
            <tr data-service-id="${service.id}">
                <td><span class="readonly">${service.name}</span>
                    <input type="text" class="editfield" name="name" value="${service.name}" style="display:none;" required></td>
                <td><span class="readonly">${service.description!}</span>
                    <input type="text" class="editfield" name="description" value="${service.description!}" style="display:none;"></td>
                <td><span class="readonly">${service.price}</span>
                    <input type="number" class="editfield" name="price" value="${service.price}" min="0" step="0.01" style="display:none;" required></td>
                <td>
                    <button type="button" class="editBtn">Редактировать</button>
                    <form action="/admin/services" method="post" class="editForm" style="display:none; margin:0;">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="id" value="${service.id}">
                        <input type="hidden" class="editName" name="name">
                        <input type="hidden" class="editDescription" name="description">
                        <input type="hidden" class="editPrice" name="price">
                        <button type="submit">Подтвердить</button>
                        <button type="button" class="cancelBtn">Отмена</button>
                    </form>
                    <form action="/admin/services" method="post" class="deleteForm" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${service.id}">
                        <button type="button" class="deleteBtn" style="color:red;">Удалить</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <script>
        document.getElementById('showAddServiceBtn').onclick = function() {
            document.getElementById('addServiceForm').style.display = 'block';
        };
        document.querySelectorAll('.editBtn').forEach(function(btn){
            btn.onclick = function() {
                var tr = btn.closest('tr');
                tr.querySelectorAll('.readonly').forEach(function(el){ el.style.display='none'; });
                tr.querySelectorAll('.editfield').forEach(function(el){ el.style.display='inline'; });
                btn.style.display = 'none';
                tr.querySelector('.editForm').style.display = 'inline';
            };
        });
        document.querySelectorAll('.cancelBtn').forEach(function(btn){
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
                form.querySelector('.editDescription').value = tr.querySelector('input[name="description"]').value;
                form.querySelector('.editPrice').value = tr.querySelector('input[name="price"]').value;
            };
        });
        document.querySelectorAll('.deleteBtn').forEach(function(btn){
            btn.onclick = function() {
                var tr = btn.closest('tr');
                var name = tr.querySelector('span.readonly').innerText;
                if (confirm('Удалить услугу "' + name + '"? Это действие нельзя отменить!')) {
                    btn.closest('form.deleteForm').submit();
                }
            };
        });
    </script>
</#macro>
<@display_page/>

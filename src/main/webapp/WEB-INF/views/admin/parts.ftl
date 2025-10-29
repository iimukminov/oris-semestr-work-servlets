<#include "../base.ftl">

<#macro page_head>
    <title>Запчасти (админ)</title>
</#macro>

<#macro page_body>
    <h2>Запчасти</h2>
    <#if error??>
        <div style="color:red;">${error}</div>
    </#if>
    <button id="showAddPartBtn" type="button">Добавить запчасть</button>
    <div id="addPartForm" style="display:none; margin:16px 0;">
        <form action="/admin/parts" method="post">
            <input type="hidden" name="action" value="add">
            <label>Название:<input type="text" name="name" required></label><br>
            <label>Количество:<input type="number" name="quantityInStock" min="0" required></label><br>
            <label>Цена:<input type="number" name="price" min="0" step="0.01" required></label><br>
            <button type="submit">Сохранить</button>
            <button type="button" onclick="document.getElementById('addPartForm').style.display='none'">Отмена</button>
        </form>
    </div>
    <table border="1" id="partsTable">
        <thead>
        <tr>
            <th>Название</th>
            <th>Количество</th>
            <th>Цена</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <#list parts as part>
            <tr data-part-id="${part.id}">
                <td><span class="readonly">${part.name}</span>
                    <input type="text" class="editfield" name="name" value="${part.name}" style="display:none;" required></td>
                <td><span class="readonly">${part.quantity!}</span>
                    <input type="number" class="editfield" name="quantityInStock" value="${part.quantityInStock!}" style="display:none;" required></td>
                <td><span class="readonly">${part.price}</span>
                    <input type="number" class="editfield" name="price" value="${part.price}" min="0" step="0.01" style="display:none;" required></td>
                <td>
                    <button type="button" class="editBtn">Редактировать</button>
                    <form action="/admin/parts" method="post" class="editForm" style="display:none; margin:0;">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="id" value="${part.id}">
                        <input type="hidden" class="editName" name="name">
                        <input type="hidden" class="editQuantity" name="quantityInStock">
                        <input type="hidden" class="editPrice" name="price">
                        <button type="submit">Подтвердить</button>
                        <button type="button" class="cancelBtn">Отмена</button>
                    </form>
                    <form action="/admin/parts" method="post" class="deleteForm" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${part.id}">
                        <button type="button" class="deleteBtn" style="color:red;">Удалить</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <script>
        document.getElementById('showAddPartBtn').onclick = function() {
            document.getElementById('addPartForm').style.display = 'block';
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
                form.querySelector('.editQuantity').value = tr.querySelector('input[name="quantityInStock"]').value;
                form.querySelector('.editPrice').value = tr.querySelector('input[name="price"]').value;
            };
        });
        document.querySelectorAll('.deleteBtn').forEach(function(btn){
            btn.onclick = function() {
                var tr = btn.closest('tr');
                var name = tr.querySelector('span.readonly').innerText;
                if (confirm('Удалить запчасть "' + name + '"? Это действие нельзя отменить!')) {
                    btn.closest('form.deleteForm').submit();
                }
            };
        });
    </script>
</#macro>
<@display_page/>

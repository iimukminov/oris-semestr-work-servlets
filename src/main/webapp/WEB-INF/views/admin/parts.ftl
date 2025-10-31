<#include "../base.ftl">

<#macro page_head>
    <title>Запчасти (админ)</title>
</#macro>

<#macro page_body>
    <main>
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
                <button type="button" id="cancelAddBtn">Отмена</button>
            </form>
        </div>
        <table id="partsTable">
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
                        <input type="text" class="editfield" name="name" value="${part.name}" style="display:none;"
                               required></td>
                    <td><span class="readonly">${part.quantity!}</span>
                        <input type="number" class="editfield" name="quantityInStock" value="${part.quantity!0}"
                               style="display:none;" required></td>
                    <td><span class="readonly">${part.price} ₽</span>
                        <input type="number" class="editfield" name="price" value="${part.price}" min="0" step="0.01"
                               style="display:none;" required></td>
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
    </main>
    <script>
        $('#showAddPartBtn').click(function () {
            $('#addPartForm').show();
            $(this).hide();
        });
        $('#cancelAddBtn').click(function () {
            $('#addPartForm').hide();
            $('#showAddPartBtn').show();
        });
        $('.editBtn').click(function () {
            var tr = $(this).closest('tr');
            tr.find('.readonly').hide();
            tr.find('.editfield').show();
            $(this).hide();
            tr.find('.editForm').show();
        });
        $('.cancelBtn').click(function () {
            var tr = $(this).closest('tr');
            tr.find('.editfield').hide();
            tr.find('.readonly').show();
            tr.find('.editBtn').show();
            tr.find('.editForm').hide();
        });
        $('.editForm').submit(function () {
            var form = $(this);
            var tr = form.closest('tr');
            form.find('.editName').val(tr.find('input[name="name"]').val());
            form.find('.editQuantity').val(tr.find('input[name="quantityInStock"]').val());
            form.find('.editPrice').val(tr.find('input[name="price"]').val());
        });
        $('.deleteBtn').click(function () {
            var btn = $(this);
            var tr = btn.closest('tr');
            var name = tr.find('span.readonly').text();
            if (confirm('Удалить запчасть "' + name + '"? Это действие нельзя отменить!')) {
                btn.closest('form.deleteForm').submit();
            }
        });
    </script>
</#macro>
<@display_page/>

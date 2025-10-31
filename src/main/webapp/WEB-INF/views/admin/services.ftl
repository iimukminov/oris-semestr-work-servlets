<#include "../base.ftl">

<#macro page_head>
    <title>Услуги (админ)</title>
</#macro>

<#macro page_body>
    <main>
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
                <button type="button" id="cancelAddBtn">Отмена</button>
            </form>
        </div>
        <table id="servicesTable">
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
                        <input type="text" class="editfield" name="name" value="${service.name}" style="display:none;"
                               required></td>
                    <td><span class="readonly">${service.description!}</span>
                        <input type="text" class="editfield" name="description" value="${service.description!}"
                               style="display:none;"></td>
                    <td><span class="readonly">${service.price} ₽</span>
                        <input type="number" class="editfield" name="price" value="${service.price}" min="0" step="0.01"
                               style="display:none;" required></td>
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
    </main>
    <script>
        $('#cancelAddBtn').click(function () {
            $('#addServiceForm').hide();
            $('#showAddServiceBtn').show();
        });

        $('#showAddServiceBtn').click(function () {
            $('#addServiceForm').show();
            $(this).hide();
        });
        $('.editBtn').click(function () {
            var tr = $(this).closest('tr');
            tr.find('.readonly').hide();
            tr.find('.editfield').css('display', 'inline');
            $(this).hide();
            tr.find('.editForm').css('display', 'inline');
        });
        $('.cancelBtn').click(function () {
            var tr = $(this).closest('tr');
            tr.find('.editfield').hide();
            tr.find('.readonly').css('display', 'inline');
            tr.find('.editBtn').show();
            tr.find('.editForm').hide();
        });
        $('.editForm').submit(function () {
            var form = $(this);
            var tr = form.closest('tr');
            form.find('.editName').val(tr.find('input[name="name"]').val());
            form.find('.editDescription').val(tr.find('input[name="description"]').val());
            form.find('.editPrice').val(tr.find('input[name="price"]').val());
        });
        $('.deleteBtn').click(function () {
            var btn = $(this);
            var tr = btn.closest('tr');
            var name = tr.find('span.readonly').text();
            if (confirm('Удалить услугу "' + name + '"? Это действие нельзя отменить!')) {
                btn.closest('form.deleteForm').submit();
            }
        });
        $('#cancelAddBtn').click(function () {
            $('#addServiceForm').hide();
            $('#showAddServiceBtn').show();
        });
    </script>

</#macro>
<@display_page/>

<#include "../base.ftl">

<#macro page_head>
    <title>Мои устройства</title>
</#macro>

<#macro page_body>
    <h2>Мои зарегистрированные оборудования</h2>

    <#if error??>
        <div style="color:red;">${error}</div>
    </#if><br>

    <button id="showAddFormBtn" type="button">+ Добавить оборудование</button>
    <form id="addForm" action="/user/equipments" method="post" style="display:none; margin-top:15px;">
        <input type="hidden" name="action" value="add">
        <div>
            <label>Тип:</label>
            <input type="text" name="type" required maxlength="64">
        </div>
        <div>
            <label>Бренд:</label>
            <input type="text" name="brand" required maxlength="64">
        </div>
        <div>
            <label>Модель:</label>
            <input type="text" name="model" required maxlength="64">
        </div>
        <div>
            <label>Серийный номер:</label>
            <input type="text" name="serialNumber" required maxlength="64">
        </div>
        <div>
            <label>Описание:</label>
            <input type="text" name="description" required>
        </div>
        <button type="submit">Добавить</button>
        <button type="button" id="cancelAddBtn">Отмена</button>
    </form>

    <div style="overflow-x:auto;">
        <table id="equipmentTable" class="user-equip">
            <thead>
            <tr>
                <th>Тип</th>
                <th>Бренд</th>
                <th>Модель</th>
                <th>Серийный номер</th>
                <th>Описание</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            <#list equipmentList as eq>
                <tr data-equip-id="${eq.id}">
                    <td>
                        <span class="readonly">${eq.type!}</span>
                        <input class="editfield" type="text" name="type" value="${eq.type!}" required maxlength="64" style="display:none;">
                    </td>
                    <td>
                        <span class="readonly">${eq.brand!}</span>
                        <input class="editfield" type="text" name="brand" value="${eq.brand!}" required maxlength="64" style="display:none;">
                    </td>
                    <td>
                        <span class="readonly">${eq.model!}</span>
                        <input class="editfield" type="text" name="model" value="${eq.model!}" required maxlength="64" style="display:none;">
                    </td>
                    <td>
                        <span class="readonly">${eq.serialNumber!}</span>
                        <input class="editfield" type="text" name="serialNumber" value="${eq.serialNumber!}" required maxlength="64" style="display:none;">
                    </td>
                    <td>
                        <span class="readonly">${eq.description!}</span>
                        <input class="editfield" type="text" name="description" value="${eq.description!}" required style="display:none;">
                    </td>
                    <td>
                        <button type="button" class="editBtn">Изменить</button>
                        <form action="/user/equipments" method="post" class="editForm" style="display:none; margin:0;">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="id" value="${eq.id}">
                            <input type="hidden" name="type">
                            <input type="hidden" name="brand">
                            <input type="hidden" name="model">
                            <input type="hidden" name="serialNumber">
                            <input type="hidden" name="description">
                            <button type="submit">Сохранить</button>
                            <button type="button" class="cancelBtn">Отмена</button>
                        </form>
                        <form action="/user/equipments" method="post" class="deleteForm" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${eq.id}">
                            <button type="button" class="deleteBtn" style="color:red;">Удалить</button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(document).ready(function () {
            $('#showAddFormBtn').on('click', function() {
                $('#addForm').show();
                $(this).hide();
            });
            $('#cancelAddBtn').on('click', function() {
                $('#addForm').hide();
                $('#showAddFormBtn').show();
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
                var $tr = $(this).closest('tr');
                $(this).find('input[name="type"]').val($tr.find('input[name="type"]').val());
                $(this).find('input[name="brand"]').val($tr.find('input[name="brand"]').val());
                $(this).find('input[name="model"]').val($tr.find('input[name="model"]').val());
                $(this).find('input[name="serialNumber"]').val($tr.find('input[name="serialNumber"]').val());
                $(this).find('input[name="description"]').val($tr.find('input[name="description"]').val());
            });

            $('.deleteBtn').on('click', function () {
                var $tr = $(this).closest('tr');
                var name = $tr.find('span.readonly').first().text();
                if (confirm('Удалить оборудование "' + name + '"?')) {
                    $(this).closest('form.deleteForm').submit();
                }
            });
        });
    </script>
</#macro>

<@display_page/>

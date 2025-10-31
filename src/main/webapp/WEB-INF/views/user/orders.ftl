<#include "../base.ftl">

<#macro page_head>
    <title>Мои заявки</title>
</#macro>

<#macro page_body>
    <h2>Управление заявками</h2>

<#if error??>
    <div style="color:red; margin-bottom: 1em;">${error}</div>
</#if><br>

    <button id="showAddFormBtn" type="button">+ Создать заявку</button>
    <form id="addForm" action="/user/orders" method="post" style="display:none; margin-top:15px;">
        <input type="hidden" name="action" value="add">
        <div>
            <label>Оборудование:</label>
            <select name="equipmentId" required>
                <option value="">Выберите оборудование</option>
                <#list userEquipments as equipment>
                    <option value="${equipment.id}">${equipment.type} ${equipment.brand} ${equipment.model}</option>
                </#list>
            </select>
        </div>
        <div>
            <label>Описание:</label>
            <input type="text" name="description" maxlength="255" required>
        </div>
        <button type="submit">Создать</button>
        <button type="button" id="cancelAddBtn">Отмена</button>
    </form>

    <div style="overflow-x:auto; margin-top: 1.5em;">
        <table id="ordersTable" class="user-orders" style="width: 100%;">
            <thead>
            <tr>
                <th>Оборудование</th>
                <th>Статус</th>
                <th>Описание</th>
                <th>Создана</th>
                <th>Завершено</th>
                <th>Услуги</th>
                <th>Запчасти и количество</th>
                <th>Сумма</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            <#list orders as order>
                <tr data-order-id="${order.id}">
                    <td>
                        <#if order.equipment??>
                            ${order.equipment.type} ${order.equipment.brand} ${order.equipment.model}
                        <#else>
                            (оборудование не найдено)
                        </#if>
                    </td>
                    <td><#if order.status == "NEW">
                            Новая. Свяжитесь с нами в телеграмм для согласования
                        <#elseif order.status == "IN_PROGRESS">
                            В процессе
                        <#else >
                            Завершена
                        </#if></td>
                    <td>
                        <#if order.status == "NEW">
                            <span class="readonly">${order.description!}</span>
                            <input type="text" class="editfield" name="description" value="${order.description!}"
                                   maxlength="255" style="display: none">
                        <#else>
                            <span>${order.description!}</span>
                        </#if>
                    </td>
                    <td>${order.createdAt!}</td>
                    <td>
                        <#if order.completedAt??>
                            ${order.completedAt}
                        <#else>
                            Не выполнено
                        </#if>
                    </td>
                    <td>
                        <#list order.services as service>
                            <div>${service.name}</div>
                        </#list>
                    </td>
                    <td>
                        <#list order.parts as partQuantity>
                            <div>${partQuantity.part.name}: ${partQuantity.quantity}</div>
                        </#list>
                    </td>
                    <td><#if order.totalCost??>${order.totalCost} ₽</#if></td>
                    <td>
                        <#if order.status == "NEW">
                            <button type="button" class="editBtn">Редактировать описание</button>
                            <form action="/user/orders" method="post" class="editForm" style="display:none; margin:0;">
                                <input type="hidden" name="action" value="editDescription">
                                <input type="hidden" name="orderId" value="${order.id}">
                                <input type="hidden" class="editDescriptionInput" name="description">
                                <button type="submit">Сохранить</button>
                                <button type="button" class="cancelBtn">Отмена</button>
                            </form>

                            <form action="/user/orders" method="post" class="deleteForm" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="orderId" value="${order.id}">
                                <button type="button" class="deleteBtn" style="color:red;">Удалить</button>
                            </form>
                        <#else>
                            —
                        </#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(document).ready(function () {
            $('#showAddFormBtn').click(function () {
                $('#addForm').show();
                $(this).hide();
            });

            $('#cancelAddBtn').click(function () {
                $('#addForm').hide();
                $('#showAddFormBtn').show();
            });

            $('.editBtn').click(function () {
                var $tr = $(this).closest('tr');
                $tr.find('.readonly').hide();
                $tr.find('.editfield').show();
                $(this).hide();
                $tr.find('.editForm').show();
            });

            $('.cancelBtn').click(function () {
                var $tr = $(this).closest('tr');
                $tr.find('.editfield').hide();
                $tr.find('.readonly').show();
                $tr.find('.editBtn').show();
                $tr.find('.editForm').hide();
            });

            $('.editForm').submit(function () {
                var $tr = $(this).closest('tr');
                var desc = $tr.find('input[name="description"]').val();
                $(this).find('.editDescriptionInput').val(desc);
            });

            $('.deleteBtn').click(function (e) {
                if (!confirm('Удалить заявку? Это действие нельзя отменить!')) {
                    e.preventDefault();
                } else {
                    $(this).closest('form').submit();
                }
            });
        });
    </script>
</#macro>

<@display_page/>

<#include "../base.ftl">

<#macro page_head>
    <title>Мои заявки (сотрудник)</title>
    <style>
        .editfield {
            display: none;
        }

        .editfield input[type="number"] {
            width: 50px;
        }

        label {
            cursor: pointer;
            user-select: none;
        }

        label input[type="checkbox"] {
            margin-right: 5px;
        }
    </style>
</#macro>

<#macro page_body>
    <div class="div-table-wrapper">
        <h2 style="margin: 50px 0 20px">Свободные заявки</h2>

        <table id="freeOrdersTable" class="staff-orders-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Клиент</th>
                <th>Оборудование</th>
                <th>Статус</th>
                <th>Описание</th>
                <th>Создана</th>
                <th>Услуги</th>
                <th>Запчасти и использованное количество</th>
                <th>Сумма</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            <#list freeOrders as order>
                <tr data-order-id="${order.id}">
                    <td data-label="ID">${order.id}</td>
                    <td data-label="Клиент">
                        <#if order.client??>${order.client.name} ${order.client.lastname}<#else>(клиент не найден)</#if>
                    </td>
                    <td data-label="Оборудование">
                        <#if order.equipment??>${order.equipment.type} ${order.equipment.brand} ${order.equipment.model}
                        <#else>(оборудование не найдено)</#if>
                    </td>
                    <td data-label="Статус">${order.status}</td>
                    <td data-label="Описание">${order.description!}</td>
                    <td data-label="Создана">${order.createdAt!}</td>
                    <td data-label="Услуги">
                        <#list order.services as service>
                            <div>${service.name}</div></#list>
                    </td>
                    <td data-label="Запчасти и количество">
                        <#list order.parts as partQuantity>
                            <div>${partQuantity.part.name}: ${partQuantity.quantity}</div></#list>
                    </td>
                    <td data-label="Сумма">${order.price!} ₽</td>
                    <td data-label="Действия">
                        <div class="actions-btns">
                            <form action="/staff/orders" method="post" class="takeForm" style="display:inline;">
                                <input type="hidden" name="action" value="take">
                                <input type="hidden" name="id" value="${order.id}">
                                <button type="button" class="takeBtn" style="color:green;">Занять</button>
                            </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <div class="div-table-wrapper">
        <h2 style="margin: 50px 0 20px 0">Мои заявки</h2>

        <table id="myOrdersTable" class="staff-orders-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Клиент</th>
                <th>Оборудование</th>
                <th>Статус</th>
                <th>Описание</th>
                <th>Создана</th>
                <th>Завершено</th>
                <th>Услуги</th>
                <th>Запчасти и использованное количество</th>
                <th>Сумма</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            <#list myOrders as order>
                <tr data-order-id="${order.id}">
                    <td data-label="ID">${order.id}</td>
                    <td data-label="Клиент">
                        <#if order.client??>${order.client.name} ${order.client.lastname}<#else>(клиент не найден)</#if>
                    </td>
                    <td data-label="Оборудование">
                        <#if order.equipment??>${order.equipment.type} ${order.equipment.brand} ${order.equipment.model}
                        <#else>(оборудование не найдено)</#if>
                    </td>
                    <td data-label="Статус">
                        <span class="readonly">${order.status}</span>
                        <select class="editfield" name="status" style="display:none;">
                            <option value="NEW" <#if order.status=="NEW">selected</#if>>NEW</option>
                            <option value="IN_PROGRESS" <#if order.status=="IN_PROGRESS">selected</#if>>IN_PROGRESS
                            </option>
                            <option value="COMPLETED" <#if order.status=="COMPLETED">selected</#if>>COMPLETED</option>
                        </select>
                    </td>
                    <td data-label="Описание">${order.description!}</td>
                    <td data-label="Создана">${order.createdAt!}</td>
                    <td data-label="Завершено">
                            <span class="readonly">
                                <#if order.completedAt??>${order.completedAt}
                                <#else>Не выполнено
                                </#if>
                            </span>
                        <input type="datetime-local" class="editfield" name="completedAt"
                               value="${order.completedAt!"Не выполнено"}" style="display:none;">
                    </td>
                    <td data-label="Услуги">
                            <span class="readonly">
                                <#list order.services as service>
                                    <div>${service.name}</div></#list>
                            </span>
                        <div class="editfield" style="display:none;">
                            <#list services as serviceOption>
                                <label>
                                    <input type="checkbox" name="services" value="${serviceOption.id}"
                                            <#if order.services??>
                                        <#list order.services as s>
                                            <#if s.id == serviceOption.id>checked</#if>
                                        </#list>
                                            </#if>>
                                    ${serviceOption.name}
                                </label><br/>
                            </#list>
                        </div>
                    </td>
                    <td data-label="Запчасти и количество">
                            <span class="readonly">
                                <#list order.parts as partQuantity>
                                    <div>${partQuantity.part.name}: ${partQuantity.quantity}</div></#list>
                            </span>
                        <div class="editfield" style="display:none;">
                            <#list parts as partOption>
                                <label>
                                    <input type="checkbox" name="parts" value="${partOption.id}"
                                            <#if order.parts??>
                                        <#list order.parts as partQuantity>
                                            <#if partQuantity.part.id == partOption.id>checked</#if>
                                        </#list>
                                            </#if>>
                                    ${partOption.name}
                                </label>
                                <input type="number" name="part_quantity_${partOption.id}" min="1" step="1"
                                       max="<#list parts as p><#if p.id == partOption.id>${p.quantity}</#if></#list>"
                                       value="<#local q=0><#list order.parts as partQuantity><#if partQuantity.part.id == partOption.id><#local q=partQuantity.quantity></#if></#list>${q}"
                                       style="width: 60px; margin-bottom: 5px;">
                                <br/>
                            </#list>
                        </div>
                    </td>
                    <td data-label="Сумма">${order.price!} ₽</td>
                    <td data-label="Действия">
                        <button type="button" class="editBtn">Редактировать</button>
                        <form action="/staff/orders" method="post" class="editForm" style="display:none; margin:0;">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="id" value="${order.id}">
                            <input type="hidden" class="editStatus" name="status">
                            <input type="hidden" class="editCompletedAt" name="completedAt">
                            <input type="hidden" class="editServicesAndParts" name="services">
                            <input type="hidden" class="editServicesAndParts" name="parts">
                            <button type="submit">Подтвердить</button>
                            <button type="button" class="cancelBtn">Отмена</button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(document).ready(function () {
            $('.takeBtn').on('click', function () {
                var $tr = $(this).closest('tr');
                var orderId = $tr.data('order-id');
                if (confirm('Вы действительно хотите занять заявку ID ' + orderId + '?')) {
                    $(this).closest('form.takeForm').submit();
                }
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

                var selectedServices = [];
                $tr.find('input[type="checkbox"][name="services"]:checked').each(function () {
                    selectedServices.push($(this).val());
                });

                var selectedParts = [];
                $tr.find('input[type="checkbox"][name="parts"]:checked').each(function () {
                    var partId = $(this).val();
                    var qtyInput = $tr.find('input[name="part_quantity_' + partId + '"]');
                    var qty = qtyInput.length ? qtyInput.val() : 1;
                    selectedParts.push(partId + ":" + qty);
                });

                var servicesData = [];
                $tr.find('input[type="checkbox"][name="services"]').each(function () {
                    var id = $(this).val();
                    var checked = $(this).is(':checked') ? "1" : "0";
                    servicesData.push(id + ":" + checked);
                });

                var selectedParts = [];
                $tr.find('input[type="checkbox"][name="parts"]').each(function () {
                    var partId = $(this).val();
                    var checked = $(this).is(':checked');
                    var qtyInput = $tr.find('input[name="part_quantity_' + partId + '"]');
                    var qty = qtyInput.length ? qtyInput.val() : 0;
                    if (!checked) {
                        qty = 0;
                    }
                    selectedParts.push(partId + ":" + qty);
                });

                $form.find('.editStatus').val($tr.find('select[name="status"]').val());
                $form.find('.editCompletedAt').val($tr.find('input[name="completedAt"]').val());
                $form.find('input[name="services"]').val(selectedServices.join(","));
                $form.find('input[name="parts"]').val(selectedParts.join(","));
            });

            $('input[type="number"][name^="part_quantity_"]').on('input', function () {
                var max = parseInt($(this).attr('max')) || 1;
                var val = parseInt($(this).val());

                if (val > max) {
                    $(this).val(max);
                }
                if (val < 0 || isNaN(val)) {
                    $(this).val(0);
                }
            });
        });
    </script>

</#macro>

<@display_page/>

<#include "../base.ftl">

<#macro page_head>
    <title>Заявки (админ)</title>
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
    <h2>Управление заявками</h2>
    <#if error??>
        <div style="color:red;">${error}</div>
    </#if>

    <table border="1" id="ordersTable">
        <thead>
        <tr>
            <th>ID</th>
            <th>Клиент</th>
            <th>Оборудование</th>
            <th>Техник</th>
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
        <#list orders as order>
            <tr data-order-id="${order.id}">
                <td>${order.id}</td>
                <td>
                    <#if order.client??>
                        ${order.client.name} ${order.client.lastname}
                    <#else>
                        (клиент не найден)
                    </#if>
                </td>
                <td>
                    <#if order.equipment??>
                        ${order.equipment.type} ${order.equipment.brand} ${order.equipment.model}
                    <#else>
                        (оборудование не найдено)
                    </#if>
                </td>
                <td>
                    <span class="readonly">
                        <#if order.technician??>
                            ${order.technician.name} (${order.technician.position!})
                        <#else>
                            (не назначен)
                        </#if>
                    </span>
                    <select class="editfield" name="employeeId" style="display:none;">
                        <option value="">(не назначен)</option>
                        <#list employees as emp>
                            <option value="${emp.id}"<#if order.technician?? && order.technician.id == emp.id> selected</#if>>
                                ${emp.name} (${emp.position!}) [${emp.role}]
                            </option>
                        </#list>
                    </select>
                </td>
                <td>
                    <span class="readonly">${order.status}</span>
                    <select class="editfield" name="status" style="display:none;">
                        <option value="NEW"<#if order.status=="NEW"> selected</#if>>NEW</option>
                        <option value="IN_PROGRESS"<#if order.status=="IN_PROGRESS"> selected</#if>>IN_PROGRESS</option>
                        <option value="COMPLETED"<#if order.status=="COMPLETED"> selected</#if>>COMPLETED</option>
                    </select>
                </td>
                <td>
                    <span class="readonly">${order.description!}</span>
                    <input type="text" class="editfield" name="description" value="${order.description!}"
                           style="display:none;">
                </td>
                <td>${order.createdAt!}</td>
                <td>
                    <span class="readonly">
                        <#if order.completedAt??>
                            ${order.completedAt}
                        <#else>
                            Не выполнено
                        </#if>
                    </span>
                    <input type="datetime-local" class="editfield" name="completedAt"
                           value="${order.completedAt?string('yyyy-MM-dd\'T\'HH:mm')!}" style="display:none;">
                </td>

                <td>
                    <span class="readonly">
                        <#list order.services as service>
                            <div>${service.name}</div>
                        </#list>
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

                <td>
                    <span class="readonly">
                        <#list order.parts as partQuantity>
                            <div>${partQuantity.part.name}: ${partQuantity.quantity}</div>
                        </#list>
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

                <td>${order.price!}</td>

                <td>
                    <button type="button" class="editBtn">Редактировать</button>
                    <form action="/admin/orders" method="post" class="editForm" style="display:none; margin:0;">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="id" value="${order.id}">
                        <input type="hidden" class="editTechnicianId" name="employeeId">
                        <input type="hidden" class="editStatus" name="status">
                        <input type="hidden" class="editDescription" name="description">
                        <input type="hidden" class="editCompletedAt" name="completedAt">
                        <input type="hidden" class="editServicesAndParts" name="services">
                        <input type="hidden" class="editServicesAndParts" name="parts">
                        <button type="submit">Подтвердить</button>
                        <button type="button" class="cancelBtn">Отмена</button>
                    </form>
                    <form action="/admin/orders" method="post" class="deleteForm" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${order.id}">
                        <button type="button" class="deleteBtn" style="color:red;">Удалить</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

    <script>
        document.querySelectorAll('input[type="number"][name^="part_quantity_"]').forEach(function (input) {
            input.addEventListener('input', function () {
                var max = parseInt(this.max);
                var val = parseInt(this.value);

                if (val > max) {
                    this.value = max;
                }
                if (val < 1 || isNaN(val)) {
                    this.value = 1;
                }
            });
        });

        document.querySelectorAll('.editBtn').forEach(function (btn) {
            btn.onclick = function () {
                var tr = btn.closest('tr');
                tr.querySelectorAll('.readonly').forEach(function (el) {
                    el.style.display = 'none';
                });
                tr.querySelectorAll('.editfield').forEach(function (el) {
                    el.style.display = 'inline';
                });
                btn.style.display = 'none';
                tr.querySelector('.editForm').style.display = 'inline';
            };
        });

        document.querySelectorAll('.cancelBtn').forEach(function (btn) {
            btn.onclick = function () {
                var tr = btn.closest('tr');
                tr.querySelectorAll('.editfield').forEach(function (el) {
                    el.style.display = 'none';
                });
                tr.querySelectorAll('.readonly').forEach(function (el) {
                    el.style.display = 'inline';
                });
                tr.querySelector('.editBtn').style.display = 'inline';
                tr.querySelector('.editForm').style.display = 'none';
            };
        });

        document.querySelectorAll('.editForm').forEach(function (form) {
            form.onsubmit = function () {
                var tr = form.closest('tr');

                var selectedServices = [];
                tr.querySelectorAll('input[type="checkbox"][name="services"]:checked').forEach(function (checkbox) {
                    selectedServices.push(checkbox.value);
                });

                var selectedParts = [];
                tr.querySelectorAll('input[type="checkbox"][name="parts"]:checked').forEach(function (checkbox) {
                    var partId = checkbox.value;
                    var qtyInput = tr.querySelector('input[name="part_quantity_' + partId + '"]');
                    var qty = qtyInput ? qtyInput.value : 1;
                    selectedParts.push(partId + ":" + qty);
                });
                form.querySelector('.editTechnicianId').value = tr.querySelector('select[name="employeeId"]').value;
                form.querySelector('.editStatus').value = tr.querySelector('select[name="status"]').value;
                form.querySelector('.editDescription').value = tr.querySelector('input[name="description"]').value;
                form.querySelector('.editCompletedAt').value = tr.querySelector('input[name="completedAt"]').value;
                form.querySelector('input[name="services"]').value = selectedServices.join(",");
                form.querySelector('input[name="parts"]').value = selectedParts.join(",");
            };
        });

        document.querySelectorAll('.deleteBtn').forEach(function (btn) {
            btn.onclick = function () {
                var tr = btn.closest('tr');
                var name = tr.querySelector('span.readonly').innerText;
                if (confirm('Удалить заявку "' + name + '"? Это действие нельзя отменить!')) {
                    btn.closest('form.deleteForm').submit();
                }
            };
        });
    </script>
</#macro>

<@display_page/>

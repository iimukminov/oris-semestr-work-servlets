<#include "../base.ftl">

<#macro page_head>
    <title>Заявки (админ)</title>
</#macro>

<#macro page_body>
    <h2>Управление заявками</h2>
    <#if error??>
        <div style="color:red;">${error}</div>
    </#if>
    <div style="overflow-x:auto;">
        <table id="ordersTable" class="pidor">
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
                            <option value="IN_PROGRESS"<#if order.status=="IN_PROGRESS"> selected</#if>>IN_PROGRESS
                            </option>
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
                        <#if order.completedAt?? && order.completedAt?has_content>
                            ${order.completedAt}
                        <#else>
                            Не выполнено
                        </#if>
                    </span>
                        <input type="datetime-local" class="editfield" name="completedAt"
                               value="${order.completedAt!"Не выполнено"}" style="display:none;">
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

                    <td>${order.price!} ₽</td>

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
    </div>
    <script>
        $(document).ready(function () {
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

                $(this).find('.editTechnicianId').val($tr.find('select[name="employeeId"]').val());
                $(this).find('.editStatus').val($tr.find('select[name="status"]').val());
                $(this).find('.editDescription').val($tr.find('input[name="description"]').val());
                $(this).find('.editCompletedAt').val($tr.find('input[name="completedAt"]').val());
                $(this).find('input[name="services"]').val(selectedServices.join(','));
                $(this).find('input[name="parts"]').val(selectedParts.join(','));
            });

            $('.deleteBtn').on('click', function () {
                var $tr = $(this).closest('tr');
                var name = $tr.find('span.readonly').first().text();
                if (confirm('Удалить заявку "' + name + '"? Это действие нельзя отменить!')) {
                    $(this).closest('form.deleteForm').submit();
                }
            });

            $('input[type="number"][name^="part_quantity_"]').on('input', function () {
                var max = parseInt($(this).attr('max')) || 1;
                var val = parseInt($(this).val());

                if (val > max) {
                    $(this).val(max);
                }
                if (val < 1 || isNaN(val)) {
                    $(this).val(1);
                }
            });
        });

    </script>
</#macro>

<@display_page/>

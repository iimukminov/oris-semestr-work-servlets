<#include "../base.ftl">

<#macro page_head>
    <title>Список доступных услуг</title>
</#macro>

<#macro page_body>
    <h2>Услуги</h2>
    <#if error??>
        <div style="color:red;">${error}</div>
    </#if>
    <table border="1" id="servicesTable">
        <thead>
        <tr>
            <th>Название</th>
            <th>Описание</th>
            <th>Цена</th>
        </tr>
        </thead>
        <tbody>
        <#list services as service>
            <tr data-service-id="${service.id}">
                <td><span class="readonly">${service.name}</span></td>
                <td><span class="readonly">${service.description!}</span></td>
                <td><span class="readonly">${service.price} ₽</span></td>
            </tr>
        </#list>
        </tbody>
    </table>
</#macro>
<@display_page/>

<#include "../base.ftl">

<#macro page_head>
    <title>Список доступных услуг</title>
</#macro>

<#macro page_body>
    <main>
        <h2>Услуги</h2>
        <#if error??>
            <div style="color:red;">${error}</div>
        </#if>
        <table id="servicesTable">
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
    </main>
</#macro>
<@display_page/>

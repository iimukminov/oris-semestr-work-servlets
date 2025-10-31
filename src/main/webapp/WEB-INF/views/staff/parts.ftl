<#include "../base.ftl">

<#macro page_head>
    <title>Запчасти</title>
</#macro>

<#macro page_body>
    <h2>Список доступных запчастей</h2>
    <#if error??>
        <div style="color:red;">${error}</div>
    </#if>
    <table border="1" id="partsTable">
        <thead>
        <tr>
            <th>Название</th>
            <th>Количество</th>
            <th>Цена</th>
        </tr>
        </thead>
        <tbody>
        <#list parts as part>
            <tr data-part-id="${part.id}">
                <td><span class="readonly">${part.name}</span></td>
                <td><span class="readonly">${part.quantity!}</span></td>
                <td><span class="readonly">${part.price} ₽</span></td>
            </tr>
        </#list>
        </tbody>
    </table>
</#macro>
<@display_page/>

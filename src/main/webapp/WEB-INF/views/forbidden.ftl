<#include "base.ftl">

<#macro page_head>
    <title>Доступ запрещён (403)</title>
</#macro>

<#macro page_body>
    <h2>Доступ запрещён (403)</h2>
    <p>У вас нет прав на просмотр этой страницы.</p>
    <a href="/dashboard">На главную</a>
</#macro>

<@display_page/>

<#include "base.ftl">

<#macro page_head>
    <title>Главная</title>
</#macro>

<#macro page_body>
    <h2>Добро пожаловать, ${user.name}!</h2>
    <p>Здесь отображается основная статистика и навигация.</p>
</#macro>

<@display_page/>

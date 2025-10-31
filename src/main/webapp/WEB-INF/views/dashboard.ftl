<#include "base.ftl">

<#macro page_head>
    <title>Главная</title>
    <style>
        body {
            background-image: url('/img/bg1.jpg');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            min-height: 100vh;
            margin: 0;
        }

        .welcome-container {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: flex-end;
            padding-right: 8vw;
        }

        .welcome-block {
            background: rgba(0, 0, 0, 0.60);
            border-radius: 20px;
            padding: 48px 56px;
            box-shadow: 0 6px 32px rgba(0, 0, 0, 0.25);
            text-align: center;
            max-width: 560px;
        }

        h2 {
            font-size: 3.5rem;
            margin-bottom: 1.2rem;
            color: #fff;
        }

        p {
            font-size: 1.7rem;
            color: #fff;
        }

        footer {
            display: none;
        }
    </style>
</#macro>

<#macro page_body>
    <div class="welcome-container">
        <div class="welcome-block">
            <#if user??>
                <h2>Добро пожаловать, ${user.name}!</h2>
            <#else>
                <h2>Добро пожаловать!</h2>
            </#if>
            <p>Мы чиним технику</p>
        </div>
    </div>
</#macro>

<@display_page/>

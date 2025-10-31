<!DOCTYPE html>
<html>
<head>
    <title>404 – Такой страницы нет</title>
    <style>
        body {
            margin: 0;
            min-height: 100vh;
            background: #6e94fb url("/img/error-bg2.jpg") no-repeat center center fixed;
            background-size: cover;
            color: #fff;
            font-family: 'Segoe UI', 'Arial', sans-serif;
            text-align: center;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .main-404 {
            width: 100%;
            max-width: 500px;
            background: white;
            border-radius: 30px;
            box-sizing: border-box;
        }
        .main-404 h1 {
            font-size: 3em;
            font-weight: bold;
            color: black;
            margin-bottom: 18px;
        }
        .main-404 .code-block {
            display: inline-block;
            background: #dab9fa;
            color: #fff;
            font-size: 2em;
            font-weight: bold;
            border-radius: 16px;
            padding: 0 25px;
            margin-left: 10px;
        }
        .main-404 p {
            font-size: 1.2em;
            color: black;
            margin: 28px 0 34px 0;
        }
        a.btn404 {
            background: #fff;
            color: #5981f9;
            border: none;
            border-radius: 24px;
            font-size: 1em;
            padding: 16px 32px;
            margin: 16px 20px 20px 20px;
            cursor: pointer;
            display: inline-block;
            text-decoration: none;
            transition: background 0.2s, color 0.2s;
        }
        a.btn404:hover {
            background: #5981f9;
            color: #fff;
        }
    </style>
</head>
<body>
<div class="main-404">
    <h1>😕</h1>
    <h1>Такой страницы нет<br>
        <span class="code-block">404</span>
    </h1>
    <p>Она спряталась от кабана, потому что не знает, как с ним драться<br>
    Проверьте URL или вернитесь на главную.</p>
    <a href="/dashboard" class="btn404">На главную</a>
</div>
</body>
</html>

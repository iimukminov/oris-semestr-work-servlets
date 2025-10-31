<!DOCTYPE html>
<html>
<head>
    <title>Ошибка ${statusCode!}</title>
    <style>
        body {
            margin: 0;
            min-height: 100vh;
            background: #6e94fb no-repeat center center fixed;
            background-size: cover;
            color: #fff;
            font-family: 'Segoe UI', 'Arial', sans-serif;
            text-align: center;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .main-error {
            width: 100%;
            max-width: 500px;
            box-sizing: border-box;
        }
        .main-error h1 {
            font-size: 3em;
            font-weight: bold;
            margin-bottom: 18px;
        }
        .main-error .error-block {
            display: inline-block;
            background: #dab9fa;
            color: #fff;
            font-size: 2em;
            font-weight: bold;
            border-radius: 16px;
            padding: 0 25px;
            margin-left: 10px;
        }
        .main-error p {
            font-size: 1.2em;
            margin: 28px 0 34px 0;
        }
        a.btn404 {
            background: #fff;
            color: #5981f9;
            border: none;
            border-radius: 24px;
            font-size: 1em;
            padding: 16px 32px;
            margin: 16px 20px 0 20px;
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
<div class="main-error">
    <h1>Упс! Ошибка <span class="error-block">${statusCode!'?'}</span></h1>
    <a href="/sign-up" class="btn404">Вернуться на главную</a>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br" data-theme="lofi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css" rel="stylesheet" type="text/css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Cadastro - IFSales</title>
</head>
<body class="min-h-screen bg-base-200">
    <div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <h2 class="mt-16 mb-4 text-2xl font-bold text-center text-secondary">Crie sua conta</h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <form action="ControllerServlet" method="post" class="space-y-6">
                <div>
                    <label for="email" class="font-semibold">E-mail<span class="text-error">*</span></label>
                    <input type="email" id="email" name="email" required class="input input-bordered w-full mt-2">
                </div>
                <span id="error-email" class="text-error hidden"></span>

                <div>
                    <label for="password" class="font-semibold">Senha<span class="text-error">*</span></label>
                    <input type="password" id="password" name="password" required class="input input-bordered w-full mt-2">
                </div>
                <span id="error-password" class="text-error hidden"></span>

                <div>
                    <label for="confirmPassword" class="font-semibold">Confime sua senha<span class="text-error">*</span></label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required class="input input-bordered w-full mt-2">
                </div>
                <span id="error-confirmPassword" class="text-error hidden"></span>

                <div class="space-y-2">
                    <button type="submit" name="action" value="createUser" class="btn btn-primary btn-block">
                        Cadastrar
                    </button>

                    <a href="login.jsp" class="btn btn-outline btn-block">
                        Voltar
                    </a>
                </div>
            </form>
        </div>
    </div>

    <script defer src="./scripts/validateUserRegister.js"></script>
</body>
</html>
let papelSelecionado = "";

function mostrarLogin(papel) {
    papelSelecionado = papel;
    document.getElementById("login-form").style.display = "block";
}

// Função de login
async function fazerLogin() {
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

    if (!email || !senha) {
        alert("Preencha email e senha.");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, senha })
        });

        if (response.ok) {
            const user = await response.json();
            localStorage.setItem("usuario", JSON.stringify(user));

            // Redireciona conforme o papel
            if (papelSelecionado === "ADM" && user.papel === "ADMIN") {
                window.location.href = "adm/admEntrada.html";
            } else if (papelSelecionado === "GARCOM" && user.papel === "GARCOM") {
                window.location.href = "garcom/garcomEntrada.html";
            } else {
                alert("Papel incorreto ou usuário inválido!");
            }

        } else {
            const msg = await response.text();
            alert("Erro: " + msg);
        }
    } catch (err) {
        alert("Erro ao conectar com o servidor.");
        console.error(err);
    }
}

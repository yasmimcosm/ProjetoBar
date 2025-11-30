async function carregarCardapio() {
    const tabela = document.getElementById("tabela-cardapio");

    const resp = await fetch("http://localhost:8080/api/cardapio");
    const itens = await resp.json();

    itens.forEach(i => {
        tabela.innerHTML += `
        <tr>
            <td>${i.nome}</td>
            <td>${i.categoria}</td>
            <td>R$ ${i.preco}</td>
            <td>
                <button class="btn-login" onclick="editarItem(${i.id})">Editar</button>
                <button class="btn-login" onclick="excluirItem(${i.id})">Excluir</button>
            </td>
        </tr>
        `;
    });
}

function verificarSenha() {
    const senhaDigitada = document.getElementById("senha").value;

    const senhaCorreta = "admin123"; // você escolhe

    if (senhaDigitada === senhaCorreta) {
        window.location.href = "/admEntrada.html";
    } else {
        document.getElementById("msg").textContent = "Senha incorreta!";
    }
}


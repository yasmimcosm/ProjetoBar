// abrir mesa
async function abrirMesa() {
    const mesa = document.getElementById("numeroMesa").value;
    const pessoas = document.getElementById("qtdPessoas").value;

    const resp = await fetch("http://localhost:8080/api/mesa/abrir", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ numero: mesa, pessoas: pessoas })
    });

    const msg = await resp.text();
    alert(msg);
    if (resp.ok) history.back();
}

// carregar mesas abertas
async function carregarMesas() {
    const tabela = document.getElementById("tabela-mesas");

    const resp = await fetch("http://localhost:8080/api/mesa/abertas");
    const mesas = await resp.json();

    mesas.forEach(m => {
        tabela.innerHTML += `
        <tr>
            <td>${m.numero}</td>
            <td>${m.pessoas}</td>
            <td>
                <button class="btn-login" onclick="window.location.href='pedidos.html?mesa=${m.numero}'">
                    Adicionar Item
                </button>
                <button class="btn-login" onclick="removerItem(${m.numero})">Remover Item</button>
                <button class="btn-login" onclick="toggleCouvert(${m.numero})">Couvert ON/OFF</button>
                <button class="btn-login" onclick="fecharConta(${m.numero})">Fechar Conta</button>
            </td>
        </tr>`;
    });
}

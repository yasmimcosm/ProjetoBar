const API = "http://localhost:8080/api/garcom";
let mesaSelecionada = null; // Guarda a conta que estamos manipulando

// ----------------------------
// ABRIR MESA
// ----------------------------
async function abrirMesa() {
    const numeroMesa = parseInt(document.getElementById("numeroMesa").value);
    const numeroPessoas = parseInt(document.getElementById("qtdPessoas").value);
    const pagarCouvert = document.getElementById("pagarCouvert").value === "true";
    const couvertIndividual = document.getElementById("couvertIndividual").value === "true";

    const novaConta = {
        numeroMesa,
        numeroPessoas,
        pagarCouvert,
        couvertIndividual,
        total: 0,
        totalPago: 0,
        contaFechada: false
    };

    try {
        const resp = await fetch(`${API}/abrirMesa`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(novaConta)
        });

        if (!resp.ok) {
            const msg = await resp.text();
            throw new Error("Erro ao abrir mesa: " + msg);
        }

        const conta = await resp.json();
        alert(`Mesa ${conta.mesa.numero} aberta com sucesso!`);
        window.location.href = "garcomMesas.html";

    } catch (err) {
        alert(err.message);
    }
}

// ----------------------------
// CARREGAR MESAS ABERTAS
// ----------------------------
async function carregarMesas() {
    const resp = await fetch(`${API}/contasAbertas`);
    const contas = await resp.json();

    const tabela = document.querySelector("table");
    tabela.innerHTML = `
        <tr>
            <th>Mesa</th>
            <th>Pessoas</th>
            <th>Ações</th>
        </tr>
    `;

    contas.forEach(conta => {
        tabela.innerHTML += `
            <tr>
                <td>${conta.mesa.id}</td>
                <td>${conta.numeroPessoas}</td>
                <td>
                    <button class="btn-login" onclick="selecionarMesa(${conta.id})">Pedidos</button>
                    <button class="btn-login" onclick="fecharConta(${conta.id})">Fechar Conta</button>
                </td>
            </tr>
        `;
    });
}

// ----------------------------
// SELECIONAR MESA PARA PEDIDOS
// ----------------------------
function selecionarMesa(contaId) {
    mesaSelecionada = contaId;
    localStorage.setItem("mesaSelecionada", contaId);
    location.href = "pedidos.html";
}

// ----------------------------
// CARREGAR ITENS DO CARDÁPIO
// ----------------------------
async function carregarCardapio() {
    const resp = await fetch("http://localhost:8080/api/cardapio");
    const itens = await resp.json();

    const tabela = document.querySelector("table");
    tabela.innerHTML = `
        <tr>
            <th>Item</th>
            <th>Categoria</th>
            <th>Preço</th>
            <th>Ação</th>
        </tr>
    `;

    itens.forEach(item => {
        tabela.innerHTML += `
            <tr>
                <td>${item.nome}</td>
                <td>${item.categoria}</td>
                <td>R$ ${item.preco.toFixed(2)}</td>
                <td>
                    <button class="btn-login" onclick="adicionarPedido(${item.id}, '${item.nome}', ${item.preco})">Adicionar</button>
                </td>
            </tr>
        `;
    });
}

// ----------------------------
// ADICIONAR PEDIDO
// ----------------------------
async function adicionarPedido(itemId, nome, preco) {
    if (!mesaSelecionada) mesaSelecionada = localStorage.getItem("mesaSelecionada");
    if (!mesaSelecionada) return alert("Selecione uma mesa!");

    const pedido = {
        itemId: itemId,
        nome: nome,
        preco: preco,
        quantidade: 1
    };

    await fetch(`${API}/conta/${mesaSelecionada}/pedido`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(pedido)
    });

    alert(`Item "${nome}" adicionado à mesa!`);
}

// ----------------------------
// REMOVER PEDIDO
// ----------------------------
async function removerPedido(pedidoId) {
    if (!mesaSelecionada) mesaSelecionada = localStorage.getItem("mesaSelecionada");
    if (!mesaSelecionada) return alert("Selecione uma mesa!");

    await fetch(`${API}/conta/${mesaSelecionada}/pedido/${pedidoId}`, { method: "DELETE" });
    alert("Pedido removido!");
    carregarCardapio();
}

// ----------------------------
// PAGAMENTO
// ----------------------------
async function registrarPagamento(valor) {
    if (!mesaSelecionada) mesaSelecionada = localStorage.getItem("mesaSelecionada");

    await fetch(`${API}/conta/${mesaSelecionada}/pagamento?valor=${valor}`, { method: "PATCH" });
    alert(`Pagamento de R$ ${valor.toFixed(2)} registrado!`);
}

// ----------------------------
// FECHAR CONTA
// ----------------------------
async function fecharConta(contaId) {
    await fetch(`${API}/conta/${contaId}/fechar`, { method: "PATCH" });
    alert("Conta fechada!");
    carregarMesas();
}

// ----------------------------
// ONLOAD
// ----------------------------
window.onload = () => {
    if (document.body.id === "abrir") {
        // página abrir.html
    } else if (document.body.id === "mesas") {
        carregarMesas();
    } else if (document.body.id === "pedidos") {
        mesaSelecionada = localStorage.getItem("mesaSelecionada");
        carregarCardapio();
    }
};

async function cancelarPedido(pedidoId) {
    const motivo = prompt("Digite o motivo do cancelamento:");
    if (!motivo) return alert("Cancelamento precisa de motivo!");

    await fetch(`http://localhost:8080/api/conta/${mesaSelecionada}/pedidos/${pedidoId}?motivo=${encodeURIComponent(motivo)}`, {
        method: "DELETE"
    });
    alert("Pedido cancelado!");
    carregarPedidos();
}

const garcomCadastrado = {
    usuario: "joao",
    senha: "1234"
};

async function verificarGarcom() {
    const senha = document.getElementById("senha").value;

    try {
        const resp = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ senha: senha })
        });

        if (!resp.ok) {
            document.getElementById("msg").textContent = "Erro ao conectar!";
            return;
        }

        const result = await resp.json();

        if (result.autorizado === true) {
            window.location.href = "/garcom/garcomEntrada.html";
        } else {
            document.getElementById("msg").textContent = "Usuário ou senha incorretos!";
        }

    } catch (error) {
        document.getElementById("msg").textContent = "Erro de conexão!";
        console.error(error);
    }
}

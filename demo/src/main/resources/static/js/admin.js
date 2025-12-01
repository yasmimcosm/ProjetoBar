const API = "http://localhost:8080/api/cardapio";
let editandoId = null;

// ----------------------------
// CARREGAR CARDÁPIO
// ----------------------------
async function carregarCardapio() {
    const resp = await fetch(`${API}`);
    const dados = await resp.json();

    const tabela = document.getElementById("tabela-cardapio");
    tabela.innerHTML = `
        <tr>
            <th>Nome</th>
            <th>Categoria</th>
            <th>Preço</th>
            <th>Ações</th>
        </tr>
    `;

    dados.forEach(item => {
        tabela.innerHTML += `
            <tr>
                <td>${item.nome}</td>
                <td>${item.categoria}</td>
                <td>R$ ${item.preco.toFixed(2)}</td>
                <td>
                    <button onclick="abrirModalEditar(${item.id}, '${item.nome}', '${item.categoria}', ${item.preco})">Editar</button>
                    <button onclick="excluirItem(${item.id})">Excluir</button>
                </td>
            </tr>
        `;
    });
}

// ----------------------------
// ABRIR MODAL ADICIONAR
// ----------------------------
function abrirModalAdicionar() {
    editandoId = null;
    document.getElementById("modal-titulo").textContent = "Novo Item";
    document.getElementById("nome").value = "";
    document.getElementById("categoria").value = "";
    document.getElementById("preco").value = "";
    document.getElementById("overlay").style.display = "block";
    document.getElementById("modal").style.display = "block";
}

// ----------------------------
// ABRIR MODAL EDITAR
// ----------------------------
function abrirModalEditar(id, nome, categoria, preco) {
    editandoId = id;
    document.getElementById("modal-titulo").textContent = "Editar Item";
    document.getElementById("nome").value = nome;
    document.getElementById("categoria").value = categoria;
    document.getElementById("preco").value = preco;
    document.getElementById("overlay").style.display = "block";
    document.getElementById("modal").style.display = "block";
}

// ----------------------------
// FECHAR MODAL
// ----------------------------
function fecharModal() {
    document.getElementById("overlay").style.display = "none";
    document.getElementById("modal").style.display = "none";
}

// ----------------------------
// SALVAR ITEM (POST ou PUT)
// ----------------------------
async function salvarItem() {
    const nome = document.getElementById("nome").value;
    const categoria = document.getElementById("categoria").value;
    const preco = parseFloat(document.getElementById("preco").value);

    if (!nome || !categoria || isNaN(preco)) {
        alert("Preencha todos os campos corretamente!");
        return;
    }

    const item = {
        nome: nome,
        categoria: categoria,
        preco: preco,
        tipo: categoria.toLowerCase() === "bebida" ? 2 : 3
    };

    if (editandoId == null) {
        // Criar novo item
        await fetch(`${API}`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(item)
        });
    } else {
        // Editar item existente
        await fetch(`${API}/${editandoId}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(item)
        });
    }

    fecharModal();
    carregarCardapio();
}

// ----------------------------
// EXCLUIR ITEM
// ----------------------------
async function excluirItem(id) {
    if (!confirm("Tem certeza que deseja excluir este item?")) return;

    await fetch(`${API}/${id}`, { method: "DELETE" });
    carregarCardapio();
}

// ----------------------------
// LOGIN (se usar senha admin)
// ----------------------------
async function verificarSenha() {
    let senha = document.getElementById("senha").value;

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
        window.location.href = "/adm/admEntrada.html";
    } else {
        document.getElementById("msg").textContent = "Senha incorreta!";
    }
}

// ----------------------------
// ONLOAD
// ----------------------------
window.onload = carregarCardapio;

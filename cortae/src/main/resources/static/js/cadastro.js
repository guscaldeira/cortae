document.addEventListener("DOMContentLoaded", function () {

     const botoesToggle = document.querySelectorAll(".cadastro-campo__toggle-senha");
 
    botoesToggle.forEach(function (botao) {
        botao.addEventListener("click", function () {
            const idAlvo = botao.getAttribute("data-alvo");
            const input = document.getElementById(idAlvo);
            const icone = botao.querySelector("i");
 
            const estaVisivel = input.type === "text";
 
            input.type = estaVisivel ? "password" : "text";
            botao.setAttribute("aria-pressed", String(!estaVisivel));
            botao.setAttribute("aria-label", estaVisivel ? "Mostrar senha" : "Ocultar senha");
 
            icone.classList.toggle("bi-eye", estaVisivel);
            icone.classList.toggle("bi-eye-slash", !estaVisivel);
        });
    });

    const form = document.querySelector(".cadastro-form");
    const campoSenha = document.getElementById("senha");
    const campoConfirmarSenha = document.getElementById("confirmarSenha");
    const mensagemErro = document.getElementById("erro-confirmar-senha");
 
    function senhasCoincidem() {
        return campoSenha.value === campoConfirmarSenha.value;
    }
 
    function atualizarEstadoErro() {
        if (campoConfirmarSenha.value.length > 0 && !senhasCoincidem()) {
            mensagemErro.hidden = false;
            campoConfirmarSenha.setAttribute("aria-invalid", "true");
        } else {
            mensagemErro.hidden = true;
            campoConfirmarSenha.removeAttribute("aria-invalid");
        }
    }
 
    campoConfirmarSenha.addEventListener("input", atualizarEstadoErro);
    campoSenha.addEventListener("input", atualizarEstadoErro);
 
    form.addEventListener("submit", function (evento) {
        if (!senhasCoincidem()) {
            evento.preventDefault();
            atualizarEstadoErro();
            campoConfirmarSenha.focus();
        }
    });
 
});
document.addEventListener("DOMContentLoaded", function () {

    const prefereMovimentoReduzido = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
    const elementosReveal = document.querySelectorAll(".suporte-reveal");

    if (prefereMovimentoReduzido || !("IntersectionObserver" in window)) {
        // Sem suporte ou usuário pediu menos animação: mostra tudo de uma vez
        elementosReveal.forEach(function (elemento) {
            elemento.classList.add("suporte-reveal--visivel");
        });
    } else {
        const observador = new IntersectionObserver(
            function (entradas, observer) {
                entradas.forEach(function (entrada) {
                    if (entrada.isIntersecting) {
                        entrada.target.classList.add("suporte-reveal--visivel");
                        observer.unobserve(entrada.target);
                    }
                });
            },
            { threshold: 0.15 }
        );

        elementosReveal.forEach(function (elemento) {
            observador.observe(elemento);
        });
    }

    const campoBusca = document.getElementById("busca-suporte");
    if (!campoBusca) return;

    const cardsTopico = document.querySelectorAll(".suporte-topico");
    const itensFaq = document.querySelectorAll(".suporte-faq-item");
    const mensagemSemResultadoTopicos = document.getElementById("suporte-sem-resultados-topicos");
    const mensagemSemResultadoFaq = document.getElementById("suporte-sem-resultados-faq");

    function normalizar(texto) {
        return texto
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "")
            .toLowerCase()
            .trim();
    }

    function filtrarTopicos(termo) {
        let algumVisivel = false;
 
        cardsTopico.forEach(function (card) {
            const titulo = card.querySelector(".suporte-topico__titulo");
            const texto = card.querySelector(".suporte-topico__texto");
            const conteudo = normalizar((titulo ? titulo.textContent : "") + " " + (texto ? texto.textContent : ""));
 
            const corresponde = termo === "" || conteudo.includes(termo);
            card.hidden = !corresponde;
 
            if (corresponde) {
                algumVisivel = true;
            }
        });

        if (mensagemSemResultadoTopicos) {
            mensagemSemResultadoTopicos.hidden = algumVisivel || termo === "";
        }
    }

    function filtrarFaq(termo) {
        let algumVisivel = false;
        itensFaq.forEach(function (item) {
            const pergunta = item.querySelector("summary");
            const resposta = item.querySelector(".suporte-faq-item__resposta");
            const conteudo = normalizar((pergunta ? pergunta.textContent : "") + " " + (resposta ? resposta.textContent : ""));

            const corresponde = termo === "" || conteudo.includes(termo);
            item.hidden = !corresponde;

            if (corresponde) {
                algumVisivel = true;
            }
        });

        if (mensagemSemResultadoFaq) {
            mensagemSemResultadoFaq.hidden = algumVisivel || termo === "";
        }
    }

    campoBusca.addEventListener("input", function () {
        const termo = normalizar(campoBusca.value);
        filtrarTopicos(termo);
        filtrarFaq(termo);
    });
});

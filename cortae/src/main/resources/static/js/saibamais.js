document.addEventListener("DOMContentLoaded", function () {

    const prefereMovimentoReduzido = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
    const elementosReveal = document.querySelectorAll(".saibamais-reveal"); 

    if (prefereMovimentoReduzido || !("IntersectionObserver" in window)) {

        elementosReveal.forEach(function (elemento) {
            elemento.classList.add("saibamais-reveal--visivel");
        });

    } else {
        const observador = new IntersectionObserver(
            function (entradas, observer) {
                entradas.forEach(function (entrada) {
                    if (entrada.isIntersecting) {
                        entrada.target.classList.add("saibamais-reveal--visivel");
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

    document.querySelectorAll(".saibamais-faq-item summary").forEach(function (resumo) {
    resumo.addEventListener("click", function (evento) {
        evento.preventDefault();

        const item = resumo.parentElement;
        const corpo = item.querySelector(".saibamais-faq-item__corpo");
        const estaAberto = item.hasAttribute("open");
        const prefereMovimentoReduzido = window.matchMedia("(prefers-reduced-motion: reduce)").matches;

        if (estaAberto) {
            if (prefereMovimentoReduzido) {
                item.removeAttribute("open");
                corpo.style.height = "";
                return;
            }

            corpo.style.height = corpo.scrollHeight + "px";
            requestAnimationFrame(function () {
                corpo.style.height = "0px";
            });

            corpo.addEventListener("transitionend", function aoTerminar() {
                item.removeAttribute("open");
                corpo.style.height = "";
                corpo.removeEventListener("transitionend", aoTerminar);
            }, { once: true });
        } else {
            item.setAttribute("open", "");

            if (prefereMovimentoReduzido) {
                corpo.style.height = "auto";
                return;
            }
            corpo.style.height = "0px";
            requestAnimationFrame(function () {
                corpo.style.height = corpo.scrollHeight + "px";
            });

            corpo.addEventListener("transitionend", function aoTerminar() {
                corpo.style.height = "auto";
                corpo.removeEventListener("transitionend", aoTerminar);
            }, { once: true });
        }
    });
});

});
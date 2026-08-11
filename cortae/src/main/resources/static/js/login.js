document.getElementById('btnToggleSenha').addEventListener('click', function () {
    const campoSenha = document.getElementById('password');
    const icone = document.getElementById('iconeToggleSenha');
    const botao = document.getElementById('btnToggleSenha');

    if (campoSenha.type === 'password') {
        campoSenha.type = 'text';
        icone.classList.remove('bi-eye');
        icone.classList.add('bi-eye-slash');
        botao.setAttribute('aria-pressed', 'true');
        botao.setAttribute('aria-label', 'Ocultar senha');
    } else {
        campoSenha.type = 'password';
        icone.classList.remove('bi-eye-slash');
        icone.classList.add('bi-eye');
        botao.setAttribute('aria-pressed', 'false');
        botao.setAttribute('aria-label', 'Mostrar senha');
    }
});
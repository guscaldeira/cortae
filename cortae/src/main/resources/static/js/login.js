document.getElementById('btnToggleSenha').addEventListener('click', function () {
    const campoSenha = document.getElementById('password');
    const icone = document.getElementById('iconeToggleSenha');

    if (campoSenha.type === 'password') {
        campoSenha.type = 'text';
        icone.classList.remove('bi-eye');
        icone.classList.add('bi-eye-slash');
    } else {
        campoSenha.type = 'password';
        icone.classList.remove('bi-eye-slash');
        icone.classList.add('bi-eye');
    }
});
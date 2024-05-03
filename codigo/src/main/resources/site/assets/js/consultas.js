window.addEventListener('load', () => {
  const token = getUserSession();
  if (!token) {
    // early return
    window.location.href = `${baseUrl}/login`;
  }

  const { tipo, id:usuarioId } = decodeJwt(token);

  if (tipo !== 'Paciente') {
    // se for paciente, redireciona para a página de pacientes
    window.location.href = baseUrl + '/pacientes';
  }

  fetchDataAndPopulate(usuarioId);
}, false);

async function fetchDataAndPopulate(usuarioId) {
  // /paciente/:id/consultas -> retorna um lista de consultas
  const consultas = await requestData(`${baseURLRequest}/paciente/${usuarioId}/consultas`, 'GET');

  // FIX - especialidade
  const consultasHtml = consultas.map(consulta => `
    <li>
      <a href="./consulta?id=${consulta.id}">
        <span class="title">${consulta.titulo}</span>
        <span class="doctor">${consulta.medico}</span>
        <span class="specialty">Especialidade*</span>
        <div class="date">
          <i class="nf nf-md-calendar"></i>
          <span>${formatDate(consulta.dataHora)}</span>
        </div>
      </a>
    </li>
  `).join('');

  document.querySelector('#consultas').innerHTML = consultasHtml;
}

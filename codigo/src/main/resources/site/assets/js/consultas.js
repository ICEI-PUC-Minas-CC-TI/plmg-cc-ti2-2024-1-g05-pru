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

  let consultasHtml = '';
  for (const consulta of consultas) {
    const especialidades = await requestData(`${baseURLRequest}/medico/${consulta.medicoId}/especialidades`, 'GET');

    const especialidadesHtml = especialidades.map(esp => `<span class="specialty">${esp.titulo}</span>`).join('');

    consultasHtml += `
      <li>
        <a href="./consulta?id=${consulta.id}">
          <span class="title">${consulta.titulo}</span>
          <span class="doctor">Dr(a) ${consulta.medico}
            <span class="specialty">${especialidadesHtml}</span>
          </span>
          <div class="date">
            <i class="nf nf-md-calendar"></i>
            <span>${formatDate(consulta.dataHora)}</span>
          </div>
        </a>
      </li>
    `;
  }

  document.querySelector('#consultas').innerHTML = consultasHtml;
}

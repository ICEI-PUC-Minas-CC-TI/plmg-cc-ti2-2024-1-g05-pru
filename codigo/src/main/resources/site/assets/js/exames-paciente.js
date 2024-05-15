window.addEventListener('load', () => {
  const token = getUserSession();
  if (!token) {
    // early return
    window.location.href = `${baseUrl}/login`;
  }

  const { tipo, id:usuarioId } = decodeJwt(token);

  if (tipo !== 'Medico') {
    // se não for medico, redireciona para a página de consultas
    window.location.href = baseUrl + '/exames';
  }

  const pacienteId = getUrlId();
  if (!pacienteId) {
    window.location.href = `${baseUrl}/pacientes`;
  }

  // TODO Validar se esse paciente está vinculado ao médico

  fetchDataAndPopulate(pacienteId);
}, false);

async function fetchDataAndPopulate(usuarioId) {
  // /paciente/:id/exames -> retorna um lista de exames do paciente
  const exames = await requestData(`${baseURLRequest}/paciente/${usuarioId}/exames`, 'GET');

  const paciente = await requestData(`${baseURLRequest}/paciente/${usuarioId}`, 'GET');

  document.querySelector('h2 span').innerHTML = paciente.nome;

  let examesHtml = '';
  exames.forEach(exame => {
    const statusClass = () => {
      switch (exame.status.toLowerCase()) {
        case 'concluido':
        case 'concluído':
        case 'finalizado':
          return 'done';
        case 'pendente':
        case 'aguardando':
          return 'pending';
        case 'cancelado':
          return 'canceled';
      }
    };

    examesHtml += `
    <li>
      <a href="./exame?id=${exame.id}">
        <span class="title">${exame.titulo}</span>
        <span class="status ${statusClass()}">${exame.status}</span>
      </a>
    </li>`;
  });

  document.querySelector('#exames ul').innerHTML = examesHtml;
}

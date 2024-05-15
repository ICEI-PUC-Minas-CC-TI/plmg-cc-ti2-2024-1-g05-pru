window.addEventListener('load', () => {
  const token = getUserSession();
  if (!token) {
    // early return
    window.location.href = `${baseUrl}/login`;
  }

  const { tipo, id:usuarioId } = decodeJwt(token);

  if (tipo !== 'Paciente') {
    // se não for paciente, redireciona o médico para a página de paciente
    window.location.href = baseUrl + '/paciente';
  }

  fetchDataAndPopulate(usuarioId);
}, false);

async function fetchDataAndPopulate(usuarioId) {
  // /paciente/:id/exames -> retorna um lista de exames do paciente
  const exames = await requestData(`${baseURLRequest}/paciente/${usuarioId}/exames`, 'GET');

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

window.addEventListener('load', () => {
  const token = getUserSession();
  if (!token) {
    // early return
    window.location.href = `${baseUrl}/login`;
  }

  const { tipo, id:medicoId } = decodeJwt(token);

  if (tipo !== 'Medico') {
    // se não for medico, redireciona para a página de medicos
    window.location.href = baseUrl + '/medicos';
  }

  fetchDataAndPopulate(medicoId);
}, false);

async function fetchDataAndPopulate(usuarioId) {
  const vinculos = await requestData(`${baseURLRequest}/medico/${usuarioId}/pacientes`, 'GET');

  let pacientesHtml = '';
  vinculos.forEach(vinculo => {
    const statusClass = () => {
      switch (vinculo.status ? vinculo.status.toLowerCase() : null) {
        case 'ativo':
        case 'aprovado':
          vinculo.status = 'Ativo';
          return 'done';
        case 'pendente':
        case 'aguardando':
        case null:
          vinculo.status = 'Pendente';
          return 'pending';
        case 'cancelado':
        case 'revogado':
        case 'recusado':
          vinculo.status = 'Cancelado';
          return 'canceled';
      }
    };

    pacientesHtml += `
    <li>
      <div class="user-info">
        <span class="name">${vinculo.paciente}</span>
        <span class="status ${statusClass()}">${vinculo.status}</span>
      </div>
      <div class="actions ${vinculo.status}">
        <a href="${baseUrl}/consultas/paciente?id=${vinculo.pacienteId}"><i class="nf nf-fa-stethoscope"></i></a>

        <a href="${baseUrl}/exames/paciente?id=${vinculo.pacienteId}"><i class="nf nf-fa-file_text"></i></a>

        <button class="delete" data-id="${vinculo.id}" data-name="${vinculo.paciente}">
          <i class="nf nf-fa-trash"></i>
        </button>
      </div>
    </li>`;
  });

  document.querySelector('#pacientes ul').innerHTML = pacientesHtml;

  // Adiciona event listener a cada botão de delete
  const deleteButtons = document.querySelectorAll('.delete');
  deleteButtons.forEach(button => button.addEventListener('click', deletePaciente, false));
}

async function deletePaciente(e) {
  const vinculo = e.currentTarget.dataset.id;
  const paciente = e.currentTarget.dataset.name;

  if (!confirm(`Tem certeza que deseja deletar o paciente ${paciente}?`))
    return;

  await requestData(`${baseURLRequest}/vinculo/${vinculo}`, 'DELETE');
  window.location.reload();
}

document.querySelector('#form-add-paciente').addEventListener('submit', (e) => {
  e.preventDefault();

  const pacienteId = document.querySelector('#paciente').value;

  if (!pacienteId) {
    alert('Digite o ID do paciente!');
    return;
  }

  const data = {
    pacienteId,
    medicoId: decodeJwt(getUserSession()).id
  };

  requestData(`${baseURLRequest}/vinculo/`, 'POST', data);
  window.location.reload();
});

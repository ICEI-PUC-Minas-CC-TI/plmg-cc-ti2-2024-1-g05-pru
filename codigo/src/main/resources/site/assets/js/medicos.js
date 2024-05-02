window.addEventListener('load', () => {
  const token = getUserSession();
  if (!token) {
    // early return
    window.location.href = `${baseUrl}/login`;
  }

  const { tipo, id:pacienteId } = decodeJwt(token);

  if (tipo !== 'Paciente') {
    // se não for paciente, redireciona para a página de pacientes
    window.location.href = baseUrl + '/pacientes';
  }

  fetchDataAndPopulate(pacienteId);
}, false);

async function fetchDataAndPopulate(usuarioId) {
  const vinculos = await requestData(`${baseURLRequest}/paciente/${usuarioId}/medicos`, 'GET');

  let medicosHtml = '';
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

    medicosHtml += `
    <li>
      <div class="user-info">
        <span class="name">${vinculo.medico}</span>
        <span class="status ${statusClass()}">${vinculo.status}</span>
      </div>
      <div class="actions">`

      if (vinculo.status === 'Pendente') {
        medicosHtml += `
        <button class="aprove" data-id="${vinculo.id}" data-name="${vinculo.medico}" data-medico-id="${vinculo.medicoId}">
          <i class="nf nf-fa-check"></i>
        </button>`;
      }

      medicosHtml += `
        <button class="delete" data-id="${vinculo.id}" data-name="${vinculo.medico}">
          <i class="nf nf-fa-trash"></i>
        </button>
      </div>
    </li>`;
  });

  console.log(vinculos)

  document.querySelector('#medicos ul').innerHTML = medicosHtml;

  // Adiciona event listener a cada botão de aprovação
  const aproveButtons = document.querySelectorAll('.aprove');
  aproveButtons.forEach(button => button.addEventListener('click', aproveVinculo, false));

  // Adiciona event listener a cada botão de delete
  const deleteButtons = document.querySelectorAll('.delete');
  deleteButtons.forEach(button => button.addEventListener('click', deleteMedico, false));
}

async function aproveVinculo(e) {
  const vinculo = e.currentTarget.dataset.id;
  const medico = e.currentTarget.dataset.name;
  const medicoId = e.currentTarget.dataset.medicoId;

  if (!confirm(`Tem certeza que deseja aprovar o médico ${medico}?`))
    return;

  const data = {
    id: vinculo,
    status: 'Aprovado',
    pacienteId: decodeJwt(getUserSession()).id,
    medicoId
  };

  await requestData(`${baseURLRequest}/vinculo/${vinculo}`, 'PUT', data);

  window.location.reload();
}

async function deleteMedico(e) {
  const vinculo = e.currentTarget.dataset.id;
  const medico = e.currentTarget.dataset.name;

  if (!confirm(`Tem certeza que deseja deletar o médico ${medico}?`))
    return;

  await requestData(`${baseURLRequest}/vinculo/${vinculo}`, 'DELETE');
  window.location.reload();
}

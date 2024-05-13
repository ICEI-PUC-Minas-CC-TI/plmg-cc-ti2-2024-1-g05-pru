window.addEventListener('load', () => {
  const token = getUserSession();
  if (!token) {
    // early return
    window.location.href = `${baseUrl}/login`;
  }

  const crmDoctor = document.querySelector('#crm');
  const patientInfo = document.querySelector('.patient-info');

  const { tipo } = decodeJwt(token);

  if (tipo === 'Paciente') {
    crmDoctor.style.display = 'none';
    patientInfo.style.display = 'flex';
  } else if (tipo === 'Medico') {
    patientInfo.style.display = 'none';
    crmDoctor.style.display = 'block';
  }

  fetchDataAndPopulate(token);
});

async function fetchDataAndPopulate(token) {
  const { id, tipo } = decodeJwt(token);

  if(tipo === 'Paciente'){
    const image = document.querySelector('.user-image');
    const name = document.querySelector('.user-name');
    const lastConsultas = document.querySelector('.consultas-recentes ul');
    const lastExames = document.querySelector('.exames-recentes ul');

    // Nome e foto
    const { nome, urlFoto } = await requestData(`${baseURLRequest}/paciente/${id}`, 'GET');
    image.innerHTML = urlFoto ? `<img src="${urlFoto}" alt="${nome}">` : '<i class="nf nf-fa-user_circle"></i>';
    name.innerHTML = nome;

    // Consultas
    const consultas = await requestData(`${baseURLRequest}/paciente/${id}/consultas/5`, 'GET');
    if (consultas.length === 0) {
      lastConsultas.innerHTML = '<li>Nenhuma consulta encontrada</li>';
    } else {
      lastConsultas.innerHTML = consultas.map(consulta => {
        return `
        <li>
          <a href="${baseUrl}/consultas/consulta?id=${consulta.id}">
            <span class="date">${formatDate(consulta.dataHora)}</span>
            <span class="title">${consulta.titulo.substring(0, 30)}</span>
          </a>
        </li>`;
      }).join('');
    }

    // Exames
    const exames = await requestData(`${baseURLRequest}/paciente/${id}/exames/5`, 'GET');
    if (exames.length === 0) {
      lastExames.innerHTML = '<li>Nenhum exame encontrado</li>';
    } else {
      let examesHtml = '';
      exames.forEach(exame => {
        exame.data = exame.data ? new Date(exame.data) : null;

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
          <a href="${baseUrl}/exames/exame?id=${exame.id}">
            <span class="date">${exame.data ? formatDate(exame.data) : ''}</span>
            <span class="title">${exame.titulo.substring(0, 20)}</span>
            <span class="status ${statusClass()}">${exame.status}</span>
          </a>
        </li>`;
      });
      lastExames.innerHTML = examesHtml;
    }

  } else if (tipo === 'Medico') {
    const { nome, urlFoto, crm } = await requestData(`${baseURLRequest}/medico/${id}`, 'GET');

    if (urlFoto) {
      document.querySelector('.user-image').innerHTML =
        `<img src="${urlFoto}" alt="${nome}">`;
    } else {
      document.querySelector('.user-image').innerHTML =
      '<i class="nf nf-fa-user_circle"></i>';
    }

    document.querySelector('.user-name').innerHTML = nome;
    document.querySelector('#crm').innerHTML = crm;
  }
}

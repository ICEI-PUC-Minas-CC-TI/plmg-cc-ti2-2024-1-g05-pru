window.addEventListener('load', showTypeSection);

async function fetchDataAndPopulate(token) {
  const { id, tipo } = decodeJwt(token);

  if(tipo === 'Paciente'){
    const { nome, urlFoto } = await requestData(`${baseURLRequest}/paciente/${id}`, 'GET');

    if (urlFoto) {
      document.querySelector('.user-image').innerHTML =
        `<img src="${urlFoto}" alt="${nome}">`;
    } else {
      document.querySelector('.user-image').innerHTML =
      '<i class="nf nf-fa-user_circle"></i>';
    }

    document.querySelector('.user-name').innerHTML = nome;

    // TODO - Adicionar lista de exames/consultas/medicamentos
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

    // TODO - Lista de pacientes
  }
}

async function showTypeSection() {
  const doctorInfo = document.querySelector('.doctor-info');
  const crmDoctor = document.querySelector('#crm');
  const patientInfo = document.querySelector('.patient-info');

  const {token, tipo} = getUserSession() || {};

  if (!tipo) {
    window.location.href = `${baseUrl}/login`;
  } else if (tipo === 'Paciente') {
    doctorInfo.style.display = 'none';
    crmDoctor.style.display = 'none';
    patientInfo.style.display = 'flex';
  } else if (tipo === 'Medico') {
    patientInfo.style.display = 'none';
    doctorInfo.style.display = 'flex';
    crmDoctor.style.display = 'block';
  }

  await fetchDataAndPopulate(token);
}

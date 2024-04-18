window.addEventListener('load', showTypeSection);

//TODO - Passar somente token ?
async function fetchDataAndPopulate(token, role) {
  //TODO - Checar tratamento de erro
  const { id } = await decodeJwt(token);

  if(role === 'paciente'){
    const { nome, foto } = await requestData(`${baseURLRequest}/paciente/${id}`, 'GET');
    document.querySelector('#user-name').value = nome;
    document.querySelector('#user-image').src = foto;
    // TODO - Adicionar lista de exames/consultas/medicamentos
  } else {
    const { nome, foto, crm } = await requestData(`${baseURLRequest}/medico/${id}`, 'GET');
    document.querySelector('#user-name').value = nome;
    document.querySelector('#user-image').src = foto;
    document.querySelector('#crm').value = crm;
    // TODO - Exibiremos as especialidades do medico em seu proprio perfil ?
    // const especialidadeContainer = document.querySelector('#especialidade');
    // especialidade.forEach(item => {
    //   const esp = document.createElement('span');
    //   esp.value = item;
    //   esp.textContent = item;
    //   especialidadeContainer.appendChild(esp);
    // });
    // TODO - Lista de pacientes
  }
}



async function showTypeSection() {
  const doctorInfo = document.querySelector('.doctor-info');
  const crmDoctor = document.querySelector('#crm');
  const patientInfo = document.querySelector('.patient-info');

  const {token, role} = getUserSession() || {};

  // Teoricamente o tratamento de existencia de role ja foi feito no getUserSession
  // if (role) {
    if (role === 'paciente') {
      doctorInfo.style.display = 'none';
      crmDoctor.style.display = 'none';
      patientInfo.style.display = 'flex';
    } else {
      patientInfo.style.display = 'none';
      doctorInfo.style.display = 'flex';
      crmDoctor.style.display = 'block';
    }
  // } else {
  //   window.location.href = `${baseUrl}/login`;
  // }
  await fetchDataAndPopulate(token, role);
  console.log("passei");
}
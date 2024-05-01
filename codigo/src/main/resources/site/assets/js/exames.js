window.addEventListener('load', () => {
  const token = getUserSession();
  if (!token) {
    // early return
    window.location.href = `${baseUrl}/login`;
  }

  const { tipo } = decodeJwt(token);

  const exameId = getUrlId();
  if (!exameId) {
    // se for médico, redireciona para a página de pacientes e se for paciente, redireciona para a página de exmaes (caso não tenha id na URL)
    window.location.href = baseUrl +
      (tipo === 'Medico' ?  + '/pacientes' : '/exames');
  }

  // TODO - Validar se o médico ou o paciente tem acesso
  fetchDataAndPopulate(exameId);
}, false);

async function fetchDataAndPopulate(exameId) {
  try {
    const exame = await requestData(`${baseURLRequest}/exame/${exameId}`, 'GET');
    const { titulo:tituloConsulta, medico:nomeMedico } = await requestData(`${baseURLRequest}/consulta/${exame.consultaId}`, 'GET');

    // Header
    document.querySelector('.info-header h1').textContent = exame.titulo;
    document.querySelector('.info-header .requested a').textContent = tituloConsulta;
    document.querySelector('.info-header .requested a').href = `${baseUrl}/consultas/consulta?id=${exame.consultaId}`;

    document.querySelector('.info-header .doctor .name').textContent = `Dr(a). ${nomeMedico}`;

    document.querySelector('.info-header .date span').textContent = formatDate(formatDate(exame.data));

    // Exame
    const downloadButton = document.querySelector('section.result .download');
    const uploadButton = document.querySelector('section.result .upload');

    if (exame.urlArquivo) {
      uploadButton.style.display = 'none';
      downloadButton.style.display = 'block';
      downloadButton.addEventListener('click', () => {
        window.open(exame.urlArquivo, '_blank');
      });
    } else {
      downloadButton.style.display = 'none';
      document.querySelector('section.result .upload').style.display = 'block';
      document.querySelector('section.result .upload').addEventListener('click', () => console.log('upload') );
    }
  } catch (error) {
    window.location.href = baseUrl + '/404';
  }
}

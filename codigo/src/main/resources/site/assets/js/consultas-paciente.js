window.addEventListener('load', () => {
  const token = getUserSession();
  if (!token) {
    // early return
    window.location.href = `${baseUrl}/login`;
  }

  const { tipo, id:usuarioId } = decodeJwt(token);

  if (tipo !== 'Medico') {
    // se não for medico, redireciona para a página de consultas
    window.location.href = baseUrl + '/consultas';
  }

  const pacienteId = getUrlId();
  if (!pacienteId) {
    window.location.href = `${baseUrl}/pacientes`;
  }

  // TODO Validar se esse paciente está vinculado ao médico

  fetchDataAndPopulate(pacienteId);
}, false);

async function fetchDataAndPopulate(usuarioId) {
  // /paciente/:id/consultas -> retorna um lista de consultas do paciente
  const consultas = await requestData(`${baseURLRequest}/paciente/${usuarioId}/consultas`, 'GET');

  const paciente = await requestData(`${baseURLRequest}/paciente/${usuarioId}`, 'GET');

  document.querySelector('h2 span.name').innerHTML = paciente.nome;

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

// FORM ADD CONSULTA
// Event Listeners para adicionar campos de exames e medicamentos
document.getElementById('addExame').addEventListener('click', () => {
  document.getElementById('exames').appendChild(createChildField('exame'));
}, false);

document.getElementById('addMedicamento').addEventListener('click', () => {
  document.getElementById('medicamentos').appendChild(createChildField('medicamento'));
}, false);

// Função para criar campos de exames e medicamentos dinamicamente
function createChildField(childName, id = generateUUID(8)) {
  const childField = document.createElement('section');
  childField.className = `input-field ${childName}`;

  childField.innerHTML = `<input type="text" name="${childName}-${id}" placeholder="Novo ${childName}">`
  childField.innerHTML += (childName === 'medicamento') ? `<input type="number" name="${childName}-dias-${id}" placeholder="Dias" min="0">` : '';
  childField.innerHTML += ` <button class="remove"><i class="nf nf-fa-trash"></i></button>`;

  childField.querySelector('.remove').addEventListener('click', () => {
    childField.remove();
  }, false);

  return childField;
}

// Código base para ler os campos do formulário
document.querySelector('#form-add-consulta').addEventListener('submit', async (e) => {
  e.preventDefault();

  const formData = new FormData(e.target);

  data = {
    titulo: formData.get('titulo'),
    diagnostico: formData.get('diagnostico'),
    dataHora: getTimeStamp(),
    urlAnexo: " ",
    medicoId: decodeJwt(getUserSession()).id,
    pacienteId: getUrlId(),
  }

  const { id } = await requestData(`${baseURLRequest}/consulta/`, 'POST', data);

  let exames = [];
  let medicamentos = [];
  for (let [name, value] of formData) {
    if (name.includes('exame')) {
      requestData(`${baseURLRequest}/exame/`, 'POST', {
        titulo: value,
        data: null,
        urlArquivo: null,
        status: "Pendente",
        consultaId: id,
      });
    }
    else if (name.includes('medicamento') && name.includes('dias') === false) {
      requestData(`${baseURLRequest}/medicamento/`, 'POST', {
        nome: value,
        dias: formData.get(`${name}-dias-${name.split('-')[1]}`),
        consultaId: id
      });
    }
  }
});

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

  document.querySelector('h2 span.name').innerHTML = consultas[0].paciente;

  // FIX - Especialidade
  const consultasHtml = consultas.map(consulta => `
    <li>
      <a href="./consulta?id=${consulta.id}">
        <span class="title">${consulta.titulo}</span>
        <span class="doctor">${consulta.medico}</span>
        <span class="specialty">Cardiologista</span>
        <div class="date">
          <i class="nf nf-md-calendar"></i>
          <span>${formatDate(consulta.dataHora)}</span>
        </div>
      </a>
    </li>
  `).join('');

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
document.querySelector('#form-add-consulta').addEventListener('submit', (event) => {
  event.preventDefault();

  const formData = new FormData(event.target);

  for (let [name, value] of formData) {
    console.log(`${name} = ${value}`);
  }
});

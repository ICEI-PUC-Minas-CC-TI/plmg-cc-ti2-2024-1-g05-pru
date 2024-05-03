window.addEventListener('load', () => {
  const token = getUserSession();
  if (!token) {
    // early return
    window.location.href = `${baseUrl}/login`;
  }

  const { tipo } = decodeJwt(token);

  const consultaId = getUrlId();
  if (!consultaId) {
    // se for médico, redireciona para a página de pacientes e se for paciente, redireciona para a página de consultas (caso não tenha id na URL)
    window.location.href = baseUrl +
      (tipo === 'Medico' ?  + '/pacientes' : '/consultas');
  }

  // TODO - Validar se o médico ou o paciente tem acesso

  if (tipo === 'Medico') {
    // se for médico, exibe o botão de editar
    document.querySelector('.info-header .edit').style.display = 'inline-block';
  }

  fetchDataAndPopulate(consultaId);
}, false);


async function fetchDataAndPopulate(consultaId) {
  try {
    const consulta = await requestData(`${baseURLRequest}/consulta/${consultaId}`, 'GET');

    // Header
    document.querySelector('.info-header h1').textContent = consulta.titulo;
    document.querySelector('.info-header .doctor a').textContent = consulta.medico;
    document.querySelector('.info-header .doctor span').textContent = 'Especialidade*';

    document.querySelector('.info-header .date span').textContent = formatDate(consulta.dataHora);

    document.querySelector('.diagnostic p').textContent = consulta.diagnostico;

    const exames = await requestData(`${baseURLRequest}/consulta/${consultaId}/exames`, 'GET');

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
        <a href="${baseUrl}/exames/exame?id=${exame.id}">
          <span class="id">#${exame.id}</span>
          -
          <span class="title">${exame.titulo}</span>
          <span class="status ${statusClass()}">${exame.status}</span>
        </a>
      </li>`;
    });

    document.querySelector('.requested-exams ul').innerHTML = examesHtml;

    const medicamentos = await requestData(`${baseURLRequest}/consulta/${consultaId}/medicamentos`, 'GET');


    let medicamentoHtml = '';
    medicamentos.forEach(medicamento => {
      medicamentoHtml += `
      <li>
        <span>${medicamento.nome}</span>
        ${medicamento.dias === 0 ? '' : ' - <span>' + medicamento.dias + ' dias</span>'}
      </li>`;
    });

    console.log(medicamentoHtml);

    document.querySelector('.remedy ul').innerHTML = medicamentoHtml;

  } catch (error) {
    window.location.href = baseUrl + '/404';
  }
}



// FORM ADD/EDIT CONSULTA
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
document.querySelector('#form-edit-consulta').addEventListener('submit', (event) => {
  event.preventDefault();

  const formData = new FormData(event.target);

  for (let [name, value] of formData) {
    console.log(`${name} = ${value}`);
  }
});

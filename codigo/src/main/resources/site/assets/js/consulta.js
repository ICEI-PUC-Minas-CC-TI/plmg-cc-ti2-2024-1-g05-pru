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

    const especialidades = await requestData(`${baseURLRequest}/medico/${consulta.medicoId}/especialidades`, 'GET');

    // Header
    document.querySelector('.info-header h1').textContent = consulta.titulo;
    document.querySelector('.info-header .doctor .name').textContent = `Dr(a) ${consulta.medico}`;
    document.querySelector('.info-header .doctor .specialty').innerHTML = especialidades.map(esp => {
      return `<span class="specialty">${esp.titulo}</span>`;
    }).join('');

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

    document.querySelector('.remedy ul').innerHTML = medicamentoHtml;
    populateForm(consultaId);
  } catch (error) {
    window.location.href = baseUrl + '/404';
  }
}



// FORM EDIT CONSULTA
// Event Listeners para adicionar campos de exames e medicamentos
document.getElementById('addExame').addEventListener('click', () => {
  document.getElementById('exames').appendChild(createExameField());
}, false);

document.getElementById('addMedicamento').addEventListener('click', () => {
  document.getElementById('medicamentos').appendChild(createMedicamentoField());
}, false);

// cria campo de exame
function createExameField(id = generateUUID(8), value = '') {
  const childField = document.createElement('section');
  childField.className = `input-field exame`;

  childField.innerHTML = `
    <input type="text" id="${id}" ${value !== '' ? '' : 'new' } name="exame-${id}" placeholder="Novo exame" value="${value}">
    <button class="remove"><i class="nf nf-fa-trash"></i></button>
  `;

  childField.querySelector('.remove').addEventListener('click', (e) => {
    e.preventDefault();

    if (childField.querySelector('input').hasAttribute('new')) {
      childField.remove();
      return;
    } else {
      if (confirm('Deseja realmente remover este exame?')) {
        requestData(`${baseURLRequest}/exame/${id}`, 'DELETE');
        childField.remove();
      }
    }
  }, false);

  return childField;
}

// cria campo de medicamento
function createMedicamentoField(id = generateUUID(8), value = '', days = null) {
  const childField = document.createElement('section');
  childField.className = `input-field medicamento`;

  childField.innerHTML = `
    <input type="text" id="${id}" ${value !== '' ? '' : 'new' } name="medicamento-${id}" placeholder="Novo medicamento" value="${value}">
    <input type="number" id="dias-${id}" ${days ? '' : 'new' } name="medicamento-dias-${id}" placeholder="Dias" value="${days}">
    <button class="remove"><i class="nf nf-fa-trash"></i></button>
  `;

  childField.querySelector('.remove').addEventListener('click', (e) => {
    e.preventDefault();

    if (childField.querySelector('input').hasAttribute('new')) {
      childField.remove();
      return;
    } else {
      if (confirm('Deseja realmente remover este medicamento?')) {
        requestData(`${baseURLRequest}/medicamento/${id}`, 'DELETE');
        childField.remove();
      }
    }
  }, false);

  return childField;
}

//
async function populateForm(consultaId) {
  const consulta = await requestData(`${baseURLRequest}/consulta/${consultaId}`, 'GET');
  const exames = await requestData(`${baseURLRequest}/consulta/${consultaId}/exames`, 'GET');
  const medicamentos = await requestData(`${baseURLRequest}/consulta/${consultaId}/medicamentos`, 'GET');

  document.getElementById('titulo').value = consulta.titulo;
  document.getElementById('diagnostico').value = consulta.diagnostico;

  // exames
  exames.forEach(exame => {
    document.getElementById('exames').appendChild(createExameField(exame.id, exame.titulo));
  });

  // medicamentos
  medicamentos.forEach(medicamento => {
    document.getElementById('medicamentos').appendChild(createMedicamentoField(medicamento.id, medicamento.nome, medicamento.dias));
  });


  // Código para ler os campos do formulário e enviar a requisição
  document.querySelector('#form-edit-consulta').addEventListener('submit', async (e) => {
    e.preventDefault();

    const data = {
      id: consultaId,
      titulo: document.getElementById('titulo').value,
      diagnostico: document.getElementById('diagnostico').value,
      dataHora: consulta.dataHora,
      urlAnexo: consulta.urlAnexo,
      medicoId: consulta.medicoId,
      pacienteId: consulta.pacienteId
    };

    await requestData(`${baseURLRequest}/consulta/${consultaId}`, 'PUT', data);

    // exames
    const exames = document.querySelectorAll('.exame input');
    exames.forEach(async (exame) => {
      if (exame.value) {
        const exameId = exame.id;
        const titulo = exame.value;

        // early return
        if (titulo === '' || titulo === null) {
          alert('O campo de exame não pode estar vazio.');
          return;
        }

        const data = {
          titulo: exame.value,
          data: null,
          urlArquivo: null,
          status: 'Pendente',
          consultaId
        };

        console.log({ ...data, id: exameId });

        if (exame.hasAttribute('new')) {
          const resp = await requestData(`${baseURLRequest}/exame/`, 'POST', data);
          console.log(resp)
        } else {
          await requestData(`${baseURLRequest}/exame/${exameId}`, 'PUT', { ...data, id: exameId });
        }
      }
    });

    // medicamentos
    const medicamentos = document.querySelectorAll('.medicamento input[type="text"]');
    medicamentos.forEach(async (medicamento) => {
      if (medicamento.value) {
        const medicamentoId = medicamento.id;
        const nome = medicamento.value;
        const dias = document.getElementById(`dias-${medicamentoId}`).value || 0;

        // early return
        if (nome === '' || nome === null) {
          alert('O campo de medicamento não pode estar vazio.');
          return;
        }

        const data = {
          nome,
          dias,
          consultaId
        };

        if (medicamento.hasAttribute('new')) {
          await requestData(`${baseURLRequest}/medicamento/`, 'POST', data);
        } else {
          await requestData(`${baseURLRequest}/medicamento/${medicamentoId}`, 'PUT', { ...data, id: medicamentoId });
        }
      }
    });

    window.location.reload();
  });
}

window.addEventListener('load', () => {
  const editButton = document.querySelector('.info-header .edit');

  const userType = getUserType();
  const id = getIDFromURL();

  if (!id) {
    // se for médico, redireciona para a página de pacientes e se for paciente, redireciona para a página de consultas (caso não tenha id na URL)
    window.location.href = baseUrl + (userType === 'paciente' ? '/consultas' : '/pacientes');
  } else {
    // se for médico, exibe o botão de editar
    editButton.style.display = userType === 'medico' ? 'inline-block' : 'none';
  }
}, false);


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

  if (childName === 'medicamento') {
    childField.innerHTML = `
      <input type="text" name="${childName}-${id}" placeholder="Novo ${childName}">
      <input type="number" name="${childName}-dias-${id}" placeholder="Dias" min="0">
      <button class="remove"><i class="nf nf-fa-trash"></i></button>
    `;
  } else {
    childField.innerHTML = `
      <input type="text" name="${childName}-${id}" placeholder="Novo ${childName}">
      <button class="remove"><i class="nf nf-fa-trash"></i></button>
    `;
  }

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

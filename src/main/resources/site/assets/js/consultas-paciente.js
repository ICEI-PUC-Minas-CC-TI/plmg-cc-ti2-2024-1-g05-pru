// Verifica se o ID do paciente está presente na URL (/consultas/paciente?id=)
window.addEventListener('load', () => {
  if (!getIDFromURL()) {
    window.location.href = `${baseUrl}/pacientes`;
  }
}, false);


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

  childField.innerHTML = `
    <input type="text" id="${childName}-${id}" name="${childName}-${id}" placeholder="Novo ${childName}">
    <button class="remove"><i class="nf nf-fa-trash"></i></button>
  `;

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

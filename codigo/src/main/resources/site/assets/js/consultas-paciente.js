// Verifica se o ID do paciente está presente na URL (/consultas/paciente?id=)
window.addEventListener('load', () => {
  if (!getIDFromURL()) {
    window.location.href = `${baseUrl}/pacientes`;
  }
}, false);


// FORM ADD CONSULTA
// Código base para ler os campos do formulário
document.querySelector('#form-add-consulta').addEventListener('submit', (event) => {
  event.preventDefault();

  const formData = new FormData(event.target);

  for (let [name, value] of formData) {
    console.log(`${name} = ${value}`);
  }
});

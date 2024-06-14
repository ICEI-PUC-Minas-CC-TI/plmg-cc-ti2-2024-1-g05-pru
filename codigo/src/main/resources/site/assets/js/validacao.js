document.querySelector('form label').addEventListener('click', () => {
  document.querySelector('input').click();
});

const form = document.querySelector('form');

form.addEventListener('submit', async (event) => {
  event.preventDefault();

  const formData = new FormData(form);
  formData.append('file', form.querySelector('input[type="file"]').files[0]);
  formData.append('upload_preset', 'gzq11i84');

  const response = await fetch('https://api.cloudinary.com/v1_1/andrels-net/image/upload', {
    method: 'POST',
    body: formData
  });

  document.querySelector('#load').style.display = 'block';

  const data = await response.json();
  const { id } = decodeJwt(getUserSession());
  await fetch(`http://localhost:6789/usuario/${id}/foto`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ urlFoto: data.url })
  });

  const validado = await fetch(`http://localhost:6789/medico/${id}/validar`, {
    method: 'GET'
  });

  if (validado.status === 200) {
    alert('Você foi validado com sucesso! Faça login novamente para atualizar suas informações.');

    logout();
  }
});

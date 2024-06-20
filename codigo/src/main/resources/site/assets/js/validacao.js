document.querySelector('input[type=file]').addEventListener('change', function() {
  var fileName = this.files[0].name;
  document.querySelector('form label').textContent = fileName;
});

document.querySelector('form label').addEventListener('click', () => {
  document.querySelector('input[type=file]').click();
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
  await fetch(`${baseURLRequest}/usuario/${id}/foto`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ urlFoto: data.url })
  });

  const validado = await fetch(`${baseURLRequest}/medico/${id}/validar`, {
    method: 'GET'
  });

  if (validado.status === 200) {
    alert('Você foi validado com sucesso! Faça login novamente para atualizar suas informações.');

    logout();
  } else {
    alert('Houve um erro ao validar seu cadastro, tente novamente mais tarde.');

    location.reload();
  }
});

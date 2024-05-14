window.addEventListener('load', () => {
  const token = getUserSession();
  if (!token) {
    // early return
    window.location.href = `${baseUrl}/login`;
  }

  const { tipo } = decodeJwt(token);

  const doctorInfo = document.querySelector('.doctor-info');

  if (tipo !== 'Medico') {
    doctorInfo.style.display = 'none';
  }

  fetchDataAndPopulate(token);
});

async function fetchDataAndPopulate(token) {
  const { id, tipo } = decodeJwt(token);

  const name = document.querySelector('#nome');
  const imageUrl = document.querySelector('#foto');
  const emailAddress = document.querySelector('#email');
  const phone = document.querySelector('#telefone');
  const cepAddress = document.querySelector('#cep');

  if (tipo === 'Paciente') {
    const { nome, email, telefone, cep, urlFoto } = await requestData(`${baseURLRequest}/paciente/${id}`, 'GET');

    name.value = nome;
    imageUrl.value = urlFoto;
    emailAddress.value = email;
    phone.value = telefone;
    cepAddress.value = cep;

  } else if (tipo === 'Medico') {
    const { nome, email, telefone, cep, urlFoto } = await requestData(`${baseURLRequest}/medico/${id}`, 'GET');

    const especialidades = await requestData(`${baseURLRequest}/medico/${id}/especialidades`, 'GET');

    name.value = nome;
    imageUrl.value = urlFoto;
    emailAddress.value = email;
    phone.value = telefone;
    cepAddress.value = cep;

    const specialty = document.querySelector('#especialidades');

    especialidades.forEach((esp) => {
      specialty.appendChild(addSpecialty(esp.id, esp.titulo));
    });

    document.getElementById('addEspecialidade').addEventListener('click', () => {
      // verifica se já existe duas especialidades
      if (specialty.children.length <= 2) {
        specialty.appendChild(addSpecialty());
      }
    }, false);
  }
}

function addSpecialty(id = generateUUID(8), value = '') {
  const childField = document.createElement('section');

  childField.className = `input-field especialidade`;
  childField.innerHTML = `
    <input type="text" ${value !== '' ? '' : 'new' } name="especialidade-${id}" id="${id}" placeholder="Nova especialidade" value="${value}">
    <button class="remove"><i class="nf nf-fa-trash"></i></button>
  `;

  childField.querySelector('.remove').addEventListener('click', (e) => {
    e.preventDefault();

    if (childField.querySelector('input').hasAttribute('new')) {
      childField.remove();
      return;
    } else {
      if (confirm('Deseja realmente remover esta especialidade?')) {
        requestData(`${baseURLRequest}/especialidade/${id}`, 'DELETE');

        childField.remove();
      }
    }
  }, false);

  return childField;
}

document.querySelector('#form-edit-perfil').addEventListener('submit', async (e) => {
  e.preventDefault();

  const token = getUserSession();
  const { id:userId, tipo } = decodeJwt(token);

  const name = document.querySelector('#nome').value;
  const imageUrl = document.querySelector('#foto').value;
  const emailAddress = document.querySelector('#email').value;
  const phone = document.querySelector('#telefone').value;
  const cepAddress = document.querySelector('#cep').value;

  if (tipo === 'Paciente') {
    const userData = {
      id: userId,
      nome: name,
      email: emailAddress,
      telefone: phone,
      urlFoto: imageUrl,
      cep: cepAddress
    };

    await requestData(`${baseURLRequest}/paciente/${userId}`, 'PUT', userData);

  } else if (tipo === 'Medico') {
    const userData = {
      id: userId,
      nome: name,
      email: emailAddress,
      telefone: phone,
      urlFoto: imageUrl,
      cep: cepAddress
    };

    await requestData(`${baseURLRequest}/medico/${userId}`, 'PUT', userData);

    const especialidades = document.querySelectorAll('.especialidade input');

    especialidades.forEach(async (esp) => {
      const espId = esp.id;
      const titulo = esp.value;

      // early return
      if (titulo === '' || titulo === null) {
        alert('O campo especialidade não pode ser vazio');
        return;
      }

      const data = {
        titulo,
        medicoId: userId
      };

      // se for uma especialidade nova faz um POST e se for uma especialidade existente faz um PUT
      if (esp.hasAttribute('new'))
        await requestData(`${baseURLRequest}/especialidade/`, 'POST', data);
      else
        await requestData(`${baseURLRequest}/especialidade/${espId}`, 'PUT', { ...data, id: espId });
    });
  }

  window.location.href = `${baseUrl}/perfil`;
});

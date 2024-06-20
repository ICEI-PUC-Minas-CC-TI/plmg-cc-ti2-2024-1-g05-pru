document.querySelector('.radio-input').addEventListener('click', () => {
  const infoPessoal = document.querySelector('.personal-info');
  const infoMedico = document.querySelector('.doctor-info');
  const tipo = document.querySelector('.radio-input [name="account-type"]:checked')?.value;

  if (!infoMedico || !infoPessoal || !tipo) return;

  const ehMedico = tipo === 'P' ? 'none' : 'block';

  infoPessoal.style.display = 'block';
  infoMedico.style.display = ehMedico;
});

document.querySelector('.register').addEventListener('submit', (event) => {
  event.preventDefault();

  const tipo = document.querySelector('.radio-input [name="account-type"]:checked')?.value;

  if (!tipo) return;

  if (tipo === 'P') {
    registerPatient();
  } else if (tipo === 'M') {
    registerDoctor();
  } else {
    alert('É necessário selecionar um tipo de conta!');
  }
});

/*
  Registra Medico
*/
async function registerDoctor() {
  const nome = document.querySelector('#nome').value;
  const email = document.querySelector('#email').value;
  const emailConfirm = document.querySelector('#email-confirme').value;
  const senha = document.querySelector('#senha').value;
  const senhaConfirm = document.querySelector('#senha-confirme').value;
  const nascimento = document.querySelector('#nascimento').value;
  const cpf = document.querySelector('#cpf').value;
  const sexo = document.querySelector('#sexo').value;
  const telefone = document.querySelector('#telefone').value;
  const cep = document.querySelector('#cep').value;
  const crm = document.querySelector('#crm').value;

  if (email !== emailConfirm) {
    alert('Os emails não coincidem');
    return;
  }

  if (senha !== senhaConfirm) {
    alert('As senhas não coincidem');
    return;
  }

  const data = {
    nome,
    cpf,
    email,
    senha,
    telefone,
    sexo,
    nascimento,
    urlFoto: "",
    cep,
    crm,
    validado: false,
  };

  const res = await requestData('http://localhost:6789/medico/', 'POST', data);

  if (res.status === 200) {
    window.location.href = `${baseUrl}/login`;
  } else {
    alert(res);
  }
}

/*
  Registra Medico
*/
async function registerPatient() {
  const nome = document.querySelector('#nome').value;
  const email = document.querySelector('#email').value;
  const emailConfirm = document.querySelector('#email-confirme').value;
  const senha = document.querySelector('#senha').value;
  const senhaConfirm = document.querySelector('#senha-confirme').value;
  const nascimento = document.querySelector('#nascimento').value;
  const cpf = document.querySelector('#cpf').value;
  const sexo = document.querySelector('#sexo').value;
  const telefone = document.querySelector('#telefone').value;
  const cep = document.querySelector('#cep').value;

  if (email !== emailConfirm) {
    alert('Os emails não coincidem');
    return;
  }

  if (senha !== senhaConfirm) {
    alert('As senhas não coincidem');
    return;
  }

  const data = {
    nome,
    cpf,
    email,
    senha,
    telefone,
    sexo,
    nascimento,
    urlFoto: "",
    cep,
  };

  const res = await requestData('http://localhost:6789/paciente/', 'POST', data);

  if (res.status === 200) {
    window.location.href = `${baseUrl}/login`;
  } else {
    alert(res);
  }
}

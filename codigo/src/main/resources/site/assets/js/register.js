document.querySelector('.radio-input').addEventListener('click', typeSelect);

function typeSelect() {
  const doctorInfo = document.querySelector('.doctor-info');
  const personalInfo = document.querySelector('.personal-info');
  const option = document.querySelector('.radio-input [name="account-type"]:checked')?.value;

  if (!doctorInfo || !personalInfo || !option) return;

  const doctorDisplayValue = option === 'paciente' ? 'none' : 'block';

  personalInfo.style.display = 'block';
  doctorInfo.style.display = doctorDisplayValue;
}

document.querySelector('.register').addEventListener('submit', (event) => {
  event.preventDefault();

  const option = document.querySelector('.radio-input [name="account-type"]:checked')?.value;

  if (!option) return;

  if (option === 'paciente') {
    //registerPatient();
    console.log('registerPatient');
  } else if (option === 'medico'){
    registerDoctor();
    console.log('registerDoctor');
  }
});

/*
  Registra Medico
*/
function registerDoctor() {
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
    urlFoto: 'https://randomuser.me/api/portraits/men/23.jpg',
    cep,
    crm
  };

  requestData('http://localhost:6789/medico/', 'POST', data);
}

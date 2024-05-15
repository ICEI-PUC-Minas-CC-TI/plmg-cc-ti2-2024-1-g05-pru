document.querySelector('.login').addEventListener('submit', login);

/*
  Função que valida o login, e caso estaja correto adiciona os dados ao localstorage e retorna true
*/
async function loginValidations(email, senha, tipo) {
  const data = { email, senha, tipo };

  try {
    const response = await requestData(`${baseURLRequest}/login`, 'POST', data);

    console.log(response);


    if (!response.token) {
      alert('Falha na autenticação. Tente novamente.');
      return false;
    }

    sessionStorage.setItem('token', response.token);

    return true;
  } catch (error) {
    console.error('Erro ao tentar fazer login:', error);
    alert('Erro ao tentar fazer login. Por favor, tente novamente.');
    return false;
  }
}

/*
  Função que realiza o login, e redireciona o usuario para a pagina correta
*/
async function login(event) {
  event.preventDefault();

  const email = document.querySelector('#email').value;
  const senha = document.querySelector('#senha').value;
  const tipo = document.querySelector('.radio-input [name="account-type"]:checked')?.value;

  if (tipo == null) {
    alert('É necessário selecionar um tipo de conta!');
    return;
  }

  if (await loginValidations(email, senha, tipo)) {
    window.location.href = `${baseUrl}/perfil`;
  } else {
    alert('Erro ao efetuar o login, tente novamente.');
  }
}

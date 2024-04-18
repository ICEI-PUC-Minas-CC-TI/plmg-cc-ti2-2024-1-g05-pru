// TODO Descobrir pq nao esta funcinando o import
//import { requestLoginWithRole } from './services/requests.js';
document.querySelector('.login').addEventListener('submit', login);
// import jwt from 'jsonwebtoken';


// Valores padroes que serao usados no token, TODO no back
/*
  const secretKey = 'CHAVE_SECRETA_PRU'; 
  const jwtConfig = {
    "expiresIn": "4h",
    "alg": "HS256",
    "typ": "JWT"
  };
*/

/*
  Função que valida o login, e caso estaja correto adiciona os dados ao localstorage e retorna true
*/
async function loginValidations(email, password) {
  const data = { email, password };
  try {
    //TODO CHECAR SE ESTA FUNCIONANDO, ALTERAR URL
    const { token, role } = await requestData(`${baseURLRequest}/login`, 'POST', data);
    sessionStorage.setItem('token', token);
    sessionStorage.setItem('role', role);

    return true;

  } catch (error) {
    alert('Email ou senha inválidos');
  }
}

/*
  Função que realiza o login, e redireciona o usuario para a pagina correta
*/
async function login(event) {
  event.preventDefault();

  const email = document.querySelector('#email').value;
  const password = document.querySelector('#senha').value;

  if (await loginValidations(email, password)) {
    window.location.href = `${baseUrl}/perfil`;
  } else {
    alert('Erro ao efetuar o login, tente novamente.');
  }
}

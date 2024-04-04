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
  Função que chamara o back para validar o login, este retornara
  seu jwt e a role do usuario caso esteja correto
*/
async function requestLoginWithRole(url, data) {
  const { email, password } = data;
  let role = 'paciente'
  let token = ''
  // TODO - retirar esse mock quando implementar o back
  if (email === 'medico@gmail.com') {
    if (password !== 'medico123') { throw new Error('Email ou senha inválidos'); }
    url = 'http://localhost:3000/login_medico';
    role = 'medico';
    token = 'eyJleHBpcmVzSW4iOiI0aCIsImFsZyI6IkhTMjU2IiwidHlwIjoiSldUIn0.eyJpZCI6MywidGlwbyI6Im1lZGljbyJ9.8A2941kkCcqDsIoIepQXut3lO9Wh3dZ_MeWp1pvATCY'
  } else {
    if (password !== 'paciente123') { throw new Error('Email ou senha inválidos') }
    url = 'http://localhost:3000/login_paciente';
    token = 'eyJleHBpcmVzSW4iOiI0aCIsImFsZyI6IkhTMjU2IiwidHlwIjoiSldUIn0.eyJpZCI6MSwidGlwbyI6InBhY2llbnRlIn0.4CSuDbEzK52iDq5d0PtAY6dDtYxPFCDr9VNdsWeVctQ'
  }

  return { token, role };
}

/*
  Função que valida o login, e caso estaja correto adiciona os dados ao localstorage e retorna true
*/
async function loginValidations(email, password) {
  const data = { email, password };
  try {
    const { token, role } = await requestLoginWithRole('/login', data);
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

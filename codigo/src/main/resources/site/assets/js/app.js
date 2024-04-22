/*
*  Variaveis globais de ambiente
*  - baseUrl - é essencial para a construção de URLs relativas
*  - requestPath - é a URL da requisição atual
*/
const baseUrl = window.location.origin + (window.location.hostname !== "localhost" ? '/' + window.location.pathname.split('/')[1] : '');
const baseURLRequest= 'http://..........';

document.addEventListener('DOMContentLoaded', () => {
  const logoutButton = document.querySelector('#logout');
  if (logoutButton) {
      logoutButton.addEventListener('click', logout);
  }
});

/*
  Função que gera um UUID para uma quantidade de caracteres
*/
function generateUUID(qtde) {
  uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    var r = Math.random() * 16 | 0,
      v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });

  if (qtde) { return uuid.slice(0, qtde) }
  else { return uuid }
}

/*
  Função que retorna o ID da URL
*/
function getIDFromURL() {
  const urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get('id');
  return id ? id : null;
}

/*
  Função que verifica se existe token/role no sessionStorage e retorna um booleano
*/
function doesUserSessionExists() {
  return sessionStorage.getItem('token') && sessionStorage.getItem('role');
}

//TODO - Separar logica talvez... Poder add Alert e retirar doesUserSessionExists
/*
  Função que verifica se o token existe no sessionStorage e realiza o logout do usuário caso nao exista
*/
function getUserSession() {
  const token = sessionStorage.getItem('token');
  const role = sessionStorage.getItem('role');
  return token && role ? {token, role } : logout();
}
/*
  Função que decodifica o token JWT
*/
async function decodeJwt (token) {
  return await JSON.parse(decodeURIComponent(atob(token.split('.')[1].replace('-', '+')
          .replace('_', '/')).split('').map(c => `%${('00' + c.charCodeAt(0)
          .toString(16)).slice(-2)}`).join(''))); }

/*
  Função que realiza o logout do usuário e redireciona para a página de login
*/
function logout() {
  sessionStorage.removeItem('token');
  sessionStorage.removeItem('role');
  window.location.href = baseUrl + '/login';
}

/*
  Função que realiza a requisição para o servidor
*/
async function requestData(url, method, body) {
  const response = await fetch(url, {
    method: method,
    mode: 'no-cors',
    headers: {
      'Content-Type': 'application/json',
    },
    body: body ? JSON.stringify(body) : '',
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }
  data = await response.json();
  return data;
};

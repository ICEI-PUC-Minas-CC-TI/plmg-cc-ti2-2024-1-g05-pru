/*
*  Variaveis globais de ambiente
*  - baseUrl - é essencial para a construção de URLs relativas
*  - requestPath - é a URL da requisição atual
*/
const baseUrl = window.location.origin + (window.location.hostname !== "localhost" ? '/' + window.location.pathname.split('/')[1] : '');
const baseURLRequest= 'http://localhost:6789';


// Date formater
function formatDate(dateString) {
  const formatter = new Intl.DateTimeFormat('pt-BR', { day: '2-digit', month: '2-digit', year: 'numeric' });
  
  return formatter.format(Date.parse(dateString));
}

document.addEventListener('DOMContentLoaded', () => {
  const logoutButton = document.querySelector('#logout');

  logoutButton ? logoutButton.addEventListener('click', logout) : null;
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

  return qtde ? uuid.slice(0, qtde) : uuid;
}

/*
  Função que retorna o ID no query string da URL
*/
function getUrlId() {
  const urlParams = new URLSearchParams(window.location.search);
  return Number.parseInt(urlParams.get('id')) || null;
}

/*
  Função que verifica se o token existe no sessionStorage e realiza o logout do usuário caso nao exista
*/
function getUserSession() {
  return sessionStorage.getItem('token') ?
    sessionStorage.getItem('token') :
    null;
}

/*
  Função que decodifica o token JWT
*/
function decodeJwt (token) {
  return JSON.parse(atob(token.split('.')[1]));
}

/*
  Função que realiza o logout do usuário e redireciona para a página de login
*/
function logout() {
  sessionStorage.removeItem('token');

  window.location.href = baseUrl + '/login';
}

async function requestData(url, method, body) {
  const options = {
    method: method,
    headers: {
      'Content-Type': 'application/json',
    },
  };

  if (body && method !== 'GET') {
    options.body = JSON.stringify(body);
  }

  const response = await fetch(url, options);

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  return await response.json();
};

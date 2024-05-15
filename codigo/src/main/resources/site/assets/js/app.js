/*
*  Variaveis globais de ambiente
*  - baseUrl - é essencial para a construção de URLs relativas
*  - requestPath - é a URL da requisição atual
*/
const baseUrl = window.location.origin + (window.location.hostname !== "localhost" ? '/' + window.location.pathname.split('/')[1] : '');
const baseURLRequest= 'http://localhost:6789';


document.addEventListener('DOMContentLoaded', () => {
  const logoutButton = document.querySelector('#logout');

  logoutButton ? logoutButton.addEventListener('click', logout) : null;
});

/*
  Função que realiza o logout do usuário e redireciona para a página de login
*/
function logout() {
  sessionStorage.removeItem('token');

  window.location.href = baseUrl + '/login';
}

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
  return sessionStorage.getItem('token') || null;
}

/*
  Função que decodifica o token JWT
*/
function decodeJwt (token) {
  try {
    return JSON.parse(atob(token.split('.')[1]));
  } catch (e) {
    return null;
  }
}

// Date formater
function formatDate(dateString) {
  const formatter = new Intl.DateTimeFormat('pt-BR', { day: '2-digit', month: '2-digit', year: 'numeric' });

  return formatter.format(Date.parse(dateString));
}

function getTimeStamp() {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  const seconds = String(now.getSeconds()).padStart(2, '0');

  return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
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

  if (response.status !== 204) {
    const contentType = response.headers.get("content-type");

    if (contentType && contentType.includes("application/json")) {
      return response.json();
    } else {
      return response.text();
    }
  }
};

/*
*  Variaveis globais de ambiente
*  - baseUrl - é essencial para a construção de URLs relativas
*  - requestPath - é a URL da requisição atual
*/
const baseUrl = window.location.origin + (window.location.hostname !== "localhost" ? '/' + window.location.pathname.split('/')[1] : '');
const requestPath = 'http://localhost:3000/db';

// Função para gerar UUIDs, podendo passar a quantidade de caracteres desejada
function generateUUID(qtde) {
  uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    var r = Math.random() * 16 | 0,
      v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });

  if (qtde) { return uuid.slice(0, qtde) }
  else { return uuid }
}

function getIDFromURL() {
  const urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get('id');
  return id ? id : null;
}

// TODO - implementar função que retorna o tipo de usuário
function getUserType() {
  return 'paciente';
  //return 'medico';
}

// TODO - implementar função que retorna se existe uma sessão de usuário
function userSessionExists() { return false; }
//function userSessionExists() { return sessionStorage.getItem('user') ? true : false; }

function fetchData() {
  fetch(requestPath)
    .then(response => response.json())
    .then(data => {
      // Process the fetched data here
      console.log(data);
    })
    .catch(error => {
      // Handle any errors here
      console.error(error);
    });
}
fetchData();
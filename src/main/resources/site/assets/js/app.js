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

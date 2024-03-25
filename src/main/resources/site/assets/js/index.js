// fuction used to determine if user is logged or not.

//function userSessionExists() { return sessionStorage.getItem('user') ? true : false; }
function userSessionExists() { return false; }

function getIDFromURL() {
  const urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get('id');
  return id ? id : null;
}
window.addEventListener('load', loadHeader, false);

function loadHeader() {
  const login = document.querySelector('header nav .menu li.login');
  const profile = document.querySelector('header nav .menu li.profile');

  if (userSessionExists()) {
    login.style.display = 'none';
    profile.style.display = 'block';
  } else {
    login.style.display = 'block';
    profile.style.display = 'none';
  }
}

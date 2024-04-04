window.addEventListener('load', loadHeader, false);

function loadHeader() {
  const login = document.querySelector('header nav .menu li.login');
  const profile = document.querySelector('header nav .menu li.profile');
  const logout = document.querySelector('header nav .menu li.logout');
  const isUserSessionExists = userSessionExists();

  login.style.display = isUserSessionExists ? 'none' : 'block';
  profile.style.display = isUserSessionExists ? 'block' : 'none';
  logout.style.display = isUserSessionExists ? 'flex' : 'none';
}

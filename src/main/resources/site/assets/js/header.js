window.addEventListener('load', loadHeader, false);

function loadHeader() {
  const login = document.querySelector('header nav .menu li.login');
  const profile = document.querySelector('header nav .menu li.profile');
  const logout = document.querySelector('header nav .menu li.logout');
  const menuPages = document.querySelectorAll('header nav li.page');
  const isUserSessionExists = userSessionExists();
  const role = getUserType();

  login.style.display = isUserSessionExists ? 'none' : 'block';
  profile.style.display = isUserSessionExists ? 'block' : 'none';
  logout.style.display = isUserSessionExists ? 'flex' : 'none';

  if (role === 'paciente') {
    // display only menu pages who are not restricted (.all class)
    menuPages.forEach((menuPage) => {
      if (menuPage.classList.contains('paciente')) {
        menuPage.style.display = 'flex';
        console.log(menuPage);
      }
    });
  } else if (role === 'medico') {
    // display only menu pages who are not restricted (.all class)
    menuPages.forEach((menuPage) => {
      if (menuPage.classList.contains('medico')) {
        menuPage.style.display = 'flex';
        console.log(menuPage);
      }
    });
  }
}

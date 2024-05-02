window.addEventListener('load', () => {
  const login = document.querySelector('header nav .menu li.login');
  const profile = document.querySelector('header nav .menu li.profile');
  const logout = document.querySelector('header nav .menu li.logout');
  const menuPages = document.querySelectorAll('header nav li.page');

  const token = getUserSession();

  login.style.display = token ? 'none' : 'block';
  profile.style.display = token ? 'block' : 'none';
  logout.style.display = token ? 'flex' : 'none';

  // early return
  if (!token)
    return;


  const tipo = decodeJwt(token).tipo || 'none';

  if (tipo === 'Paciente') {
    menuPages.forEach((menuPage) => {
      if (menuPage.classList.contains('paciente')) {
        menuPage.style.display = 'flex';
      }
    });
  } else if (tipo === 'Medico') {
    menuPages.forEach((menuPage) => {
      if (menuPage.classList.contains('medico')) {
        menuPage.style.display = 'flex';
      }
    });
  }
});

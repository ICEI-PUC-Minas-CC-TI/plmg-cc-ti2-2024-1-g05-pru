document.querySelector('.login').addEventListener('submit', loginValidations);
import jwt from 'jsonwebtoken';
/*
  Função que chamara o back para validar o login, este retornara 
  seu jwt caso esteja correto e entao sera adicionado ao sesionStoare
*/

// Valores padroes que serao usados no token, TODO no back
const secretKey = 'CHAVE_SECRETA_PRU';
const jwtConfig = {
  "expiresIn": "4h",
  "alg": "HS256",
  "typ": "JWT"
};

async function loginValidations() { 
  try {
    const { token } = await requestLogin('/login', { email, password });
    const { role } = await requestData('/login/role', { email, password });

    localStorage.setItem('token',  token);
    localStorage.setItem('role',  role);

    return true;

  } catch (error) {
    alert('Email ou senha inválidos');
  }
}

function login(event) {
  event.preventDefault();

  const email = document.querySelector('#email').value;
  const password = document.querySelector('#senha').value;

  if(loginValidations(email, password)) {
    
  }
  console.log(email, password);
}

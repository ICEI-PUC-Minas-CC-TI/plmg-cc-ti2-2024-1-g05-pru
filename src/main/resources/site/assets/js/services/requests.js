export const requestData = async (endpoint) => {
  // const { data } = await get(endpoint); esboco
  return data;
};

async function requestLoginWithRole(url, data) {
  // Com o server real sera feito aproximadamente assim
  // const response = await fetch(url, {
  //   method: 'POST',
  //   headers: {
  //     'Content-Type': 'application/json',
  //   },
  //   body: JSON.stringify(data),
  // });

  // TODO - retirar esse mock
  if(data.email === 'medico@gmail.com') {
    url = 'http://localhost:3000/login_medico';
  } else {
    url = 'http://localhost:3000/login_paciente';
  }
  const response = await fetch(url, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });
  
  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  const { token, role } = await response.json();
  return { token, role };
}

export { requestLoginWithRole };
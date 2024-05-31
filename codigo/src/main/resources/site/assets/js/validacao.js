document.querySelector('form label').addEventListener('click', () => {
  document.querySelector('input').click();
});

const form = document.querySelector('form');

form.addEventListener('submit', async (event) => {
  event.preventDefault();

  const formData = new FormData(form);
  formData.append('file', form.querySelector('input[type="file"]').files[0]);
  formData.append('upload_preset', 'gzq11i84');

  const response = await fetch('https://api.cloudinary.com/v1_1/andrels-net/image/upload', {
    method: 'POST',
    body: formData
  });

  document.querySelector('#load').style.display = 'block';

  const data = await response.json();

  console.log(data.url);
});

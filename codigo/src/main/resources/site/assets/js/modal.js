var modal = document.getElementById('modal');

document.querySelector('#open-modal').addEventListener('click', () => {
  modal.style.display = 'block';
}, false);

document.querySelector('#close-modal').addEventListener('click', () => {
  modal.style.display = 'none';
}, false);

window.addEventListener('click', (event) => {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}, false);

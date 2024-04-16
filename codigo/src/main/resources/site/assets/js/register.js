document.querySelector('.radio-input').addEventListener('click', typeSelect);

function typeSelect() {
  const doctorInfo = document.querySelector('.doctor-info');
  const personalInfo = document.querySelector('.personal-info');
  const option = document.querySelector('.radio-input [name="account-type"]:checked')?.value;

  if (!doctorInfo || !personalInfo || !option) return;

  const doctorDisplayValue = option === 'paciente' ? 'none' : 'block';

  personalInfo.style.display = 'block';
  doctorInfo.style.display = doctorDisplayValue;
}

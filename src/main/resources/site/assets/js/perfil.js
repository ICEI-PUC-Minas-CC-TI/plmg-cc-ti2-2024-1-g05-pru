window.addEventListener('load', showTypeSection);

function showTypeSection() {
  const doctorInfo = document.querySelector('.doctor-info');
  const crmDoctor = document.querySelector('#crm');
  const patientInfo = document.querySelector('.patient-info');

  //const userType = getUserType();
  userType = 'paciente'

  if (userType === 'paciente') {
    doctorInfo.style.display = 'none';
    crmDoctor.style.display = 'none';
    patientInfo.style.display = 'flex';
  } else {
    patientInfo.style.display = 'none';
    doctorInfo.style.display = 'flex';
    crmDoctor.style.display = 'block';
  }
}

examId = getIDFromURL();
window.addEventListener('load', loadResult, false);

function loadResult() {
  const dowload = document.querySelector('section.result div.download');
  const upload = document.querySelector('section.result div.upload');
  const isExamUploaded = ExamUploaded();

  dowload.style.display = isExamUploaded ? 'inherit' : 'none';
  upload.style.display = isExamUploaded ? 'none' : 'inherit';
}

// implementar função futuramente
function ExamUploaded() {
  return examId ? true : false;
}
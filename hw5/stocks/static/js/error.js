function renderError() {
  const navBar = document.getElementById('horizontal-navbar');
  navBar.innerHTML = '';
  return `
  <div class="error">
    <h4>Error: No record has been found, please enter a valid symbol.</h4>
  </div>
  `;
}

function renderNavBar() {
  const navBar = document.getElementById('horizontal-navbar');
  navBar.innerHTML = `
    <button class="tab-button active" onclick="changeTab(event, 'company-outlook')">Company Outlook</button>
    <button class="tab-button" onclick="changeTab(event, 'stock-summary')">Stock Summary</button>
    <button class="tab-button" onclick="changeTab(event, 'charts')">Charts</button>
    <button class="tab-button" onclick="changeTab(event, 'latest-news')">Latest News</button>
  `;
}

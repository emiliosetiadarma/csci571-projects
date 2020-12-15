function renderCompanyOutlook(info) {
  return `
    <div id="company-outlook" class="tab-content">
      <table style="width: 100%">
        <tr class="table-row">
          <td class="table-category">Company Name</td>
          <td class="table-content">${info.name}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">Stock Ticker Symbol</td>
          <td class="table-content">${info.ticker}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">Stock Exchange Code</td>
          <td class="table-content">${info.exchangeCode}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">Company Start Date</td>
          <td class="table-content">${info.startDate}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">Description</td>
          <td class="table-content clamp-to-five">${info.description}</td>
        </tr>
      </table>
    </div>
  `;
}

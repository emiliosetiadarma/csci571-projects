function renderStockSummary(info) {
  const greenArrowUrl = 'https://csci571.com/hw/hw6/images/GreenArrowUp.jpg';
  const redArrowUrl = 'https://csci571.com/hw/hw6/images/RedArrowDown.jpg';
  const tradingDay = new Date(info.timestamp);
  const change = info.last - info.prevClose;
  var changeArrow = '';
  if (change > 0) {
    changeArrow = `<img src="${greenArrowUrl}" class="change-arrow" />`;
  } else if (change < 0) {
    changeArrow = `<img src="${redArrowUrl}" class="change-arrow" />`;
  }
  const percentChange = (change / info.prevClose) * 100;
  return `
    <div id="stock-summary" class="tab-content" style="display: none">
      <table style="width: 100%">
        <tr class="table-row">
          <td class="table-category">Stock Ticker Symbol</td>
          <td class="table-content">${info.ticker}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">Trading Day</td>
          <td class="table-content">${formatDate(tradingDay)}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">Previous Closing Price</td>
          <td class="table-content">${twoDecimals(info.prevClose)}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">Opening Price</td>
          <td class="table-content">${twoDecimals(info.open)}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">High Price</td>
          <td class="table-content">${twoDecimals(info.high)}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">Low Price</td>
          <td class="table-content">${twoDecimals(info.low)}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">Last Price</td>
          <td class="table-content">${twoDecimals(info.last)}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">Change</td>
          <td class="table-content">${twoDecimals(change)}   ${changeArrow}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">Change Percent</td>
          <td class="table-content">${twoDecimals(
            percentChange
          )}%   ${changeArrow}</td>
        </tr>
        <tr class="table-row">
          <td class="table-category">Number of Shares Traded</td>
          <td class="table-content">${info.volume}</td>
        </tr>
      </table>
    </div>
  `;
}

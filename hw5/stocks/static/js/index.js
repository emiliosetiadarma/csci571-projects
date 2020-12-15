// form submitting
const searchForm = {
  ticker: document.getElementById('ticker'),
  submit: document.getElementById('submit'),
  form: document.getElementById('search-form')
};

searchForm.form.addEventListener('submit', (e) => {
  e.preventDefault();
  const request = new XMLHttpRequest();
  request.onload = () => {
    let responseOject = null;
    try {
      responseObject = JSON.parse(request.responseText);
    } catch (e) {
      console.error('Could not parse JSON!');
    }

    if (responseObject) {
      handleResponse(responseObject);
    }
  };
  params = '?ticker=' + searchForm.ticker.value;
  url =
    'https://pythonstocksearch-emilio-v5.wl.r.appspot.com/api/stock' + params;
  request.open('get', url);
  request.send();
});

// render
function render(info) {
  const content = document.getElementById('content');
  if (!info.valid) {
    content.innerHTML = renderError();
    return;
  }
  renderNavBar();
  var newContent = renderCompanyOutlook(info.companyOutlook);
  newContent += renderStockSummary(info.stockSummary[0]);
  newContent += renderChartContainer();
  newContent += renderNews(info.news);
  content.innerHTML = newContent;
  renderCharts(info.charts);
}

// render the objects
function handleResponse(responseObject) {
  render(responseObject);
}

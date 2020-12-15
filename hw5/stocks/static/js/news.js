function renderNews(info) {
  addedContent = generateNewsBoxes(info);
  return `
    <div id="latest-news" class="tab-content" style="display: none">
      ${addedContent}
    </div>
  `;
}

function generateNewsBoxes(news) {
  if (news.length === 0) {
    return `
    <div class="error">
      <h4>Error: No news has been found, sorry.</h4>
    </div>
    `;
  }
  var result = '';
  news.forEach((article) => {
    var newContent = `
      <div>
        <table class="news-container">
          <tr>
            <td style="width: 14%"><img src="${
              article.urlToImage
            }" alt="news image url" class="news-image"/></td>
            <td class="news-content">
              <span style="font-weight: bold">${article.title}</span><br />
              Published Date: ${formatNewsDate(article.publishedAt)}<br />
              <a href="${article.url}">See Original Post</a>
            </td>
          </tr>
        </table>
      </div>
    `;
    result += newContent;
  });

  return result;
}

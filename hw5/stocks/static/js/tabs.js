function changeTab(e, tab) {
  var i, tabContent, tabButtons;
  tabContent = document.getElementsByClassName('tab-content');
  for (i = 0; i < tabContent.length; i++) {
    tabContent[i].style.display = 'none';
  }

  tabButtons = document.getElementsByClassName('tab-button');
  for (i = 0; i < tabButtons.length; i++) {
    tabButtons[i].className = tabButtons[i].className.replace(' active', '');
  }

  document.getElementById(tab).style.display = 'block';
  e.currentTarget.className += ' active';
}

const toggleBtn = document.querySelector('.mobile-menu');
const toggleBtnIcon = document.querySelector('.material-symbols-outlined');
const dropDownMenu = document.querySelector('.dropdown_menu');

toggleBtn.onclick = function () {
    dropDownMenu.classList.toggle('open');
}
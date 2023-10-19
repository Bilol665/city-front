// to get current year
function getYear() {
    const currentDate = new Date();
    document.querySelector("#displayYear").innerHTML = currentDate.getFullYear();
}
getYear();

// nav menu 
function openNav() {
    document.getElementById("myNav").classList.toggle("menu_width")
    document.querySelector(".custom_menu-btn").classList.toggle("menu_btn-style")
}

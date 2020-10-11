function goToProfile() {
document.location.replace('http://localhost:8080/' + getMyId() + '/');
}

function goToDirect() {
   document.location.replace('http://localhost:8080/direct/inbox');
}

function getMyId() {
   let pathname = document.location.pathname
   return pathname.replace(/\//g, '')
}
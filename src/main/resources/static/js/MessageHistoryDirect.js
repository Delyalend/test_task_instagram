
function getHistory() {

    let offset = document.getElementById('dataOffset').dataset.offset

    let chatId = document.location.pathname.substring(10)
    fetch('/chat/'+chatId+'/messageHistory/'+offset)
      .then(
        function(response) {
          if (response.status !== 200) {
            console.log('Looks like there was a problem. Status Code: ' +
              response.status);
            return;
          }

          response.json().then(function(data) {

            displayMessages(data)
          });
        }
      )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });

}


function displayMessages(msgJson) {
    let offset = document.getElementById('dataOffset')
    let msgList = document.getElementById('messageList')

    for(let i = 0; i < msgJson.length; i++) {
        offset.setAttribute('data-offset', Number.parseInt(offset.getAttribute('data-offset')) + 1)
        msgList.innerHTML += '<div>'+msgJson[i].content+'</div>'
    }
}


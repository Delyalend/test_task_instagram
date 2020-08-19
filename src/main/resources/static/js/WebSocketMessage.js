let subscription
function subscribeOnChat() {
    let chatId = document.location.pathname.substring(10)
    subscription = client.subscribe('/topic/stompChat/'+chatId, displayMessage)
}

function buttonSendTextClick(){
    let chatId = document.location.pathname.substring(10)
    let textArea = document.getElementById('ta')
    let msg =
    {
        content: textArea.value,
        type: 'text'
    }
    client.send('/app/stompChat.sendMessage/'+chatId, {}, JSON.stringify(msg))
}

function buttonSendImageClick(){
}


function displayMessage(payload) {
    let data = JSON.parse(payload.body)

    //Нужно найти лист сообщений
    let divForMarkup = document.getElementById('messageList')

    //Затем вызвать метод, который нарисует разметку
    addMarkupMessage(data, divForMarkup)
}

function addMarkupMessage(data, divForMarkup) {
    divForMarkup.innerHTML += '<div class=\"message\">'+data.content+'</div>'
}



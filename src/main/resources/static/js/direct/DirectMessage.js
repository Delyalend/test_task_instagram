


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

    let msgList = document.getElementById('messageList')

    addMarkupMessage(data, msgList)
}


function addMarkupMessage(data, msgList) {
    const pixelPerChar = 10
    const maxWidthMsg = 300
    let offset = document.getElementById('dataOffset')
    let iamId = document.getElementById('iamId').value

    let length = data.content.replace(/\s+/g, '').length

    let widthMsg = length * pixelPerChar
    if(widthMsg > maxWidthMsg) {
        widthMsg = maxWidthMsg
    }

    let msgMarkup =  getMessageMarkup(msgList,data,widthMsg)

    if(iamId==data.ownerId) {
        msgMarkup('iamMessage')
    }
    else if(iamId != data.ownerId) {
        msgMarkup('opponentMessage')
    }

    document.getElementById('messagePanel').scrollTop = document.getElementById('messagePanel').scrollHeight;
    offset.setAttribute('data-offset', Number.parseInt(offset.getAttribute('data-offset')) + 1)
}

function getMessageMarkup(msgList, data, widthMsg) {
    return function createMarkup(msgClass) {
        msgList.insertAdjacentHTML('beforeend', '<div class="message '+msgClass+'" style="width: '+widthMsg+'px;"><p class="textMessage">'+data.content+'</p></div>')
    }
}




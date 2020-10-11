function clearMessageList() {
    let msgList = document.getElementById('messageList')
    msgList.innerHTML = ''
}

function clearOffset() {
     document.getElementById('dataOffset').setAttribute('data-offset', 0)
}

let selectedUser = ''
function clickUser(user) {
    clearOffset()

    let chatId = user.dataset.chatId
    window.history.pushState(null,null,'http://localhost:8080/direct/t/'+chatId)

    getHistory()

    if(selectedUser != '') {
        clearMessageList()
        subscription.unsubscribe()
        selectedUser.style.background = '#ffffff'
    }

    selectedUser = user
    selectedUser.style.background = '#efefef'


    MessagePanel.setDisplay('message-panel-view-1', 'none')
    MessagePanel.setDisplay('message-panel-view-2', 'block')

    subscribeOnChat()
}


function displayChatOpponent(id) {
    let opponent = document.getElementById(id)
    if (opponent != null) {
        clickUser(opponent)
    }
}

function displayAllChats(payload) {
        let data = JSON.parse(payload.body)
        let chatList = document.getElementById('chatList')
        addMarkupUser(data, chatList)
}


function addMarkupUser(data, divForMarkup) {
    for (let i = 0; i < data.length; i++) {
        let chatId = data[i].chatId
        divForMarkup.innerHTML += '<div data-chat-id=\"'+ data[i].chatId +'\" class=\"container_user\" onmouseenter=\"setBackground(this, \'#F7F7F7\')\" '+
        'onmouseleave=\"setBackground(this, \'#ffffff\')\" onclick=\"clickUser(this)\">'+
        '<img class=\"round\" src=\"https://i.ibb.co/NWGSMjQ/user.png/\">'+
        '<label class=\"nickname\">'+ data[i].username + '</label>'+
        '</div>';
    }
}


class MessagePanel {

    static setDisplay(panelId, displayType) {
        let panel = document.getElementById(panelId)
        panel.style.display = displayType
    }

}

function setBackground(user, background) {
    if(selectedUser != user) {
        user.style.background = background
    }
}




let client = null

document.addEventListener("DOMContentLoaded", () => {

    client = Stomp.client('ws://localhost:8080/stompDirect')
    let promise = new Promise((resolve, reject) => {
        client.connect({}, function () {
        client.subscribe('/topic/stompDirect/'+iam.value, displayAllChats)
        resolve()
        })
    })
    promise.then(
        result => {
            client.send('/app/stompDirect.getChats')
            displayChatOpponent('opponent')
        },
        error => {
            console.log("rejected: " + reject)
        })


})




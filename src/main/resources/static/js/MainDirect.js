class TextArea {

    static id = 'ta'

    static minHeight = 40
    static maxHeight = 80

    static setMinHeight() {
        document.getElementById(this.id).style.height = this.minHeight
    }

    static setMaxHeight() {
        document.getElementById(this.id).style.height = this.maxHeight
    }

    static getValue() {
       return document.getElementById(this.id).value
    }

    static setOverflowY(value) {
        document.getElementById(this.id).style.overflowY=value
    }

    static getElement() {
        return document.getElementById(this.id)
    }

}

function clearMessageList() {
    let msgList = document.getElementById('messageList')
    msgList.innerHTML = ''
}

function clearOffset() {
     document.getElementById('dataOffset').setAttribute('data-offset', 0)//запусти
}

function clickUser(user) {
clearOffset()



    let chatId = user.dataset.chatId
    window.history.pushState(null,null,'http://localhost:8080/direct/t/'+chatId)

    getHistory()

    if(User.selectedUser != null) {
        clearMessageList()

        subscription.unsubscribe()
        User.setBackground(Color.white)
        User.setValue('')
    }

    User.setSelectedUser(user)
    User.setBackground(Color.darkWhite)


    MessagePanel.setDisplay('message-panel-view-1', 'none')
    MessagePanel.setDisplay('message-panel-view-2', 'block')

    subscribeOnChat()
}

class Chat {
    static tryDisplay(id) {
        let opponent = document.getElementById(id)
        if (opponent != null) {
            clickUser(opponent)
        }
    }

    static displayAll(payload) {
        let data = JSON.parse(payload.body)
        let divForMarkup = document.getElementById('chatList')
        addMarkupUser(data, divForMarkup)
    }
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


class User {
    selectedUser = ''

    static setSelectedUser(selectedUser) {
        this.selectedUser = selectedUser
    }

    static setBackground(background) {
        this.selectedUser.style.background = background
    }

    static setValue(val) {
        this.selectedUser.value = val
    }


}

function setBackground(user, background) {
        if(User.selectedUser != user) {
            user.style.background = background
        }
}


class Color {
    static white = '#ffffff'
    static darkWhite = '#efefef'
}

class TextAreaButtonDiv {

    static id = 'textAreaButtonDiv'

    static minHeight = 38
    static maxHeight = 78
    static minWidth = 80
    static maxWidth = 100

    static setMaxHeight() {
        document.getElementById(this.id).style.height = this.maxHeight
    }

    static setMinHeight() {
        document.getElementById(this.id).style.height = this.minHeight
    }

    static setMaxWidth() {
        document.getElementById(this.id).style.width = this.maxWidth
    }

    static setMinWidth() {
        document.getElementById(this.id).style.width = this.minWidth
    }

}

class TextAreaButton {
    static id = 'textAreaButton'

    static minMarginLeft = 0
    static maxMarginLeft = 20
    static minMarginTop = 5
    static maxMarginTop = 25

    static setMinMarginLeft() {
        document.getElementById(this.id).style.marginLeft = this.minMarginLeft
    }

    static setMaxMarginLeft() {
        document.getElementById(this.id).style.marginLeft = this.maxMarginLeft
    }

    static setMinMarginTop() {
        document.getElementById(this.id).style.marginTop = this.minMarginTop
    }

    static setMaxMarginTop() {
        document.getElementById(this.id).style.marginTop = this.maxMarginTop
    }

    static setDisplay(display) {
        document.getElementById(this.id).style.display = display
    }

}

class ImageDiv {
    static id = 'imageDiv'

    static setDisplay(display) {
        document.getElementById(this.id).style.display = display
    }
}

TextArea.getElement().addEventListener('keyup', function(){

    if(TextArea.getValue().length == 0) {
        TextAreaButton.setDisplay('none')
        ImageDiv.setDisplay('block')
    }

    if(TextArea.getValue().length > 0) {
        TextAreaButton.setDisplay('block')
        ImageDiv.setDisplay('none')
    }

    if(TextArea.getValue().length > 104) {

        TextAreaButtonDiv.setMaxHeight()
        TextAreaButtonDiv.setMaxWidth()

        TextAreaButton.setMaxMarginTop()
        TextAreaButton.setMaxMarginLeft()

        TextArea.setMaxHeight()

        TextArea.setOverflowY('scroll')

    }
    else if(TextArea.getValue().length < 104) {
        TextAreaButtonDiv.setMinHeight()
        TextAreaButtonDiv.setMinWidth()

		TextAreaButton.setMinMarginTop()
        TextAreaButton.setMinMarginLeft()

        TextArea.setMinHeight()

        TextArea.setOverflowY('hidden')
    }

});

let client = null

document.addEventListener("DOMContentLoaded", () => {

    client = Stomp.client('ws://localhost:8080/stompDirect')
    let promise = new Promise((resolve, reject) => {
        client.connect({}, function () {
        client.subscribe('/topic/stompDirect/'+iam.value, Chat.displayAll)
        resolve()
        })
    })
    promise.then(
        result => {
            //client.send('/app/stompDirect.getChats/'+iam.value)
            client.send('/app/stompDirect.getChats')
            Chat.tryDisplay('opponent')
        },
        error => {
            console.log("rejected: " + reject)
        })


})




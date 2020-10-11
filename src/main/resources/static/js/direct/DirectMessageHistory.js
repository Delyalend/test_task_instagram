function getHistory() {
    let offset = document.getElementById('dataOffset').dataset.offset

       let chatId = document.location.pathname.substring(10)
       fetch('/chat/'+chatId+'/messageHistory/'+offset)
         .then(
           function(response) {
             if (response.status !== 200) {
               console.log('Looks like there was a problem. Status Code: ' + response.status)
               return
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


function displayMessages(data) {
    const pixelPerChar = 10
    const maxWidthMsg = 300
    let iamId = document.getElementById('iamId').value
    let offset = document.getElementById('dataOffset')
    let msgList = document.getElementById('messageList')

for(let i = 0; i < data.length; i++) {
    offset.setAttribute('data-offset', Number.parseInt(offset.getAttribute('data-offset')) + 1) //Надо вынести в отедльный метод addToDataOffset()

    let length = data[i].content.replace(/\s+/g, '').length
    let widthMsg = length * pixelPerChar //in pixel

    if(widthMsg > maxWidthMsg) {
        widthMsg = maxWidthMsg
    }

    let msgMarkup =  getMessageHistoryMarkup(msgList,data[i],widthMsg)

    if(iamId==data[i].ownerId) {
        msgMarkup('iamMessage')
    }
    else if(iamId != data[i].ownerId) {
        msgMarkup('opponentMessage')
    }

    document.getElementById('messagePanel').scrollTop = document.getElementById('messagePanel').scrollHeight;

    }
}


function getMessageHistoryMarkup(msgList, data, widthMsg) {
    return function createMarkup(msgClass) {
        msgList.insertAdjacentHTML('afterbegin', '<div class="message '+msgClass+'" style="width: '+widthMsg+'px;"><p class="textMessage">'+data.content+'</p></div>')
    }
}




function loadHistory() {
let msgPanelScroll = document.getElementById('messagePanel').scrollTop
if(msgPanelScroll <= 100) {
    let promise = new Promise((resolve,reject) => {
        getHistory()
        document.getElementById('messagePanel').removeEventListener("scroll", loadHistory)
        resolve()
    })

    promise.then(
        result => {
            document.getElementById('messagePanel').addEventListener('scroll', loadHistory)
        },
        error => {
            console.log('rejected: ' + error)
        }

    )

}
}


document.addEventListener('DOMContentLoaded', function() {

document.getElementById('messagePanel').addEventListener('scroll', loadHistory)
}, false);






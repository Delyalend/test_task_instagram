'use strict';

document.addEventListener("DOMContentLoaded", () => {
    connect()
  });

let stompClient = null
let username = null


function sendAndGetListChats() {
    stompClient.send('/app/stompDirect.getChats/'+iam.value)

}

function connect(event) {
        stompClient = Stomp.client('ws://localhost:8080/stompDirect')


 let promise = new Promise((resolve, reject) => {
     stompClient.connect({}, function () {
         stompClient.subscribe('/topic/stompDirect/'+iam.value, displayChats)
         resolve()
     })
 })

promise
    .then(
        result => {
            stompClient.send('/app/stompDirect.getChats/'+iam.value)
        },
        error => {
            console.log("rejected: " + reject)
        })


//     promise
//         .then(
//             result => {
//                 console.log('выполнил send')
//                 stompClient.send('/app/stompDirect.getChats',{},JSON.stringify({username:'testUsername'}))
//             },
//                 error => {
//                 console.log('rejected: ' + reject)
//             }
//         )

        //stompClient.connect({}, onConnected, onError)
}


function displayChats(payload) {
    let data = JSON.parse(payload.body);
    let chatList = document.getElementById('chatList')

    console.log('data: ' + data[0].username)

    for(let i = 0; i < data.length; i++) {
        chatList.innerHTML += '<div class=\"container_user\" onmouseenter=\"enterUser(this)\" onmouseleave=\"leaveUser(this)\"'+
        'onclick=\"clickUser(this)\" value=\"\"\>'+
        '\<img class=\"round" src=\"https\:\/\/i.ibb.co\/NWGSMjQ\/user.png\/\"\>'+
        '\<label class=\"nickname\"\>'+ data[i].username +'\<\/label\>'+
        '\<\/div\>'
    }



}

function onConnected() {
console.log('фаза ОнКоннектед!')
    stompClient.subscribe('/topic/stompDirect', onMessageReceived);
    console.log('фаза ОнКоннектед2!')
    stompClient.send("/app/stompDirect.getChats",
        {},
        JSON.stringify({username: 'testUsername'})
    )
    console.log('фаза онКоннектед закончилась!')
}

function onError(error) {
}



function sendMessage(event) {
        let chatMessage = {
            username: 'testUsername_1'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
}


function goToProfile() {
let username = document.getElementById('iam')
document.location.replace('http://localhost:8080/' + username.value + '/');
}


let selectedUser

function enterUser(user) {
	if(user.value != 'selected') {
		user.style.background="#F7F7F7"
	}
}

function leaveUser(user) {
	if(user.value != 'selected') {
		user.style.background="#ffffff"
	}
}

function clickUser(user) {

	if(selectedUser!=null) {
		selectedUser.style.background="#ffffff"
		selectedUser.value = ''
	}

	user.value = 'selected'
	selectedUser = user
	user.style.background="#efefef"

    hideMessagePanelView1()
	showMessagePanelView2()

}

function hideMessagePanelView1() {
    let messagePanelView1 = document.getElementById('message-panel-view-1')
    messagePanelView1.style.display = 'none';
}

function showMessagePanelView2() {
    let messagePanelView1 = document.getElementById('message-panel-view-2')
    messagePanelView1.style.display = 'block';
}


function sendMessage() {
}



let textarea = document.getElementById('ta');

	textarea.addEventListener('keyup', function(){

	let textAreaButtonDiv = document.getElementById('textAreaButtonDiv')
	let textAreaButton = document.getElementById('textAreaButton')
	let clazz = document.querySelector("textarea")
	let imageDiv = document.getElementById("imageDiv")

	if(ta.value.length == 0) {
		textAreaButton.style.display = 'none'
		imageDiv.style.display = 'block'
	}
	if(ta.value.length > 0) {
		textAreaButton.style.display = 'block'
		imageDiv.style.display = 'none'

	}
	if(ta.value.length >= 104) {
		textAreaButtonDiv.style.height = 78
		textAreaButtonDiv.style.width = 100
		textAreaButton.style.marginLeft = 20
		textAreaButton.style.marginTop = 25
		ta.style.height = 80
		let clazz = document.querySelector("textarea")
		clazz.style.overflowY='scroll'
	}
	else if(ta.value.length < 104){
		textAreaButtonDiv.style.height = 38
		textAreaButtonDiv.style.width = 80
		textAreaButton.style.marginLeft = 0
		textAreaButton.style.marginTop = 5
		ta.style.height = 40;
		clazz.style.overflowY='hidden'
	}
});

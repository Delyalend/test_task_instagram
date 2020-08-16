//WebSocketConnection


class WebSocketConnection {

    static getClient(wsUrl, subUrl, callback) {
        let client = Stomp.client(wsUrl)
        client.connect({}, function(){
            client.subscribe(subUrl,callback)
        })

        return client
    }

}


// function connect(event) {
//     client = Stomp.client('ws://localhost:8080/stompDirect')
//     let promise = new Promise((resolve, reject) => {
//      client.connect({}, function () {
//          client.subscribe('/topic/stompDirect/'+iam.value, displayChats)
//          resolve()
//         })
//     })

//     promise.then(
//         result => {
//             client.send('/app/stompDirect.getChats/'+iam.value)
//         },
//         error => {
//             console.log("function connect(): reject - " + reject)
//         }
//     )
// }

// function onError(error) {
// }
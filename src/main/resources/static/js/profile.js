
//function scroller(){
//    let requestIsSent = false
//    let inputWithNumberPage = document.getElementById('numberPage')
//    let windowSubs = document.getElementById('WindowSubs')
//    let usr = document.createElement('label')
//    let users = WindowSubs.dataset.users;
//    const distanceSliderForDownloadingDataInPercent = 95
//    const window = document.getElementById('window')
//    const request = new XMLHttpRequest()
//    const url = 'http://localhost:8080/'+document.location.pathname+'/followers'
//    windowSubs.appendChild(usr);
//    request.open('GET', url)
//    request.setRequestHeader('Content-Type', 'application/x-www-form-url')
//
//    if((window.scrollTop) > ((((Math.round(window.scrollHeight) - Math.round(window.clientHeight)))/100*distanceSliderForDownloadingDataInPercent))) {
//    if(requestIsSent === false) {
//    requestIsSent = true;
//        request.send()
//        console.log('отправил')
//    }
//        request.addEventListener("readystatechange", () => {
//        if (request.readyState === 4 && request.status === 200) {
//            requestIsSent=true
//            let data = JSON.parse(request.responseText).map(Object.values)
//            windowSubs.innerHTML += '<fieldset><label>------NEW------</label>'
//            for(let i = 0; i < data.length; i++) {
//                let id = data[i][0]
//                let username = data[i][1]
//                let name = data[i][2]
//                let avatar = data[i][3]
//                windowSubs.innerHTML += '<fieldset><label>' + name + '</label><label>' + username + '</label></fieldset>'
//            requestIsSent = false
//            inputWithNumberPage.value = (Number.parseInt(inputWithNumberPage.value) + 1)
//            }
//        }
//            else {
//                requestIsSent = false;
//                }
//                })
//            }
//    }

//document.addEventListener('readystatechange', event => {
//    if (event.target.readyState === "interactive") {
//        ready()
//    }
//});

//function ready() {
//    let inputWithNumberPage = document.getElementById('numberPage')
//    const request = new XMLHttpRequest()
//    const url = "http://localhost:8080/getMoreUsers/"+inputWithNumberPage.value
//    request.open('GET', url)
//    request.setRequestHeader('Content-Type', 'application/x-www-form-url')
//    let windowSubs = document.getElementById('WindowSubs')
//    let usr = document.createElement('label')
//    windowSubs.appendChild(usr);
//    let users = WindowSubs.dataset.users;
//    request.addEventListener("readystatechange", () => {
//      if (request.readyState === 4 && request.status === 200) {
//            let data = JSON.parse(request.responseText).map(Object.values)
//            for(let i = 0; i < data.length; i++) {
//                let id
//                let username
//                let name
//                let avatar
//                id = data[i][0]
//                username = data[i][1]
//                name = data[i][2]
//                avatar = data[i][3]
//                windowSubs.innerHTML += '<fieldset><label>' + name + '</label><label>' + username + '</label></fieldset>'
//            }
//            inputWithNumberPage.value = (Number.parseInt(inputWithNumberPage.value) + 1)
//      }
//  });
//  request.send()
//  }
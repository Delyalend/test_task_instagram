function openPost(event) {
    openPanelWithPostFromServer(event.target.dataset.postId)
}

function openPanelWithPostFromServer(postId) {
    let href = 'http://localhost:8080/getPost/' + postId
    let status = function (response) {
        if (response.status !== 200) {
            return Promise.reject(new Error(response.statusText))
        }
            return Promise.resolve(response)
    }
    let json = function (response) {
      return response.json()
    }
    fetch(href, {
        method: 'get'
    })
    .then(status)
    .then(json)
    .then(function (data) {
        openPostPanel(data)
    })
    .catch(function (error) {
        console.log('error', error)
    })
}

let currentImageForPublications
let currentPostForPublications

function openPostPanel(post) {
    console.log(post)
    let divImages = document.getElementById('images')
	document.getElementById('substrateForPostInfo').style.display = 'block'
    document.getElementById('postInfo').style.display = 'grid'
    let images = post.images
    for(let i = 0; i < images.length; i++) {
        let imgSrc = "data:image"+i+"/"+images[i].type+";base64,"+images[i].src
        divImages.insertAdjacentHTML('afterBegin', '<img style="display:none;" class="postImage" src="' + imgSrc + '" alt="your image" id="image'+ i +'">')
    }
    let firstImage = document.getElementById('image0')
    firstImage.style.display = 'block'
    firstImage.onload = function() {
        currentImageForPublications = images[0]
        currentPostForPublications = post
        displayTransferButtonsForImages(post)
        getCommentsForCurPostFromServer()
    }
}

function getCommentsForCurPostFromServer() {
    //let href = 'http://localhost:8080/post/' + currentPostForPublications.id + '/comments"
    let href = "http://localhost:8080/post/" + currentPostForPublications.id + "/comments"
    //console.log(currentPostForPublications.id)
    let status = function (response) {
        if (response.status !== 200) {
            return Promise.reject(new Error(response.statusText))
        }
            return Promise.resolve(response)
    }
    let json = function (response) {
      return response.json()
    }
    fetch(href, {
        method: 'get'
    })
    .then(status)
    .then(json)
    .then(function (data) {
        displayCommentsForPost(data)
    })
    .catch(function (error) {
        console.log('error', error)
    })
    console.log('getCommentsByPostIdFromServer(): 53 str')
    console.log(currentPostForPublications)
}

function displayCommentsForPost(comments) {
    console.log(comments)
    let divPostComments = document.getElementById('postComments')
    for(let i = 0; i < comments.length; i++) {
        let username = comments[i].user.username;
        let date = comments[i].date;
        let commentText = comments[i].text;
        divPostComments.insertAdjacentHTML('afterBegin', '<div><div class="commentsMain"><img class="iconForCommentDiv" src="https://i.ibb.co/nsGxBpj/user-1.png" width="32" height="32" style="margin-top: 10px;"><label class="usernameForOwnerComment">'+username+'</label><div class="textComment pTextComment">'+commentText+'</div></div><label style="margin-left: 80px;">'+date+'</label></div>')
    }
    //нужно взять комментарии поста и отобразить их
    //Но сначала нужно отобразить в первую очередь комментарий самого владельца поста, если таков имеется


}

function goToRightImage() {
    let images = currentPostForPublications.images
    let currentImageId
    for(let i = 0; i < images.length; i++) {
        if(images[i] == currentImageForPublications) {
            currentImageId = i
        }
    }

    currentImageForPublications = images[currentImageId + 1]
    document.getElementById('image'+currentImageId).style.display = 'none'
    document.getElementById('image'+Number.parseInt(currentImageId + 1)).style.display = 'block'
    displayTransferButtonsForImages(currentPostForPublications)

}

function goToLeftImage() {
    let images = currentPostForPublications.images
    let currentImageId
    for(let i = 0; i < images.length; i++) {
        if(images[i] == currentImageForPublications) {
            currentImageId = i
        }
    }

    currentImageForPublications = images[currentImageId - 1]
    document.getElementById('image'+currentImageId).style.display = 'none'
    document.getElementById('image'+Number.parseInt(currentImageId - 1)).style.display = 'block'
    displayTransferButtonsForImages(currentPostForPublications)
}

function displayLeftTransferArrowForImages(boolean) {
    if(boolean === true) {
        document.getElementById('leftTransferArrowForImages').style.display = "block"
    }
    else if(boolean === false) {
        document.getElementById('leftTransferArrowForImages').style.display = "none"
    }
}

function displayRightTransferArrowForImages(boolean) {
    if(boolean === true) {
        document.getElementById('rightTransferArrowForImages').style.display = "block"
    }
    else if(boolean === false) {
        document.getElementById('rightTransferArrowForImages').style.display = "none"
    }
}

function displayTransferButtonsForImages(post) {
    let images = post.images
    let currentId
    for(let i = 0; i < images.length; i++) {
        if(currentImageForPublications === images[i]) {
            currentId = i
        }
    }
    if(images.length > 1) {
        if(currentId === 0) {
            displayLeftTransferArrowForImages(false)
            displayRightTransferArrowForImages(true)
        }
        else if(currentId > 0 && currentId < images.length-1) {
            displayLeftTransferArrowForImages(true)
            displayRightTransferArrowForImages(true)
        }
        else if(currentId === images.length-1 || images.length == 1) {
            displayLeftTransferArrowForImages(true)
            displayRightTransferArrowForImages(false)
        }
    }
    else if(images.length == 1) {
        displayLeftTransferArrowForImages(false)
        displayRightTransferArrowForImages(false)
    }
}

document.getElementById('substrateForPostInfo').onclick = function() {
    document.getElementById('substrateForPostInfo').style.display = 'none'
    let divImages = document.getElementById('images')
    document.getElementById('postInfo').style.display = 'none'
    while (divImages.firstChild) {
        divImages.removeChild(divImages.firstChild);
    }
    let comments = document.getElementById('postComments')
    while(comments.firstChild) {
        comments.removeChild(comments.firstChild);
    }

}
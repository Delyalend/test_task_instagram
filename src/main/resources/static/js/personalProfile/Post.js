class Post {
	constructor() {
		this.images = []
		this.comment = ''
	}
}

class Image {
	type
	src
	constructor(type, src) {
		this.type = type
		this.src = src
	}
}

let images = []
let currentImage

function addImageToPost(image) {
    	const reader = new FileReader()
    	reader.readAsDataURL(image)
    	reader.onload = function() {
    		let imgInBase64 =  reader.result.replace(/^data:.+;base64,/, '')
        	let img = new Image(getFormatImage(image),imgInBase64)
        	post.images.push(img)
        }
        reader.onerror = error => reject(error)
}

function sendPostToServer() {
	let comment = document.getElementById('comment').value
	if(comment !== '') {
		post.comment = comment
	}

    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");

    let headers = new Headers();

    // Добавим пару заголовков
    headers.append('content-type', 'application/json')
    headers.append(header, token);

	data = JSON.stringify(post)

	fetch('http://localhost:8080/post', {
		method: 'POST',
		body: data,
		headers:headers
	})
	.then(function() {
	location.reload()
	})


}

function displayPreviewPostsFromServer() {
    let count = 100
    let offset = document.getElementById('postOffset').value
    let username = document.location.pathname.replace(/[/]/g, '')
    let href = 'http://localhost:8080/getPreviewPosts/' + username + '/' + count + '/' + offset
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
         addPostsToPage(data)
      })
      .catch(function (error) {
        console.log('error', error)
      })
}

document.addEventListener('DOMContentLoaded', function() {
   showPublications()
   displayPreviewPostsFromServer()
}, false);

function addPostsToPage(data) {
    console.log(data)
    let divPosts = document.getElementById('posts')
    for(let i = 0; i < data.length; i++) {
        let imgSrc = "data:image"+i+"/"+data[i].image.type+";base64,"+data[i].image.src
        divPosts.insertAdjacentHTML('afterBegin', '<img class="postImage" src="' + imgSrc + '" alt="your image" data-post-id="' + data[i].image.postId +'" id="image'+ i +'">')
        document.getElementById('image'+i).onclick = openPost
    }
}

function getFormatImage(image) {
    return image.type.split('image/')[1]
}
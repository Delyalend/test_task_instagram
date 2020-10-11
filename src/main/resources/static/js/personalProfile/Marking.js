let imgInp = document.getElementById('imgInp')



function showPublications() {

	document.getElementById('publicationsMenu').style.display = 'block'
	document.getElementById('creationPostMenu').style.display = 'none'
	document.getElementById('savedMenu').style.display = 'none'
	document.getElementById('marksMenu').style.display = 'none'
}

function showCreationPostMenu() {
	document.getElementById('publicationsMenu').style.display = 'none'
	document.getElementById('creationPostMenu').style.display = 'block'
	document.getElementById('savedMenu').style.display = 'none'
	document.getElementById('marksMenu').style.display = 'none'
}

function showSaved() {
	document.getElementById('publicationsMenu').style.display = 'none'
	document.getElementById('creationPostMenu').style.display = 'none'
	document.getElementById('savedMenu').style.display = 'block'
	document.getElementById('marksMenu').style.display = 'none'
}

function showMarks() {
	document.getElementById('publicationsMenu').style.display = 'none'
	document.getElementById('creationPostMenu').style.display = 'none'
	document.getElementById('savedMenu').style.display = 'none'
	document.getElementById('marksMenu').style.display = 'block'
}

function displayButtons() {
	displayButtonBack()
	displayButtonDelete()
	displayButtonForward()
}

function displayButtonBack() {
    if(images.length > 0 ) {
	    if(images[0] != currentImage) {
	    	document.getElementById('buttonBack').style.display = 'block'
	    }
	    else {
	    	document.getElementById('buttonBack').style.display = 'none'
	    }
	}
}

function displayButtonDelete() {
	if(images.length > 0 ) {
		document.getElementById('buttonDelete').style.display = 'block'
	}
	else {
		document.getElementById('buttonDelete').style.display = 'none'
	}
}

function displayButtonForward() {
	if(images.length > 1) {
		if(images[images.length-1] != currentImage) {
			document.getElementById('buttonForward').style.display = 'block'
		}
		else {
			document.getElementById('buttonForward').style.display = 'none'
		}
	}

}

function showPhotoPanel() {
	document.getElementById('photoPanel').style.display = 'block'
	document.getElementById('videoPanel').style.display = 'none'
}
function showVideoPanel() {
	document.getElementById('photoPanel').style.display = 'none'
	document.getElementById('videoPanel').style.display = 'block'
}

function displayImage() {
	let reader = new FileReader();
	reader.onload = function(e) {
	    let img = document.getElementById("blah")
	    img.src = e.target.result
	    img.style.maxWidth = 500
	    img.style.maxHeight = 500
	}
	reader.readAsDataURL(currentImage);
}

function buttonBack() {

	for(let i = 0; i < images.length; i++) {
		if(currentImage==images[i]) {
			currentImage = images[i-1]
			displayButtons()
			displayImage()
			break
		}
	}
}


function buttonDelete() {
	for(let i = 0; i < images.length; i++) {
		if(currentImage==images[i]) {

            document.getElementById('imgInp').parentNode.removeChild(document.getElementById('imgInp'))
            document.getElementById('photoPanel').insertAdjacentHTML('afterBegin','<input type="file" name="photo" accept="image/jpeg,image/png" id="imgInp">')

            if(images.length > 1) {
                if(i == images.length-1) {
                    currentImage = images[i-1]
                }
                else {
                    currentImage = images[i+1]
                }
                images.splice(i, 1)
                document.getElementById("blah").src = "#"
                displayButtons()
                displayImage()
            }
            else {
                images.splice(i, 1)
                document.getElementById("blah").src = "#"
                currentImage = ''
                displayButtons()
            }
            imgInp = document.getElementById('imgInp')
            imgInp.onchange = function() {
                  	addImageToPost(this.files[0])
                  	images.push(this.files[0])
                  	currentImage = this.files[0]
                  	displayImage()
                  	displayButtons()
                  }
            post.dtoImages.splice(i, 1)
            break

		}
	}
}

function buttonForward() {
	for(let i = 0; i < images.length; i++) {
		if(currentImage==images[i]) {
			currentImage = images[i+1]
			displayButtons()
			displayImage()
			break
		}
	}
}
let post = new Post()


//Если меняется поле ввода для изображений...
imgInp.onchange = function() {
    if(this.files[0] !== undefined) {
    	addImageToPost(this.files[0])
    	images.push(this.files[0])
    	currentImage = this.files[0]
    	displayImage()
    	displayButtons()
    }
}

	document.onclick = function(e) {
		let modalIsOpen = document.getElementById('modalIsOpen').value
		let transparentSubstrate = document.getElementById('transparent-substrate')
		if(e.target.id === 'transparent-substrate') {
		    document.getElementById('searchModal').style.display = 'none'
		    transparentSubstrate.style.display = 'none'
		}
		if(modalIsOpen==1) {
			if(e.target.id === 'substrate') {
			    if(document.getElementById('nameOfModal').value == 'modalFollowers') {
			        closeFollowersModal();
			    }
			    else if(document.getElementById('nameOfModal').value == 'modalFollows') {
			        closeFollowsModal();
			    }
			}
		}
	}
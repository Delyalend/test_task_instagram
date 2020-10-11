	function scroller(){
	    console.log('срабатывает скролл')
		if(document.getElementById('nameOfModal')!='none') {
			let inputWithNumberPage
			let list
			let window
			const request = new XMLHttpRequest()
			let url
   			const percentDistanceForDownloadingData = 95
			if(document.getElementById('nameOfModal').value=='modalFollowers') {
				inputWithNumberPage = document.getElementById('numberPageFollowers')
				list = document.getElementById('ListOfFollowers')
				window = document.getElementById('ListOfFollowers')
				url = 'http://localhost:8080'+document.location.pathname+'followers/'+document.getElementById('numberPageFollowers').value;
			}
			else if(document.getElementById('nameOfModal').value=='modalFollows'){
				inputWithNumberPage = document.getElementById('numberPageFollows')
				list = document.getElementById('ListOfFollows')
				window = document.getElementById('modalFollows')
				url = 'http://localhost:8080'+document.location.pathname+'follows/'+document.getElementById('numberPageFollows').value;
			}
			request.open('GET', url)
			request.setRequestHeader('Content-Type', 'application/x-www-form-url')
			console.log(window.scrollTop + ' > ' + (getMaxLengthScroll(window)/100*percentDistanceForDownloadingData))
    		if(window.scrollTop > (getMaxLengthScroll(window)/100*percentDistanceForDownloadingData)) {
    		    console.log('запрос отправлен!')
        		request.send()
        		window.onscroll = '';
    		}


    		request.addEventListener("readystatechange", () => {
        		if (request.readyState === 4 && request.status === 200) {
            		inputWithNumberPage.value = (Number.parseInt(inputWithNumberPage.value) + 1)
            		let data = JSON.parse(request.responseText).map(Object.values)
            		for(let i = 0; i < data.length; i++) {
                		let id = data[i][0]
                		let username = data[i][1]
                		let name = data[i][2]
                		let avatar = data[i][3]
                		list.innerHTML += '<fieldset class="user"><label>' + name + '</label><label>' + username + '</label></fieldset>'
            		}
            		window.onscroll = scroller;
        		}
        	else {
            window.onscroll = scroller;
        	}
    		})
		}
	}

function getMaxLengthScroll(window) {
    return Math.round(Math.round(window.scrollHeight) - Math.round(window.clientHeight))
}

function readyUsers() {
    let inputWithNumberPage
    let list
    const request = new XMLHttpRequest()
    let url

    if(document.getElementById('nameOfModal').value==='modalFollowers') {
		inputWithNumberPage = document.getElementById('numberPageFollowers')
		list = document.getElementById('ListOfFollowers')
		window = document.getElementById('modalFollowers')
		url = 'http://localhost:8080'+document.location.pathname+'followers/'+document.getElementById('numberPageFollowers').value;
	}
	else if(document.getElementById('nameOfModal').value==='modalFollows'){
		inputWithNumberPage = document.getElementById('numberPageFollows')
		list = document.getElementById('ListOfFollows')
		window = document.getElementById('modalFollows')
		url = 'http://localhost:8080'+document.location.pathname+'follows/'+document.getElementById('numberPageFollowers').value;
	}
	request.open('GET', url)
    request.setRequestHeader('Content-Type', 'application/x-www-form-url')

    request.addEventListener("readystatechange", () => {
        if (request.readyState === 4 && request.status === 200) {
            let data = JSON.parse(request.responseText).map(Object.values)
            for(let i = 0; i < data.length; i++) {
                let id = data[i][0]
                let username = data[i][1]
                let name = data[i][2]
                let avatar = data[i][3]
                list.innerHTML += '<fieldset class="user"><label>' + name + '</label><label>' + username + '</label></fieldset>'
            }
            inputWithNumberPage.value = (Number.parseInt(inputWithNumberPage.value) + 1)
        }
    });
    request.send()
  }

document.addEventListener('onbeforeunload', event => {
    document.getElementById('numberPageFollows').value = ''
    document.getElementById('numberPageFollowers').value = ''
    document.getElementById('nameOfModal').value = 'none'
})




	function openFollowsModal() {
		const substrate = document.getElementById('substrate')
		const window = document.getElementById('modalFollows')
		substrate.style.display = 'block'
		window.style.display = 'block'
		document.getElementById('modalIsOpen').value = 1
		document.getElementById('nameOfModal').value = 'modalFollows'
		readyUsers()
	}

	function closeFollowsModal() {
		const substrate = document.getElementById('substrate')
		const window = document.getElementById('modalFollows')
		substrate.style.display = 'none'
		window.style.display = 'none'
		listOfFollows = document.getElementById('ListOfFollows').children;
		for(let i = listOfFollows.length-1; i >= 0; i--) {
		    listOfFollows[i].remove()
		}
		document.getElementById('modalIsOpen').value = 0
		document.getElementById('numberPageFollows').value = 0;
		document.getElementById('nameOfModal').value = "none"
	}

	function openFollowersModal() {
		const substrate = document.getElementById('substrate')
		const window = document.getElementById('modalFollowers')
		substrate.style.display = 'block'
		window.style.display = 'block'
		document.getElementById('modalIsOpen').value = 1
		document.getElementById('nameOfModal').value = 'modalFollowers'
		readyUsers()
	}

	function closeFollowersModal() {
		const substrate = document.getElementById('substrate')
		const window = document.getElementById('modalFollowers')
		substrate.style.display = 'none'
		window.style.display = 'none'
		listOfFollowers = document.getElementById('ListOfFollowers').children

		for(let i = listOfFollowers.length-1; i >= 0; i--) {
		    listOfFollowers[i].remove()
		}
		document.getElementById('modalIsOpen').value = 0
		document.getElementById('numberPageFollowers').value = 0;
		document.getElementById('nameOfModal').value = "none"
	}

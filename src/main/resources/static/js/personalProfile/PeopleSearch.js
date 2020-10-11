function searchInFocus(){
    if(document.getElementById('search').value !== "") {
        document.getElementById('searchModal').style.display = 'grid'
        document.getElementById('transparent-substrate').style.display = 'block'
    }
}

function onKeyUp() {
    const searchModal = document.getElementById('searchModal')
    searchModal.style.display = 'none'
    let searchIsChanged = false
    let search = document.getElementById('search')
    let memorySearch

    if(search.value != "") {
        let promise = new Promise((resolve, reject) => {

              memorySearch = search.value
              setTimeout(() => {
                  resolve("timeIsEnd");
              }, 1000);
        });

        promise
            .then(
                result => {
                    console.log('сраниваю... ' + memorySearch + ' и ' + search.value)
                    if(memorySearch === search.value) {
                      let list = document.getElementById('ListOfUsers')
                      let url = 'http://localhost:8080'+document.location.pathname+'users/'+search.value;


	    	                    for(let i = list.children.length-1; i >= 0; i--) {
	    	                        list.children[i].remove()
	    	                    }

                      searchModal.style.display = 'grid'
                      document.getElementById('transparent-substrate').style.display = 'block'


                      const request = new XMLHttpRequest()
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
                      }
                  });
                  request.send()

                    }
            },
            error => {
                console.log('rejected: ' + error)
            }
    );
  }
}
class TextAreaButtonDiv {
    static minHeight = 38
    static maxHeight = 78
    static minWidth = 80
    static maxWidth = 100
}

class TextAreaButton {
    static minMarginLeft = 0
    static maxMarginLeft = 20
    static minMarginTop = 5
    static maxMarginTop = 25
}

class TextArea {
    static minHeight = 40
    static maxHeight = 80
}

function createMarkup() {
    let textArea = document.getElementById('ta')
    let textAreaButton = document.getElementById('textAreaButton')
    let textAreaButtonDiv = document.getElementById('textAreaButtonDiv')
    let imageDiv = document.getElementById('imageDiv')



    let lengthTextArea = textArea.value.length


    if(lengthTextArea == 0) {
        textAreaButton.style.display = 'none'
        imageDiv.style.display = 'block'
    }

    if(lengthTextArea > 0) {
        textAreaButton.style.display = 'block'
        imageDiv.style.display = 'none'
    }

    if(lengthTextArea > 104) {
        textAreaButtonDiv.style.height = TextAreaButtonDiv.maxHeight
        textAreaButtonDiv.style.width = TextAreaButtonDiv.maxWidth
        textAreaButton.style.marginTop = TextAreaButton.maxMarginTop
        textAreaButton.style.marginLeft = TextAreaButton.maxMarginLeft
        textArea.style.height = TextArea.maxHeight
        textArea.style.overflowY = 'scroll'
    }
    else {
        textAreaButtonDiv.style.height = TextAreaButtonDiv.minHeight
        textAreaButtonDiv.style.width = TextAreaButtonDiv.minWidth
        textAreaButton.style.marginTop = TextAreaButton.minMarginTop
        textAreaButton.style.marginLeft = TextAreaButton.minMarginLeft
        textArea.style.height = TextArea.minHeight
        textArea.style.overflowY = 'hidden'
    }


}

document.getElementById('ta').addEventListener('keyup', function(){
    createMarkup()
})
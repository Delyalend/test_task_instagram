class WebSocketMessager {

    #client = null

    constructor(client) {
        this.#client = client
    }

    static getChats(url) {
        this.#client.send(url)
    }

    //"/app/chat.sendMessage"
    static sendMessage(message, url) {
        client.send(url, {}, JSON.stringify(message));
    }


}

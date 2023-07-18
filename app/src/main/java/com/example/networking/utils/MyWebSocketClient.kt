package com.example.networking.utils

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI


class MyWebSocketClient(serverUri: URI) : WebSocketClient(serverUri) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        // WebSocket connection opened
    }

    override fun onMessage(message: String?) {
        // Received a message from the server
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        // WebSocket connection closed
    }

    override fun onError(ex: Exception?) {
        // An error occurred
    }
}
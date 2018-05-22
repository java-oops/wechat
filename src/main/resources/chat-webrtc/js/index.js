// Chat (WebRTC)
//
// Currently supported in Chrome and Firefox only.
// WebRTC support is ultra basic at the moment - send/receive // in current window only.
// Design based on Bookmarks app by // Eyal Zuri - http://dribbble.com/shots/1261465-Bookmarks-app-gif
//
// The below JS has been adapted from this excellent RTCDataChannel demo
// http://simpl.info/rtcdatachannel/

var chatThread = document.querySelector('.chat-thread');

function addMessage(user, text) {

    var chatNewThread = document.createElement('li'),
        chatNewMessage = document.createTextNode(text);

    chatNewThread.appendChild(chatNewMessage);
    if(user=="me"){
        chatNewThread.setAttribute("class","me")
    }else{
        chatNewThread.setAttribute("class","you")
    }
    chatThread.appendChild(chatNewThread);
    chatThread.scrollTop = chatThread.scrollHeight;
}
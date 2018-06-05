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

    // chatNewThread.appendChild(chatNewMessage);
    chatNewThread.innerHTML=text;
    if(user=="me"){
        chatNewThread.setAttribute("class","me")
    }else{
        chatNewThread.setAttribute("class","you")
    }
    chatThread.appendChild(chatNewThread);
    chatThread.scrollTop = chatThread.scrollHeight;
    chatThread.scrollIntoView();
    MathJax.Hub.Queue(["Typeset", MathJax.Hub]);
}

function addImageMessage(user,url) {

    var chatNewThread = document.createElement('li');

    var bigImg = new Image;  //创建一个img元素
    bigImg.src='http://123.207.235.153:3000/file-upload/4gEQrzFBAodNes5aJ/%E6%9C%AA%E6%A0%87%E9%A2%98-1.png';
    bigImg.
    chatNewThread.appendChild(bigImg);

    if(user=="me"){
        chatNewThread.setAttribute("class","me")
    }else{
        chatNewThread.setAttribute("class","you")
    }
    chatThread.appendChild(chatNewThread);
    chatThread.scrollTop = chatThread.scrollHeight;
    chatThread.scrollIntoView();
    MathJax.Hub.Queue(["Typeset", MathJax.Hub]);
}



function cleanMessage() {

    chatThread.innerHTML="";

}

function toBottom(){
    chatThread.scrollTop = chatThread.scrollHeight;
    chatThread.scrollIntoView();
}

window.onload=function (){

    javaapp.init();

}
window.onresize=function () {
    chatThread.scrollTop = chatThread.scrollHeight;
    chatThread.scrollIntoView();
}

var self =  require("sdk/self");
let {  Ci, Cu, Cc, Cr } = require('chrome');

//let filterLst = ["jpg", "png", "gif", "css", "mp3", "mp4", "flv", "ico"];
let filterLst = [];
let acceptLst = [];
var userAgent = null;

var server = "127.0.0.1";
var port = 7070;

function getSuffix(url) {
    let urls = url.split(/\?/);
    let suffix = urls[0];
    let reg = /\.(\w+)$/;
    let r = reg.exec(suffix);
    if (r) {
        suffix = r[1];
        return suffix;
    }
    return null;
}

let testObserver = {
    observe : function(aSubject, aTopic, aData) {
        if ("http-on-modify-request" != aTopic) {
            return;
        } 
        aSubject.QueryInterface(Ci.nsIHttpChannel);
        var url = aSubject.URI.spec;
        var suffix = getSuffix(url);
        if(acceptLst.length > 0) {
        	var reg = ""; 
        	for(var i = 0; i < acceptLst.length; i ++) {
        		reg += acceptLst[i];
        		reg += "|";
        	}
        	reg = reg.substring(0, reg.length-1);
        	let patt1=new RegExp(reg);
            if(patt1.test(aSubject.getRequestHeader("Accept"))) {
            	aSubject.cancel(Cr.NS_BINDING_ABORTED);
            }
        }
       
        if (userAgent != null) {
			aSubject.setRequestHeader("User-Agent", userAgent, false);
		}
        if(filterLst.length > 0) {
			 for (var i = 0; i < filterLst.length; i ++) {
		        if (suffix == filterLst[i]) {
		            //console.log("block: " + suffix);
		            aSubject.cancel(Cr.NS_BINDING_ABORTED);
		        }
		    }
        }
    }
};
let observerService = Cc["@mozilla.org/observer-service;1"].getService(Ci.nsIObserverService);  
observerService.addObserver(testObserver, "http-on-modify-request", false);

function setFliters(filters) {
   for(var i in filters) {
   	  filterLst[i] = filters[i];
   }
}

function setAccept(accepts) {
	for(var i in accepts) {
		acceptLst[i] = accepts[i];
	}
}

function setUserAgent(content) {
	userAgent = content;
}

var prefsvc = require("sdk/preferences/service");
//prefsvc.set("network.proxy.http", server);
//prefsvc.set("network.proxy.http_port", port);
//prefsvc.set("network.proxy.ssl", server);
//prefsvc.set("network.proxy.ssl_port", port);
//prefsvc.set("network.proxy.socks", server);
//prefsvc.set("network.proxy.socks_port", port);
//prefsvc.set("network.proxy.type", 1);

function setProxy(type, host, port) {
 switch (type) {
     case "http":
         prefsvc.set("network.proxy.http", host);
         prefsvc.set("network.proxy.http_port", port);
         prefsvc.set("network.proxy.ssl", host);
         prefsvc.set("network.proxy.ssl_port", port);
         break;
     case "socks":
         prefsvc.set("network.proxy.socks", server);
         prefsvc.set("network.proxy.socks_port", port);
         break;
     default:
 }
 prefsvc.set("network.proxy.type", 1);
}

require("sdk/tabs").on("ready", function (tab) {
    console.log("tab ready");
    var worker = tab.attach({
        contentScriptFile: self.data.url("invoke.js")
    });
    worker.port.on("setFliters", function(data) {
        console.log("setFliters: " + (data.fliters));
        setFliters(data.fliters);
    });
    worker.port.on("setUserAgent", function(data) {
        console.log("setUserAgent: " + (data.userAgent));
        setUserAgent(data.userAgent);
    });
    worker.port.on("setProxy", function(data) {
        console.log("setProxy: " + (data.type + data.host + data.port));
        setProxy(data.type, data.host, data.port);
    });
    worker.port.on("setAccept", function(data) {
        console.log("setAccept: " + (data.accepts));
        setAccept(data.accepts);
    });
});

var self =  require("sdk/self");
var {  Ci, Cu, Cc, Cr } = require('chrome');

//var filterLst = ["jpg", "png", "gif", "css", "mp3", "mp4", "flv", "ico"];
var filterLst = [];
var acceptLst = [];
var hostLst = [];
var userAgent = null;

var server = "127.0.0.1";
var port = 7070;

function getSuffix(url) {
    var urls = url.split(/\?/);
    var suffix = urls[0];
    var reg = /\.(\w+)$/;
    var r = reg.exec(suffix);
    if (r) {
        suffix = r[1];
        return suffix;
    }
    return null;
}

var testObserver = {
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
        	var patt1=new RegExp(reg);
            if(patt1.test(aSubject.getRequestHeader("Accept"))) {
            	aSubject.cancel(Cr.NS_BINDING_ABORTED);
            }
        }
        if(hostLst.length > 0) {
        	for(var i = 0; i < hostLst.length; i ++) {
        		if(aSubject.getRequestHeader("Host") == hostLst[i]) {
        			aSubject.cancel(Cr.NS_BINDING_ABORTED);
        		}
        	}
        }
        if (userAgent != null) {
			aSubject.setRequestHeader("User-Agent", userAgent, false);
		}
        if(filterLst.length > 0) {
			 for (var i = 0; i < filterLst.length; i++) {
		        if (suffix == filterLst[i]) {
		            //console.log("block: " + suffix);
		            aSubject.cancel(Cr.NS_BINDING_ABORTED);
		        }
		    }
        }
    }
};
var observerService = Cc["@mozilla.org/observer-service;1"].getService(Ci.nsIObserverService);  
observerService.addObserver(testObserver, "http-on-modify-request", false);

function setSuffixFilters(filters) {
   for(var i in filters) {
   	  filterLst[i] = filters[i];
   }
}

function setAcceptFilters(accepts) {
	for(var i in accepts) {
		acceptLst[i] = accepts[i];
	}
}

function setUserAgent(content) {
	userAgent = content;
}

function setHostFilters (hosts) {
	for(var i in hosts) {
		hostLst[i] = hosts[i];
	}
}

var prefsvc = require("sdk/preferences/service");
//prefsvc.set("network.cookie.cookieBehavior", 2);
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
//         prefsvc.set("network.proxy.ssl", host);
//         prefsvc.set("network.proxy.ssl_port", port);
         break;
     case "socks":
         prefsvc.set("network.proxy.socks", server);
         prefsvc.set("network.proxy.socks_port", port);
         break;
     default:
 }
 prefsvc.set("network.proxy.type", 1);
}

var cookieManager = Cc["@mozilla.org/cookiemanager;1"].getService(Ci.nsICookieManager);

function deleteCookies() {
    var iter = cookieManager.enumerator;
    while (iter.hasMoreElements()) {
        var cookie = iter.getNext();
        if (cookie instanceof Ci.nsICookie) {
            cookieManager.remove(cookie.host, cookie.name, cookie.path, cookie.blocked);
        }
    }
}

require("sdk/tabs").on("ready", function (tab) {
    console.log("tab ready");
    var worker = tab.attach({
        contentScriptFile: self.data.url("invoke.js")
    });
    worker.port.on("setSuffixFilters", function(data) {
        console.log("setSuffixFilters: " + (data.filters));
        setSuffixFilters(data.filters);
    });
    worker.port.on("setUserAgent", function(data) {
        console.log("setUserAgent: " + (data.userAgent));
        setUserAgent(data.userAgent);
    });
    worker.port.on("setProxy", function(data) {
        console.log("setProxy: " + (data.type + data.host + data.port));
        setProxy(data.type, data.host, data.port);
    });
    worker.port.on("setAcceptFilters", function(data) {
        console.log("setAcceptFilters: " + (data.accepts));
        setAcceptFilters(data.accepts);
    });
    worker.port.on("setHostFilters", function(data) {
        console.log("setHostFilters: " + (data.hosts));
        setHostFilters(data.hosts);
    });
    worker.port.on("deleteCookies", function() {
        deleteCookies();
    });
});
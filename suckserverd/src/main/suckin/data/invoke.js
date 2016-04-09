
function invoke(method, data) {
    self.port.emit(method, data);
}
if (!unsafeWindow.invoke) {
    exportFunction(invoke, unsafeWindow, {defineAs: "invoke"});
}


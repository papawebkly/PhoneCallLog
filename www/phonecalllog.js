cordova.define("cordova-plugin-phonecalllog.phonecalllog", function(require, exports, module) {

var exec = require('cordova/exec');

/**
 * Constructor
 */
function PhoneCallLog() {
    this._callback;
}

PhoneCallLog.prototype.getPhoneCallInfo = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "PhoneCallLog", "PhoneCallLog", []);
};

var phoneCallLog = new PhoneCallLog();
module.exports = phoneCallLog;
// Make plugin work under window.plugins
if (!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.phoneCallLog) {
    window.plugins.phoneCallLog = phoneCallLog;
}
});

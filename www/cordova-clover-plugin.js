var exec = require('cordova/exec');

//exports.test = function (arg0, success, error) {
//    exec(success, error, 'clover-android-sdk-cordova-plugin', 'coolMethod', [arg0]);
//};

exports.helloWorld = function(success, error) {
  exec(success, error, 'CordovaCloverPlugin', 'test', []);
};

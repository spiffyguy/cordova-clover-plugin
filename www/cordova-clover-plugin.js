var exec = require('cordova/exec');

exports.startPayment = function(amount, success, error) {
  exec(success, error, 'CordovaCloverPlugin', 'startPayment', [amount]);
};


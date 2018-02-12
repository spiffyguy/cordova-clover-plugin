var exec = require('cordova/exec');

exports.startPayment = function(amount, bill_id, success, error) {
  exec(success, error, 'CordovaCloverPlugin', 'startPayment', [amount, bill_id]);
};


var exec = require("cordova/exec");

function IntentPlugin() {}

IntentPlugin.prototype.startActivity = function ({
  packageName,
  activitiName,
  bundle,
  onSuccess,
  onError,
}) {
  exec(
    function (res) {
      onSuccess(res);
    },
    function (err) {
      onError(err);
    },
    "IntentPlugin",
    "startActivity",
    [packageName, activitiName, bundle]
  );
};

module.exports = new IntentPlugin();

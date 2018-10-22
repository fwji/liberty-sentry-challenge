$("#startBtn").click(function() {
  initializeGame();
});

function initializeGame() {
  // To Do
  var user = $("#userName").val();
  console.log(user);
  if (user === "") {
    alert("Please enter a valid player name");
  } else {
    $.ajax({
      type: "POST",
      crossDomain: true,
      url: "http://localhost:9081/liberty-demo-game/gameapp/game/" + user,
      success: success,
      error: fail,
      dataType: "json"
    });
  }
}

function fail() {
  alert("Device not connected!");
}

function success() {
  console.log("game started!");
  window.location.replace("game.html");
}
(function() {
  var onLoad = function() {
    var formElement = document.getElementById("loginform");
    if (formElement != null) {
      var password = document.getElementById("password");
      var username = document.getElementById("username");
      var showpass = document.getElementById("showpass");

      if (showpass) {
        showpass.addEventListener("click", function() {
          password.setAttribute("type", password.type == "text" ? "password" : "text");
          showpass.checked = false;
        });
      }

      var validatepass = function() {
        if (username && username.value.trim() == "") {
          alert("Silakan masukkan nomor handphone Anda");
          return;
        }
        if (password.value.length < 8) {
          alert("Password minimal 8 karakter");
        } else {
          formElement.submit();
        }
      };
      document.getElementById("formbutton").addEventListener("click", validatepass);
    }
  };
  document.readyState != 'loading' ? onLoad() : document.addEventListener('DOMContentLoaded', onLoad);
})();

function redirect() {
  document.location = "index.htm";
}

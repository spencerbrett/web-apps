window.onload = function () {
  emc = new ErrorMessageControl(document.getElementById("creditCard"));
}

function ErrorMessageControl (cardNumber) {
  this.textbox = cardNumber;
  this.layer = null;
  this.createMessageBox();
}

function validateCard (form) {
  var textbox = form.creditCard;
  if (textbox.value.length == 0) {
    emc.showErrorMessage("Please input a card number");
    return false;
  }
  if (textbox.value.length < 12 || textbox.value.length > 18) {
    emc.showErrorMessage("Invalid card number lenght!");
    return false;
  }
  if (/^\d+$/.test(textbox.value)) {
    return true;
  } else {
    emc.showErrorMessage("Card number must contain only digits!");
    return false;
  }
}

ErrorMessageControl.prototype.createMessageBox = function () {
  this.layer = document.createElement("div");
  this.layer.className = "customErrorMessage";
  this.layer.style.visibility = "hidden";
  this.layer.style.width = this.textbox.offsetWidth;
  document.body.appendChild(this.layer);
}

ErrorMessageControl.prototype.showErrorMessage = function (message) {
    this.layer.innerHTML = "";
    var oDiv = document.createElement("div");
    oDiv.appendChild(document.createTextNode(message));
    this.layer.appendChild(oDiv);
    var rect = this.textbox.getBoundingClientRect();
    this.layer.style.left = rect.left + "px";
    this.layer.style.top = (rect.top+this.textbox.offsetHeight) + "px";
    this.layer.style.visibility = "visible";
    this.textbox.focus();
}

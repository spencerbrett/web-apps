function AutoSuggestControl(oTextbox, oProvider) {
    this.provider = oProvider;
    this.textbox = oTextbox;
    this.init();
};

AutoSuggestControl.prototype.selectRange = function (iStart, iLength) {
    this.textbox.setSelectionRange(iStart, iLength);
    this.textbox.focus(); 
};

AutoSuggestControl.prototype.typeAhead = function (sSuggestion) {
        var iLen = this.textbox.value.length; 
        this.textbox.value = sSuggestion; 
        this.selectRange(iLen, sSuggestion.length);
};

AutoSuggestControl.prototype.autosuggest = function (aSuggestions) {
    if (aSuggestions.length > 0) {
        this.typeAhead(aSuggestions[0]);
    }
};

AutoSuggestControl.prototype.handleKeyUp = function (oEvent) {
     var iKeyCode = oEvent.keyCode;

     if (iKeyCode < 32 || (iKeyCode >= 33 && iKeyCode <= 46) || (iKeyCode >= 112 && iKeyCode <= 123)) {
        //ignore
    } else {
        this.provider.requestSuggestions(this);
    }
};

AutoSuggestControl.prototype.init = function () {
    var oThis = this;
    this.textbox.onkeyup = oThis.handleKeyUp(oEvent);
};

function SuggestionProvider() {
    this.xmlHttp = new XMLHttpRequest();
};

SuggestionProvider.prototype.requestSuggestions = function (oAutoSuggestControl) {
    var sTextboxValue = oAutoSuggestControl.textbox.value;

    if (sTextboxValue.length > 0){
        var request = "/eBay/suggest?query="+encodeURI(sTextboxValue);

        this.xmlHttp.open("GET", request); 
        this.xmlHttp.onreadystatechange = this.showSuggestion; 
        this.xmlHttp.send(null);
    }
}

SuggestionProvider.prototype.showSuggestions = function () {
    if (xmlHttp.readyState == 4) {
        // get the CompleteSuggestion elements from the response
        var s = this.xmlHttp.responseXML.getElementsByTagName('CompleteSuggestion');

        var aSuggestions = [];
        for (var i=0; i < s.length; i++) { 
            aSuggestions.push(s[i].childNodes[0].getAttribute("data"));
        } 
        oAutoSuggestControl.autosuggest(aSuggestions);
    } 
};

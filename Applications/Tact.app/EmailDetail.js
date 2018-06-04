function loadImage(cidKey, fileName) {
  var imgs = document.getElementsByTagName('img');
  for (var i=0; i<imgs.length; i++) {
    var img = imgs[i];
    var src = img.getAttribute('src');
    if (src == cidKey) {
      img.src = fileName;
    }
  }
}

function initEventHandlers() {
  disableImageSelection();
  document.body.addEventListener('touchcancel', touchCancel, false);
  document.body.addEventListener('touchstart', touchStart, false);
  document.body.addEventListener('touchmove', touchMove, false);
  document.body.addEventListener('touchend', touchEnd, false);
}

function disableImageSelection() {
  var imgs = document.getElementsByTagName('img');
  for (var count = 0; count < imgs.length; count++) {
			var img = imgs[count];
			img.style.webkitUserSelect='none';
			img.style.webkitTouchCallout='none';
  }
}

var touch = false;
function touchStart(event) {
  if (event.target.tagName != 'IMG') {
    return;
  }
  var identifier = event.touches[0].identifier;
  touch = {};
  touch.startTime = new Date().getTime();
  touch.target = event.target;
  touch.identifier = identifier;
  touch.cancelBubble = true;
  window.setTimeout(function() {
    if (touch && touch.identifier == identifier) {
      var filePath = touch.target.src;
      filePath = filePath.replace('file://', '');
      window.location='img:' + filePath;
      touch = false;
    }
  }, 500);
}

function touchCancel(event) {
  touch = false;
}

function touchEnd(event) {
  if (!touch) {
    return;
  }
  touch = false;
}

function touchMove(event) {
  if (!touch) {
    return;
  }
  var currentEl = document.elementFromPoint(event.changedTouches[0].clientX, event.changedTouches[0].clientY);
  if (touch.target != currentEl) {
    touch = false;
  }
}

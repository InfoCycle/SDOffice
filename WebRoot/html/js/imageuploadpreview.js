/**
 * @fileoverview
 * JavaScript Image Upload Preview.
 * Tested and compatible with IE6, IE7, IE8, Firefox 3.
 * 
 * @author Hedger Wang (hedgerwang@gmail.com)
 *
 */

/**
 * @constructor
 * @param {HTMLInputElement|String} input
 * @param {Function?} opt_onSuccess
 * @param {Function?} opt_onFail
 */
function ImageUploadPreview(input, opt_onSuccess, opt_onFail) {
  this.construct(input, opt_onSuccess, opt_onFail);
}

/**
 * Empty image that shows either for Http:// or Https://.
 * @define {String}
 */
ImageUploadPreview.BLANK_IMAGE_SRC = '';


/**
 * @define {RegExp}
 */
ImageUploadPreview.BASE64_IMG_URL_PATTERN =
/^data:image\/((png)|(gif)|(jpg)|(jpeg)|(bmp));base64/i;


/**
 * @type {HTMLInputElement}
 * @private
 */
ImageUploadPreview.prototype.input_ = null;


/**
 * @type {Function}
 * @private
 */
ImageUploadPreview.prototype.onChangeHandler_ = null;


/**
 * @type {Function}
 * @private
 */
ImageUploadPreview.prototype.onPreviewSuccessHandler_ = null;


/**
 * @type {Function}
 * @private
 */
ImageUploadPreview.prototype.onPreviewFailHandler_ = null;


/**
 * @type {HTMLImageElement}
 * @private
 */
ImageUploadPreview.prototype.image_ = null;


/**
 * @private
 * @type {boolean}
 */
ImageUploadPreview.prototype.isCompatible_ = null;


/**
 * @private
 * @type {Number}
 */
ImageUploadPreview.prototype.maxWidth_ = 200;


/**
 * @private
 * @type {Number}
 */
ImageUploadPreview.prototype.maxHeight_ = 200;


/**
 * @param {HTMLInputElement|String} input
 * @param {Function?} opt_onSuccess
 * @param {Function?} opt_onFail 
 * @public
 */
ImageUploadPreview.prototype.construct =
function(input, opt_onSuccess, opt_onFail) {
  if (typeof input == 'string') {
    input = document.getElementById(input);
  }
  this.onPreviewFailHandler_ = opt_onFail;
  this.onPreviewSuccessHandler_ = opt_onSuccess;
  this.input_ = input;
  this.bindEvents_();
  this.image_ = this.createImage_();
};


/**
 * @public
 */
ImageUploadPreview.prototype.dispose = function() {
  var fn = this.onChangeHandler_;

  // Already disposed.
  if (!fn) return;

  var el = this.input_;

  if (el.addEventListener) {
    el.removeEventListener('change', fn, false);
  } else if (el.attachEvent) {
    el.detachEvent('onchange', fn);
  }

  this.onChangeHandler_ = null;
  this.input_ = null;
  this.image_ = null;
};

/**
 * @public
 */
ImageUploadPreview.prototype.preview = function() {
  var that = this;

  var onLoad = function(imgInfo) {
    // Do thing now, maybe do something later.
    that.maybeCallFunction_(that.onPreviewSuccessHandler_, imgInfo);
  };

  var onError = function(src) {
    if (!tryLoad()) {
      that.showEmptyImage_();
      that.maybeCallFunction_(that.onPreviewFailHandler_, src);
    }
  };

  var loadMethods = [
    this.maybeShowImageWithDataUri_,
    this.maybeShowImageByPath_
  ];

  var tryLoad = function() {
    if (!loadMethods.length) {
      return false;
    }
    var fn = loadMethods.shift();
    fn.call(that, onLoad, onError);
    return true;
  };
  tryLoad();
};


/**
 * @public
 * @return {HTMLImageElement}
 */
ImageUploadPreview.prototype.getImageElement = function() {
  return this.image_;
};


/**
 * @public
 * @return {HTMLInputElement}
 */
ImageUploadPreview.prototype.getInputElement = function() {
  return this.input_;
};


/**
 * @public
 * @param {Number} maxW
 * @param {Number} maxH
 */
ImageUploadPreview.prototype.setMaxImageSize = function(maxW, maxH) {
  this.maxHeight_ = isNaN(maxH) ? 10000 : maxH;
  this.maxWidth_ = isNaN(maxW) ? 10000 : maxW;
};


/**
 * @private
 * @return {HTMLImageElement}
 */
ImageUploadPreview.prototype.createImage_ = function() {
  var doc = this.input_.document || this.input_.ownerDocument;
  var img = doc.createElement('img');
  img.src = ImageUploadPreview.BLANK_IMAGE_SRC;
  img.width = 0;
  img.height = 0;
  this.input_.parentNode.insertBefore(img, this.input_.nextSibling || null);
  return img;
};


/**
 * @private
 */
ImageUploadPreview.prototype.bindEvents_ = function() {
  var that = this;

  var fn = this.onChangeHandler_ = function(e) {
    e = e || window.event;

    if (!e.target && e.srcElement) {
      e.target = e.srcElement;
    }

    that.handleOnchange_.call(that, e);
  };

  var el = this.input_;

  if (el.addEventListener) {
    el.addEventListener('change', fn, false);
  } else if (el.attachEvent) {
    el.attachEvent('onchange', fn);
  }
};


/**
 * @param {Event} e
 * @private
 */
ImageUploadPreview.prototype.handleOnchange_ = function(e) {
  this.preview();
};


/**
 * @private
 */
ImageUploadPreview.prototype.showEmptyImage_ = function() {
  this.showImage_(ImageUploadPreview.BLANK_IMAGE_SRC, 0, 0)
};


/**
 * @private
 * @param {string} src
 * @param {number} w
 * @param {number} h
 */
ImageUploadPreview.prototype.showImage_ = function(src, w, h) {

  if (w > h) {
    if (w > this.maxWidth_) {
      h = h * this.maxWidth_ / w;
      w = this.maxWidth_;
    }
  } else {
    if (h > this.maxHeight_) {
      w = w * this.maxHeight_ / h;
      h = this.maxHeight_;
    }
  }

  var img = this.image_;
  img.src = src;

  var imgStyle = img.style;
  imgStyle.maxHeight = this.maxHeight_ + 'px';
  imgStyle.maxWidth = this.maxWidth_ + 'px';
  imgStyle.width = (w >= 0) ? Math.round(w) + 'px' : 'auto';
  imgStyle.height = (h >= 0) ? Math.round(h) + 'px' : 'auto';
};


/**
 * @param {Function?} fn
 * @param {Object?} var_args
 */
ImageUploadPreview.prototype.maybeCallFunction_ = function(fn, var_args) {
  if (typeof fn != 'function') return;
  var_args = Array.prototype.slice.call(arguments, 1);
  fn.apply(this, var_args);
};


/**
 * Note: Only Firefox 3 can do file.getAsDataURL() by 6/1/2009.
 * See {@link https://developer.mozilla.org/En/NsIDOMFile}.
 * @private
 * @param {Function?} opt_onload
 * @param {Function?} opt_onerror
 */
ImageUploadPreview.prototype.maybeShowImageWithDataUri_ =
function(opt_onload, opt_onerror) {
  var el = this.input_;
  var file = el.files && el.files[0];
  var src;
  var fileName = el.value;

  // Check if we can access the serialized file via getAsDataURL().
  if ((file && file.getAsDataURL) &&
      (src = file.getAsDataURL()) &&
      (ImageUploadPreview.BASE64_IMG_URL_PATTERN.test(src))) {
    var that = this;
    var img = this.image_;


    if ('naturalWidth' in this.image_) {
      // Firefox has naturalWidth.
      this.image_.src =src;

      setTimeout(function() {
        that.showImage_(src, img.naturalWidth, img.naturalHeight);
        that.maybeCallFunction_(opt_onload, {
          width: img.naturalWidth,
          height: img.naturalHeight,
          fileName :fileName,
          fileSize: el.files[0].fileSize
        });
      }, 10);

    } else {
      // Use default CSS max-width / max-height to render the size.
      that.showImage_(src, -1, -1);

      this.maybeCallFunction_(opt_onload, {
        fileName : fileName,
        width: img.offsetWidth,
        height: img.offsetHeight,
        fileSize: el.files[0].fileSize
      });
    }
  } else {
    this.maybeCallFunction_(opt_onerror, fileName);
  }
};


/**
 * Note: IE6 ~ IE8 can access image with local path. By 6/1/2009.
 *       However, this may still not work if the security setting changes.
 * @private
 * @param {Function?} opt_onload
 * @param {Function?} opt_onerror
 */
ImageUploadPreview.prototype.maybeShowImageByPath_ =
function(opt_onload, opt_onerror) {

  var that = this;
  var el = this.input_;
  var img = new Image();
  var timer;
  var fileName = el.value;

  var dispose = function() {
    if (timer) {
      window.clearInterval(timer);
    }
    img.onload = null;
    img.onerror = null;
    timer = null;
    dispose = null;
    img = null;
    that = null;
    checkIsComplete = null;
    handleError = null;
    handleComplete = null;
  };

  // Handle the case whether the File is not a image file or the path is not a
  // valid path to access.
  var handleError = function() {
    that.maybeCallFunction_(opt_onerror, el.value);
    dispose();
  };

  var handleComplete = function() {
    var w = img.width;
    var h = img.height;
    that.showImage_(img.src, w, h);
    that.maybeCallFunction_(opt_onload, {
      src:fileName,
      width: w,
      height: h,
      fileSize: img.fileSize // Note that FileSize is an IE's only attribute.
    });
    dispose();
  };

  var checkIsComplete = function(e) {
    e = e || window.event;
    var type = e && e.type;

    if (type == 'error') {
      // If the onError event is called.
      handleError();
    } else  if (img.complete || type == 'load') {
      // Sometimes IE does not fire "onload" event if the image was cahced.
      // So we have to check the "complete" state to know whether it's ready.
      if (!img.width || !img.height) {
        // Even it's not a real image, the onload event may still gets fired.
        // Check if its width or height is 0. If true, it's not a real image.
        handleError();
      } else {
        handleComplete();
      }
    }
  };

  img.onload = checkIsComplete;
  img.onerror = checkIsComplete;
  timer = window.setInterval(checkIsComplete, 50);

  // In IE, el.value us the full path of the file rather than just the fileName,
  // and we'd test if we can load image from this path.
  img.src = el.value;

};
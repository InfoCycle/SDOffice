(function ($) {
    // jQuery methods
    var methods = {
        init: function (options) {
            // default options
            var settings = {
                watermarkstyle: { "color": "grey", "font-style": "italic" },
                watermarktext:options.watermarktext!=null? options.watermarktext:'请输入...',
                autocompletesource: null,
                autocompleteselect: null,
                autocompletechange: null,
                allowfreetext: false,
                watermarkenabled: true
            }

            return this.each(function () {
                if (options) {
                    var $this = $(this);

                    // merge the input options with the default options
                    $.extend(settings, options);
                    if (!settings.regulartextstyle) {
                        var regulartextstyle = {
                            "color": $this.css("color"),
                            "font-style": $this.css("font-style")
                        }

                        settings.regulartextstyle = regulartextstyle;
                    }
                    settings.watermarktext = $.trim(settings.watermarktext);

                    // initiate the jQuery data for each selected dom object
                    // this may introduce redundent data sets, but it also allows
                    // individual configuration if needed later.
                    var instancedata = { options: $.extend({}, settings), data: {} };
                    $this.data("watermarkAutocomplete", instancedata);

                    // setup auto complete if the source functions are provided
                    // through the option Json object.
                    if (instancedata.options.autocompletesource) {
                        $(this).autocomplete({
                            source: instancedata.options.autocompletesource,
                            select: function (event, ui) {
                                if (instancedata.options.autocompleteselect) {
                                    instancedata.options.autocompleteselect(event, ui);
                                }

                                instancedata.data.autocompleteselection = ui.item;
                            },
                            change: function (event, ui) {
                                var $this = $(this);
                                if (instancedata.options.autocompletechange) {
                                    instancedata.options.autocompletechange(event, ui);
                                }
                                if (!instancedata.options.allowfreetext) {
                                    clearfreetext($this, instancedata.options, instancedata.data);
                                }
                            }
                        });
                    }

                    // null the selection when user type in anything
                    $this.bind("keyup.watermarkAutocomplete", function () {
                        var instancedata = $(this).data("watermarkAutocomplete");
                        instancedata.data.autocompleteselection = null;
                    });

                    // clear watermark when focus
                    $this.bind("focus.watermarkAutocomplete", function () {
                        var $this = $(this);
                        var instancedata = $this.data("watermarkAutocomplete");
                        if (instancedata.options.watermarkenabled) {
                            clearwatermark($this, instancedata.options);
                        }
                    });

                    // apply watermark when lose focus
                    $this.bind("blur.watermarkAutocomplete", function () {
                        var $this = $(this);
                        var instancedata = $this.data("watermarkAutocomplete");
                        if (instancedata.options.watermarkenabled) {
                            applywatermark($this, instancedata.options);
                        }
                    })

                    // Set the initial watermark
                    if (instancedata.options.watermarkenabled) {
                        applywatermark($this, instancedata.options);
                    }
                }
            });
        },

        enablewatermark: function (enable) {
            return this.each(function () {
                var $this = $(this);
                var instancedata = $this.data("watermarkAutocomplete");
                instancedata.options.watermarkenabled = enable;

                if (enable) {
                    applywatermark($this, instancedata.options);
                }
                else {
                    clearwatermark($this, instancedata.options);
                }
            });
        },

        allowfreetext: function (allow) {
            return this.each(function () {
                var $this = $(this);
                var instancedata = $this.data("watermarkAutocomplete");

                instancedata.options.allowfreetext = allow;
                if (!instancedata.options.allowfreetext) {
                    clearfreetext($this, instancedata.options, instancedata.data);
                }
            });
        },

        destroy: function () {
            return this.each(function () {
                var $this = $(this);
                var instancedata = $this.data("watermarkAutocomplete");
                clearwatermark($this, instancedata.options);
                $this.removeData("watermarkAutocomplete");
                $this.unbind(".watermarkAutocomplete");
                $this.autocomplete("destroy");
            });
        }
    };

    // The jQuery entry point
    $.fn.watermarkAutocomplete = function (method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === "object" || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error("Method " + method + "does not exist on jQuery.watermarkAutocomplete");
        }
    };

    // utility functions
    var applywatermark = function ($this, options) {
        var text = $.trim($this.val());
        if ((text == "") || (text == options.watermarktext)) {
            $this.css(options.watermarkstyle);
            $this.val(options.watermarktext);
        }
    };

    var clearwatermark = function ($this, options) {
        var text = $.trim($this.val());
        if (text == options.watermarktext) {
            $this.val("");
        }
        $this.css(options.regulartextstyle);
    };

    var clearfreetext = function ($this, options, data) {
        if (!data.autocompleteselection) {
            //$this.val("");
            if (options.watermarkenabled) {
                applywatermark($this, options);
            }
        }
    };

})(jQuery);
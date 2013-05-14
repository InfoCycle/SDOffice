/*
 * Magic ComboBox v2.0
 * author: Mac_J
 * need core.js
 */
mac.combo = function(self, cfg) {
	self.config = cfg = $.extend({
		height : 24,
		maxkLen : 20,
		comma : ',',
		name : self.prop('name')
	}, cfg);
	self.selected = cfg.selected || [];
	var cols = cfg.cols;
	var cw = cfg.width, ch = cfg.height;
	var hd = $('<div class="head"><div>').width(cw).height(ch);
	var tf = $('<input type="text" />').height(ch);
	var tw = $('<div class="left"></div>').css('margin-right', ch).height(
			ch + 2);
	tf.prop({
		name : cfg.name,
		maxLength : cfg.maxLen
	});
	var btn = $('<div class="btn"></div>');
	var ico = $('<span class="icon icon-triangle-1-s"></span>');
	ico.css('margin', Math.floor(ch / 2 - 8)).appendTo(btn);
	btn.height(ch).width(ch).css('margin-top', -ch - 2);
	tw.append(tf);
	hd.append(tw).append(btn);
	var bd = $('<div class="body hidden"></div>');
	if (cfg.boxHeight)
		bd.height(cfg.boxHeight);
	if (cfg.boxEl) {
		bd.append(cfg.boxEl);
		bd.mousedown(function() {
			return false;
		});
	}
	self.append(hd).append(bd);
	btn.click(function() {
		if (self.isOpen) {
			self.close();
		} else {
			self.open();
		}
		return false;
	});
	if (cfg.readOnly) {
		tf.attr('readonly', 'readonly');
		tf.click(function() {
			btn.click();
			return false;
		});
	}
	self.close = function() {
		self.isOpen = false;
		bd.hide();
	}
	tf.change(function() {
		if (!tf.val())
			self.select([]);
	}).blur(self.close);
	self.open = function() {
		self.isOpen = true;
		var w = cfg.boxWidth || self.width();
		var p = self.position();
		var bt = cfg.boxTop, bl = cfg.boxLeft;
		bt = (bt || bt == 0) ? bt : (cfg.height + 2);
		bl = (bl || bl == 0) ? bl : 1;
		bd.css({
			top : p.top + bt,
			left : p.left + bl
		});
		bd.width(Math.max(w, cfg.boxMinWidth || w)).show();
		if (cfg.onOpen)
			cfg.onOpen();
		$(document).one('mousedown', self.close);
	}
	self.select = function(dd) {
		if (!$.isArray(dd))
			dd = dd ? (dd + '').split(cfg.comma) : [];
		if (!cfg.multiSelect && dd.length > 1)
			dd = [ dd[0] ];
		var dv = [], sd = [], sk = [], df = cfg.displayField;
		if (cfg.boxEl) {
			$.each(dd, function(n, d) {
				dv.push(d[df]);
			});
		} else {
			dd = $.map(dd, function(c) {
				return c + '';
			});
			bd.find('.tr').each(function(n, c) {
				var a = $(c), k = a.attr('name');
				var d = self.data[k], sc = 'selected';
				a.removeClass(sc);
				if (dd.length > 0) {
					if ($.inArray(k, dd) > -1) {
						dv.push(d[df]);
						sk.push(k);
						sd.push(d);
						a.addClass(sc);
					}
				}
			});
		}
		self.selected = sk;
		dv = self.displayValue = dv.join(cfg.comma);
		tf.val(dv);
		if (cfg.onSelect)
			cfg.onSelect(self, sk, sd);
		return self;
	}
	self.loadData = function(dd) {
		if (cfg.boxEl) {
			cfg.boxEl.loadData(dd);
			return;
		}
		bd.empty();
		if (!dd || !dd.length)
			return;
		self.data = {};
		var mc = cols && cols.length;
		$.each(dd, function(i, r) {
			var kf = cfg.keyField, df = cfg.displayField;
			var k = kf ? r[kf] : 'r' + i;
			var a = $('<div class="tr"></div>').attr('name', k);
			self.data[k] = r;
			if (mc) {
				$.each(cols, function(n, c) {
					if (c.hidden)
						return;
					var b = $('<div class="td"></div>');
					b.width(c.width);
					b.append(c.render ? c.render(r, a, self) : r[c.field]);
					a.append(b);
				});
			} else {
				var b = $('<div class="td"></div>');
				b.append(r[cfg.displayField]).width(bd.width());
				a.append(b);
			}
			var hc = 'hover';
			a.mousedown(function() {
				var sl = self.selected || [];
				var i = $.inArray(k, sl), b = i > -1;
				if (cfg.multiSelect) {
					b ? sl.splice(i, 1) : sl.push(k);
					self.select(sl);
					return false;
				} else {
					self.select(b ? [] : [ k ]);
				}
				bd.children('.tr').removeClass(hc);
			}).hover(function() {
				bd.children('.tr').removeClass(hc);
				$(this).addClass(hc);
			}, function() {
				$(this).removeClass(hc);
			});
			bd.append(a);
		});
		if (cfg.onLoad)
			cfg.onLoad(self, dd);
	}
	var ldr = cfg.loader;
	if (cfg.data) {
		self.loadData(cfg.data);
	} else if (ldr && ldr.url) {
		$.post(ldr.url, ldr.params, function(data) {
			var ro = mac.eval(data);
			if (ro.success) {
				ro = ro.data;
				self.loadData(ro.list);
			}
		});
	}
	return self;
}
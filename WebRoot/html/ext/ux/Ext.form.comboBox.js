// add RegExp.escape if it has not been already added
if ('function' !== typeof RegExp.escape) {
    RegExp.escape = function(s) {
        if ('string' !== typeof s) {
            return s;
        }
        // Note: if pasting from forum, precede ]/\ with backslash manually
        return s.replace(/([.*+?^=!:${}()|[\]\/\\])/g, '\\$1');
    }; // eo function escape
}

// create namespace
Ext.ns('Ext.form');


Ext.form.MultiSelect = Ext.extend(Ext.form.ComboBox, {


    checkField: 'checked',
    separator: ',',

    initComponent: function() {

        // template with checkbox
        if (!this.tpl) {
            this.tpl =
                '<tpl for=".">' +
                '<div class="x-combo-list-item">' +
                    '<img src="' + Ext.BLANK_IMAGE_URL + '" ' + 'class="ux-MultiSelect-icon ux-MultiSelect-icon-{[values.' + this.checkField + '?"checked":"unchecked"' + ']}">' +
                    '<input type="checkbox" {[values.' + this.checkField + '?"checked=checked":""' + ']}>' +
                    '<span style="padding-left:5px;">{[values.' + this.displayField + ']}</span>' +
                 '</div>' +
                 '</tpl>';
        }

        // call parent
        Ext.form.MultiSelect.superclass.initComponent.apply(this, arguments);

        // install internal event handlers
        this.on({
            scope: this,
            beforequery: this.onBeforeQuery,
            blur: this.onRealBlur
        });

        // remove selection from input field
        this.onLoad = this.onLoad.createSequence(function() {
            if (this.el) {
                var v = this.el.dom.value;
                this.el.dom.value = '';
                this.el.dom.value = v;
            }
        });

    },
    initEvents: function() {
        Ext.form.MultiSelect.superclass.initEvents.apply(this, arguments);

        // disable default tab handling - does no good
        this.keyNav.tab = false;

    },
    /*
    清除值
    */
    clearValue: function() {
        this.value = '';
        this.setRawValue(this.value);
        this.store.clearFilter();
        this.store.each(function(r) {
            r.set(this.checkField, false);
        }, this);
        if (this.hiddenField) {
            this.hiddenField.value = '';
        }
        this.applyEmptyText();
    },

    getCheckedDisplay: function() {
        var re = new RegExp(this.separator, "g");
        return this.getCheckedValue(this.displayField).replace(re, this.separator + ' ');
    },

    getCheckedValue: function(field) {
        field = field || this.valueField;
        var c = [];

        // store may be filtered so get all records
        var snapshot = this.store.snapshot || this.store.data;

        snapshot.each(function(r) {
            if (r.get(this.checkField)) {
                c.push(r.get(field));
            }
        }, this);
        return c.join(this.separator);
    },

    onBeforeQuery: function(qe) {
        qe.query = qe.query.replace(new RegExp(RegExp.escape(this.getCheckedDisplay()) + '[ ' + this.separator + ']*'), '');
    },
    /*
    失去焦点
    */
    onRealBlur: function() {
        this.list.hide();
        var rv = this.getRawValue();
        var rva = rv.split(new RegExp(RegExp.escape(this.separator) + ' *'));
        var va = [];
        var snapshot = this.store.snapshot || this.store.data;

        // iterate through raw values and records and check/uncheck items
        Ext.each(rva, function(v) {
            snapshot.each(function(r) {
                if (v === r.get(this.displayField)) {
                    va.push(r.get(this.valueField));
                }
            }, this);
        }, this);
        this.setValue(va.join(this.separator));
        this.store.clearFilter();
    },

    /*
    选择下拉选项
    */
    onSelect: function(record, index) {
        if (this.fireEvent('beforeselect', this, record, index) !== false) {

            // toggle checked field
            record.set(this.checkField, !record.get(this.checkField));

            // display full list
            if (this.store.isFiltered()) {
                this.doQuery(this.allQuery);
            }

            // set (update) value and fire event
            this.setValue(this.getCheckedValue());
            this.fireEvent('select', this, record, index);
        }
    },

    /*
    设置元素值
    */
    setValue: function(v) {
        if (v) {
            v = '' + v;

            if (this.valueField) {
                this.store.clearFilter();
                this.store.each(function(r) {
                    var checked = !(!v.match('(^|' + this.separator + ')' + RegExp.escape(r.get(this.valueField)) + '(' + this.separator + '|$)'));
                    r.set(this.checkField, checked);
                }, this);
                this.value = this.getCheckedValue(); //设置验证值
                this.setRawValue(this.getCheckedDisplay()); //设置原始值
                if (this.hiddenField) {
                    this.hiddenField.value = this.value;
                }
            }
            else {
                this.value = v;
                this.setRawValue(v);
                if (this.hiddenField) {
                    this.hiddenField.value = v;
                }
            }
            if (this.el) {
                this.el.removeClass(this.emptyClass);
            }
        }
        else {
            this.clearValue();
        }
    },
   
    /*
    重写beforeBlur事件处理函数，修复该扩展对于extjs3.x版本的bug
    */
    beforeBlur:function(){},


    /*
    全选
    */
    selectAll: function() {
        this.store.each(function(record) {
            // toggle checked field
            record.set(this.checkField, true);
        }, this);

        //display full list
        this.doQuery(this.allQuery);
        this.setValue(this.getCheckedValue());
    },

    /*
    全不选
    */
    deselectAll: function() {
        this.clearValue();
    }


});   // eo extend

// register xtype
Ext.reg('multiSelect', Ext.form.MultiSelect);
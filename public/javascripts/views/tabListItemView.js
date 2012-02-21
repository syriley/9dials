var TabListItemView = Backbone.View.extend({

    tagName: "li",    

    render: function(eventName) {
    	var template = _.template($('#tab-list-item').html());
        $(this.el).html(template(this.model.toJSON()));
        return this;
    }
});
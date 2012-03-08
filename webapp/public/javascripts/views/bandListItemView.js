var BandListItemView = Backbone.View.extend({

    tagName: "li",    

    render: function(eventName) {
    	var template = _.template($('#band-list-item').html());
        $(this.el).html(template(this.model.toJSON()));
        return this;
    }
});
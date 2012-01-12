var TabListView = Backbone.View.extend({
   
    initialize: function() {
        this.model.bind("reset", this.render, this);
         this.el = $("#tabList");
    },

    render: function(eventName) {
        this.el.empty();
        _.each(this.model.models, function(band) {
            $(this.el).append(
                new TabListItemView({model: band}).render().el);
        }, this);
        return this;
    }
});
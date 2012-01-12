var BandListView = Backbone.View.extend({
   
    initialize: function() {
        this.model.bind("reset", this.render, this);
         this.el = $("#bandList");
    },

    render: function(eventName) {
        _.each(this.model.models, function(band) {
            $(this.el).append(
                new BandListItemView({model: band}).render().el);
        }, this);
        return this;
    }
});
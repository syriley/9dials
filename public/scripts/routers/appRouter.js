var AppRouter = Backbone.Router.extend({
    routes: {
        ""          : "list",
        "/tabs/list/:band" : "getTabsForBand"
    },
 
    list: function() {
        console.log('list');
        this.bandList = new BandCollection();
        this.bandListView = new BandListView({model: this.bandList});
        this.bandList.fetch();
    },

    getTabsForBand: function(band) {
    	console.log("getting tabs for " + band);
    	this.tabList = new TabCollection();
        this.tabListView = new TabListView({model: this.tabList});
        this.tabList.fetch({band: band});
    }
});
var AppRouter = Backbone.Router.extend({
    routes: {
        ""          						: "list",
        "/search/:search"					: "search",
        "/tabs/list/:band" 	: "getTabsForBand"
    },
 
    list: function() {
        console.log('list');
        this.bandList = new BandCollection();
        this.bandListView = new BandListView({model: this.bandList});
        this.bandList.fetch();
    },
    
    search: function(search) {
    	console.log('search');
    	
    	this.bandList = new BandCollection();
        this.bandListView = new BandListView({model: this.bandList});
        this.bandList.fetch({data: $.param({ search: search })});
    },

    getTabsForBand: function(band) {
        //this.search(search);
    	console.log("getting tabs for " + band);
    	this.tabList = new TabCollection();
        this.tabListView = new TabListView({model: this.tabList});
        this.tabList.fetch({data: $.param({ search: band })});
    }
});
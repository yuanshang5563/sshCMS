Ext.onReady(function(){
	Ext.create('Ext.menu.Menu', {
	    width: 400,
	    plain: true,
	    floating: false,  // 通常你想设置这个为真 (默认的)
	    renderTo: Ext.getBody(),  // 通常由它的包含容器呈现
	    items: [{
	        text: 'plain item 1'
	    },{
	        text: 'plain item 2'
	    },{
	        text: 'plain item 3'
	    }]
	});

});